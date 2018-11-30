package com.example.fastkit.http.rt;

import android.view.animation.BaseInterpolator;

import com.example.fastkit.http.ok.interceptor.BaseParamsInterceptor;
import com.example.fastkit.http.ok.interceptor.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zjh
 * @date 2016/6/29
 */
public class RetrofitManager {
    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;

    private static String BASE_URL = "http://222.209.82.13:9999/HTOA/";

    private RetrofitManager() {
        initRetrofit();
    }

    public static synchronized RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    private void setBaseUrl(String baseUrl) {
        this.BASE_URL = baseUrl;
    }

    private void initRetrofit() {

        BaseParamsInterceptor baseParamsInterceptor = new BaseParamsInterceptor();
        RequestInterceptor requestInterceptor = new RequestInterceptor(RequestInterceptor.Level.ALL);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(baseParamsInterceptor);
        builder.addInterceptor(requestInterceptor);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
