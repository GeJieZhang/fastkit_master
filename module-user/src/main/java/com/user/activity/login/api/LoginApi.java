package com.user.activity.login.api;

import com.example.app.ApiUtils;
import com.user.activity.login.bean.LoginBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {

    @POST(ApiUtils.LOGIN)
    Call<LoginBean> loginReq(@Query("phone") String userName, @Query("pwd") String pwd);
}
