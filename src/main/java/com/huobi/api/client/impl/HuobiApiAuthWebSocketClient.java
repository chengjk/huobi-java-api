package com.huobi.api.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobi.api.client.security.ZipUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * created by jacky. 2018/9/27 9:19 PM
 */
@Slf4j
@Getter
@Setter
public class HuobiApiAuthWebSocketClient extends WebSocketClient {

    private String auth;
    private String topic;
    private HuobiApiWebSocketListener<?> listener;

    public HuobiApiAuthWebSocketClient(URI serverURI) {
        super(serverURI);
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            this.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sc));
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send(auth);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        try {
            byte[] uncompress = ZipUtil.decompress(bytes.array());
            String text = new String(uncompress);
            onMessage(text);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void onMessage(String text) {
        try {
            if (text.contains("ping")) {
                send(text.replace("ping", "pong"));
            } else if (text.contains("pong")) {
                //ignore
            } else {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(text);
                String op = node.get("op").asText();
                if ("auth".equals(op)) {
                    String code = node.get("err-code").asText();
                    if ("0".equals(code)) {
                        //auth success todo
                        send(topic);
                    }
                } else if ("notify".equals(op)) {
                    listener.onMessage(null, text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        listener.onClosed(null, i, s);
    }

    @Override
    public void onError(Exception e) {
        listener.onFailure(null, e, null);
    }
}
