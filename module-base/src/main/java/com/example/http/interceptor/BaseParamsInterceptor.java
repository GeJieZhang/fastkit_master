package com.example.http.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseParamsInterceptor implements Interceptor {


    public BaseParamsInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {
            //添加公共参数
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    .addQueryParameter("clienttype", "1")
                    .addQueryParameter("imei", "imei")
                    .addQueryParameter("version", "VersionName")
                    .addQueryParameter("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();
            request = request.newBuilder().url(httpUrl).build();

        }
        return chain.proceed(request);

    }
}
