package com.huobi.api.client.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class ZipUtil {
    /**
     * 解压客户端发来的程序
     *
     * @param depressData
     * @return
     * @throws Exception
     */
    public static byte[] decompress(byte[] depressData) {
        ByteArrayInputStream is = new ByteArrayInputStream(depressData);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            GZIPInputStream gis = new GZIPInputStream(is);

            int count;
            byte data[] = new byte[1024];
            while ((count = gis.read(data, 0, 1024)) != -1) {
                os.write(data, 0, count);
            }
            gis.close();
            depressData = os.toByteArray();
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depressData;
    }
}
