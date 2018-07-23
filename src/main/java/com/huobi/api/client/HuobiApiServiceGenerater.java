package com.huobi.api.client;

import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.exception.HuobiApiException;
import com.huobi.api.client.security.AuthenticationInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * created by jacky. 2018/7/20 9:07 PM
 */
@Slf4j
public class HuobiApiServiceGenerater {


    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(HuobiConsts.API_URL)
                    .addConverterFactory(JacksonConverterFactory.create());



    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String secret) {
        if (!StringUtils.isEmpty(apiKey) && !StringUtils.isEmpty(secret)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                log.error(response.message());

//                BinanceApiError apiError = getBinanceApiError(response);
//                throw new BinanceApiException(apiError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static HuobiApiException getBinanceApiError(Response<?> response) throws IOException, HuobiApiException {
        return (HuobiApiException)retrofit.responseBodyConverter(HuobiApiException.class, new Annotation[0])
                .convert(response.errorBody());
    }


}
