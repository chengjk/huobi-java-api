package com.huobi.api.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.domain.event.WsNotify;
import com.huobi.api.client.domain.resp.ApiCallback;
import com.huobi.api.client.security.ZipUtil;
import lombok.Getter;
import lombok.Setter;
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
public class HuobiApiWebSocketListener extends WebSocketListener {

    private ApiCallback callback;
    private Class<? extends WsNotify> respClass;
    private TypeReference<WsNotify> eventTypeReference;
    @Setter
    @Getter
    private boolean manualClose = false;

    public HuobiApiWebSocketListener(ApiCallback apiCallback, Class<? extends WsNotify> respClass) {
        this.callback = apiCallback;
        this.respClass = respClass;
        this.eventTypeReference = new TypeReference<WsNotify>() {
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
            callback.onPing(webSocket, getCloseable(webSocket));

        } else if (text.contains("pong")) {
            //ignore
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                WsNotify event;
                if (respClass == null) {
                    event = mapper.readValue(text, eventTypeReference);
                } else {
                    event = mapper.readValue(text, respClass);
                }
                if (event.withData()) {
                    callback.onResponse(webSocket, event);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        if (manualClose) {
            log.info("failure, manual close.");
        } else {
            log.error("failure:", t);
            if (HuobiConfig.ReconnectOnFailure) {
                reconnect(webSocket, HuobiConsts.WsCode.autoReconnect, "failure:" + t.getMessage(), null);
            }
        }
    }


    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.info("closing. code:{},reason:{}", code, reason);
        if (code == HuobiConsts.WsCode.pingExpired) {
            //1003 ping check expired, session: 8e6a863b-2733-450c-9d02-5ce41ec811a7
            onExpired(webSocket, code, reason);
        } else if (code == HuobiConsts.WsCode.manualClose) {
            manualClose = true;
            //手动关闭，不重连
        } else {
            //其他所有情况都重连
            if (HuobiConfig.AutoReconnect) {
                reconnect(webSocket, code, reason, null);
            }
        }

    }


    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.info("closed. code:{},reason:{}", code, reason);
    }


    public void onExpired(WebSocket webSocket, int code, String reason) {
        log.info("expired. code:{},reason:{}", code, reason);
        callback.onExpired(webSocket, code, reason);
        if (HuobiConfig.ReconnectOnExpired) {
            reconnect(webSocket, code, reason, null);
        }
    }

    public void onConnect(WebSocket webSocket, Closeable closeable) {
        callback.onConnect(webSocket, closeable);
    }

    public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
        manualClose = false;
        if (webSocket != null && StringUtils.isNotBlank(unSub)) {
            webSocket.send(unSub);
        }
        log.info("reconnect ws, code:{},reason:{}", code, reason);
    }


    public Closeable getCloseable(WebSocket webSocket) {
        return () -> {
            this.setManualClose(true);
            this.onClosing(webSocket, HuobiConsts.WsCode.manualClose, "manual close.");
            webSocket.close(HuobiConsts.WsCode.manualClose, "manual close.");
            this.onClosed(webSocket, HuobiConsts.WsCode.manualClose, "manual close.");
        };
    }
}
