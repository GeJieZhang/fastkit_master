package com.example.fastkit.http.ok;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.example.fastkit.http.ok.err.HttpException;
import com.example.fastkit.http.ok.interceptor.RequestInterceptor;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/3/4.
 * Version 1.0
 * Description: OkHttp默认的引擎
 */
public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClient;
    private static List<Call> callList = new ArrayList<>();
    private static RequestInterceptor customInterceptor;
    private static final int TIME_OUT = 15;
    private static final String TAG = "======网络请求路径======";
    private Handler mDelivery;


    public OkHttpEngine() {

        mDelivery = new Handler(Looper.getMainLooper());
        customInterceptor = new RequestInterceptor(RequestInterceptor.Level.ALL);
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(customInterceptor)
                .build();

    }

    @Override
    public void post(final Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {

        final String jointUrl = HttpUtils.jointParams(url, params);  //打印
        Log.e(TAG, jointUrl);
        RequestBody requestBody = appendBody(params);
        final Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        callList.add(call);
        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {

                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(e);

                                callList.remove(call);
                            }
                        });

                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        final String result = response.body().string();

                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {


                                if (response.code() != 200) {
                                    callBack.onError(new HttpException(response));
                                } else {
                                    callBack.onSuccess(result);
                                }

                                callList.remove(call);

                            }
                        });


                    }
                }
        );

    }


    /**
     * 组装post请求参数body
     */
    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    // 添加参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {

            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value instanceof File) {
                    // 处理文件 --> Object File
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody
                            .create(MediaType.parse(guessMimeType(file
                                    .getAbsolutePath())), file));
                } else if (value instanceof List) {
                    // 代表提交的是 List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            // 获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    /**
     * 猜测文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    @Override
    public void get(final Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {
        url = HttpUtils.jointParams(url, params);

        Log.e(TAG, url);

        Request.Builder requestBuilder = new Request.Builder().url(url).tag(context);
        //可以省略，默认是GET请求
        Request request = requestBuilder.build();
        Call call = mOkHttpClient.newCall(request);
        callList.add(call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {


                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                        callList.remove(call);

                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String resultJson = response.body().string();


                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.code() != 200) {
                            callBack.onError(new HttpException(response));
                        } else {
                            callBack.onSuccess(resultJson);
                        }
                        callList.remove(call);

                    }
                });
            }
        });

    }


    public void cancel() {
        if (callList.size() > 0) {
            for (Call call : callList) {
                call.cancel();
                callList.remove(call);

            }
        }
    }
}
