package com.huobi.api.client.constant;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * created by jacky. 2018/7/20 8:56 PM
 */
public interface HuobiConsts {
    String API_HOST = "api.huobi.pro";
    String API_URL = "https://" + API_HOST;


    String WS_API_BASE_URL_PRO = "wss://api.huobi.pro/ws";
    String WS_API_BASE_URL_HADAX = "wss://api.hadax.com/ws";


    String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";


    String ENDPOINT_SECURITY_TYPE_APIKEY = "AccessKeyId";
    String ENDPOINT_SECURITY_TYPE_APIKEY_HEADER = ENDPOINT_SECURITY_TYPE_APIKEY + ":#";

    String ENDPOINT_SECURITY_TYPE_SIGNED = "Signature";
    String ENDPOINT_SECURITY_TYPE_SIGNED_HEADER = ENDPOINT_SECURITY_TYPE_SIGNED + ":#";

    String SIGNATURE_METHOD = "HmacSHA256";
    String SIGNATURE_VERSION = "2";

    DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
    ZoneId ZONE_GMT = ZoneId.of("Z");

}
