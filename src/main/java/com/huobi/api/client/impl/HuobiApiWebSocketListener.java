package com.huobi.api.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobi.api.client.domain.resp.ApiCallback;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * created by jacky. 2018/7/24 3:57 PM
 */
@Slf4j
public class HuobiApiWebSocketListener<T> extends WebSocketListener {


    private ApiCallback<T> callback;
    private Class<T> respClass;
    private TypeReference<T> eventTypeReference;


    public HuobiApiWebSocketListener(ApiCallback<T> apiCallback) {
        this.callback = apiCallback;
        this.eventTypeReference = new TypeReference<T>() {};
    }

    public HuobiApiWebSocketListener(ApiCallback<T> apiCallback, Class<T> respClass) {
        this.callback = apiCallback;
        this.respClass = respClass;
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        byte[] uncompress = uncompress(bytes.toByteArray());
        String resp = new String(uncompress);
        if (resp.contains("ping")) {
            webSocket.send(resp.replace("ping", "pong"));
        } else if (resp.contains("pong")) {
            //ignore
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                T event = null;
                if (respClass == null) {
                    event = mapper.readValue(resp, eventTypeReference);
                } else {
                    event = mapper.readValue(resp, respClass);
                }

                callback.onResponse(event);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

        }
    }


    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        log.info("failure", t);
    }


    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.info("closing");
        log.info(code + reason);
    }


    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {

        log.info("closed");
    }


    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


}
