package com.user.activity.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.fastkit.http.rt.RetrofitManager;
import com.example.fastkit.ioc.ViewUtils;
import com.example.ui.activity.BaseAppActivity;
import com.example.app.RouteUtils;
import com.example.ui.activity.statusbar.StatusBarCompat;
import com.example.utls.permissions.PermissionUtil;
import com.example.view.navigationbar.NomalNavigationBar;

import com.example.fastkit.utils.string_deal.regex.RegexUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.user.R;
import com.user.R2;
import com.user.activity.login.api.LoginApi;
import com.user.activity.login.bean.LoginBean;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Route(path = RouteUtils.User_LoginActivity)
public class LoginActivity extends BaseAppActivity {
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @BindView(R2.id.tv_forget)
    TextView tvForget;
    @BindView(R2.id.btn_login)
    Button btnLogin;
    @BindView(R2.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreateView() {
        setContentView(R.layout.activity_login);

        // StatusBarCompat.compat(this, R.color.colorAccent);
        ButterKnife.bind(this);


        ViewUtils.inject(this);
        initTitle();


    }




    private void initPermissions() {


        PermissionUtil.getInstance(LoginActivity.this)
                .externalStorage(new RxPermissions(this), new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
//                        toLogin();


                        ARouter.getInstance().build(RouteUtils.User_TestActivity).navigation();
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> permissions) {

                        showToast("请开启权限!");

                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                        showToast("请前往手机权限管理列表开启该权限!");
                    }
                });
    }

    private void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .isHideLeftIcon(true)
                .setTitle("登录")
                .builder();
    }


    @OnClick({R2.id.tv_forget, R2.id.btn_login, R2.id.btn_register})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_forget) {
            ARouter.getInstance().build(RouteUtils.User_FindPasswordActivity).navigation();
        }
        if (id == R.id.btn_login) {
            initPermissions();

        }
        if (id == R.id.btn_register) {
            ARouter.getInstance().build(RouteUtils.User_RegisterActivity).navigation();

        }

    }

    private void toLogin() {

        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (checkLoginParams(phone, password)) {


            //http://222.209.82.13:9999/HTOA/ph_index/getLunBo.action
//            HttpUtils.with(this)
//                    .url(ApiUtils.LOGIN)
//                    .addParam("phone", phone)
//                    .addParam("pwd", password)
//                    .post()
//                    .execute(new HttpDialogCallBack<LoginBean>() {
//                        @Override
//                        public void onSuccess(LoginBean result) {
//
//                            if (result.getState() == 1) {
//                                ACache aCache = ACache.get(LoginActivity.this);
//                                aCache.put(CacheUtils.USER_ID, result.getModel().getId() + "");
//                                aCache.put(CacheUtils.USER_NAME, result.getModel().getPersonnelName() + "");
//                                aCache.put(CacheUtils.USER_PHONE, result.getModel().getPhone() + "");
//                                aCache.put(CacheUtils.USER_HEAD, result.getModel().getHeadImg() + "");
//                                aCache.put(CacheUtils.USER_ID_CARD_NUMBER, result.getModel().getIdCard() + "");
//
//                                ARouter.getInstance().build(RouteUtils.App_MainActivity).navigation();
//
//                            }
//                            showToast(result.getMessage());
//                        }
//
//                        @Override
//                        public void onError(String e) {
//
//
//                        }
//
//
//                    });


            RetrofitManager.getInstance()
                    .createReq(LoginApi.class)
                    .loginReq("13540354597", "123456")
                    .enqueue(new Callback<LoginBean>() {
                        @Override
                        public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

                        }

                        @Override
                        public void onFailure(Call<LoginBean> call, Throwable t) {

                        }
                    });
        }

    }

    private boolean checkLoginParams(String phone, String password) {
        if (phone.isEmpty()) {

            showToast("请输入手机号");
            return false;

        }

        if (!RegexUtils.checkPhone(phone)) {
            showToast("请输入正确的手机号");
            return false;
        }


        if (password.isEmpty()) {
            showToast("请输入密码");
            return false;
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
