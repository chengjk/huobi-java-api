package com.huobi.api.contract.impl;

import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.contract.HuobiContractApiRestClient;
import com.huobi.api.contract.domain.ContractInfo;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * created by jacky. 2018/11/30 11:51 AM
 */
public class HuobiContractApiRestClientImplTest {

    private String apiKey = "a";
    private String apiSecret = "s";
    private HuobiContractApiRestClient client;

    @Before
    public void config() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties props = new Properties();
        props.load(is);
        //unnecessary
        HuobiConfig.API_HOST = HuobiConsts.API_HOST_PRO;
        HuobiConfig.REST_API_URL = "https://api.hbdm.com";
        apiKey = props.getProperty("apiKey");
        apiSecret = props.getProperty("apiSecret");
        client = new HuobiContractApiRestClientImpl(apiKey, apiSecret);
    }
    @Test
    public void info() {

        List<ContractInfo> info = client.info(null, null, null);
        assert info != null;
    }
}
