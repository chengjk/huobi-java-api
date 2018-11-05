package com.huobi.api.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.domain.resp.ApiCallback;
import com.huobi.api.client.security.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;

/**
 * created by jacky. 2018/7/24 3:57 PM
 */
@Slf4j
public class HuobiApiWebSocketListener<T> extends WebSocketListener {


    private ApiCallback<T> callback;
    private Class<T> respClass;
    private TypeReference<T> eventTypeReference;

    public HuobiApiWebSocketListener(ApiCallback<T> apiCallback, Class<T> respClass) {
        this.callback = apiCallback;
        this.respClass = respClass;
        this.eventTypeReference = new TypeReference<T>() {
        };
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        byte[] uncompress = ZipUtil.decompress(bytes.toByteArray());
        onMessage(webSocket, new String(uncompress));
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        if (text.contains("ping")) {
            webSocket.send(text.replace("ping", "pong"));
        } else if (text.contains("pong")) {
            //ignore
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                T event;
                if (respClass == null) {
                    event = mapper.readValue(text, eventTypeReference);
                } else {
                    event = mapper.readValue(text, respClass);
                }
                callback.onResponse(webSocket, event);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        log.error("failure:", t);
        if (HuobiConfig.reconnectOnFailure) {
            reconnect(webSocket, 4998, "failure:" + t.getMessage(), "");
        }
    }


    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.info("closing. code:" + code + ",reason:" + reason);
        if (code == 1003) {
            //1003 ping check expired, session: 8e6a863b-2733-450c-9d02-5ce41ec811a7
            onExpired(webSocket, code, reason);
        } else if (code == 4999) {
            //手动关闭，不重连
        } else {
            //其他所有情况都重连
            if (HuobiConfig.autoReconnect) {
                reconnect(webSocket, code, reason, "");
            }
        }

    }


    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.info("closed");
    }


    public void onExpired(WebSocket webSocket, int code, String reason) {
        callback.onExpired(webSocket, code, reason);
        if (HuobiConfig.reconnectOnExpired) {
            reconnect(webSocket, code, reason, "");
        }
    }

    public void onConnect(WebSocket webSocket, Closeable closeable) {
        callback.onConnect(webSocket, closeable);
    }

    public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
        if (StringUtils.isNotBlank(unSub)) {
            webSocket.send(unSub);
        }
        log.info("reconnect ws, code:{},reason:{}", code, reason);
    }

}
