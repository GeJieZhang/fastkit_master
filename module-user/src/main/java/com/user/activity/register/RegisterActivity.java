package com.user.activity.register;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.ui.activity.BaseAppActivity;
import com.example.app.ApiUtils;
import com.example.app.RouteUtils;
import com.example.view.navigationbar.NomalNavigationBar;

import com.example.fastkit.http.ok.HttpUtils;
import com.example.fastkit.http.ok.extension.HttpDialogCallBack;
import com.example.fastkit.utils.string_deal.regex.RegexUtils;
import com.example.fastkit.utils.timer_countdown.CountDownTimer;
import com.user.R;
import com.user.R2;
import com.user.activity.register.bean.CodeBean;
import com.user.activity.register.bean.RegisterBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = RouteUtils.User_RegisterActivity)
public class RegisterActivity extends BaseAppActivity {
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.btn_code)
    Button btnCode;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @BindView(R2.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R2.id.et_recommended_code)
    EditText etRecommendedCode;
    @BindView(R2.id.cb_xy)
    CheckBox cbXy;
    @BindView(R2.id.tv_xy)
    TextView tvXy;
    @BindView(R2.id.btn_register)
    Button btnRegister;
    private boolean IS_CHECKED = false;

    private String sessionId = "";

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnCode.setEnabled(true);
            btnCode.setText("发送验证码");
        }
    };

    @Override
    protected void onCreateView() {

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initTitle();
        initView();
    }

    private void initView() {
        cbXy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IS_CHECKED = true;
                } else {
                    IS_CHECKED = false;
                }
            }
        });
    }

    private void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("注册")
                .builder();

    }


    @OnClick({R2.id.btn_code, R2.id.tv_xy, R2.id.btn_register})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_code) {
            getCode();

        } else if (i == R.id.tv_xy) {


        } else if (i == R.id.btn_register) {


            toRegister();

        }
    }

    private boolean checkRegisterParams(String phone, String code, String pwd, String pwd2) {
        if (phone.isEmpty()) {

            showToast("请输入手机号");
            return false;

        }

        if (!RegexUtils.checkPhone(phone)) {
            showToast("请输入正确的手机号");
            return false;
        }
        if (code.isEmpty()) {

            showToast("请输入验证码");
            return false;

        }
        if (pwd.isEmpty()) {

            showToast("请输入密码");
            return false;

        }
        if (pwd2.isEmpty()) {

            showToast("请输入确认密码");
            return false;

        }

        if (!pwd.equals(pwd2)) {
            showToast("两次密码不一致");
            return false;
        }

        if (!IS_CHECKED) {
            showToast("请阅读并同意《泰保协议》");
            return false;
        }

        return true;

    }

    private void toRegister() {

        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        String pwd2 = etPasswordAgain.getText().toString().trim();


        if (checkRegisterParams(phone, code, pwd, pwd2)) {

            HttpUtils.with(this)
                    .url(ApiUtils.REGISTER)
                    .post()
                    .addParam("phone", phone)
                    .addParam("code", code)
                    .addParam("pwd", pwd)
                    .addParam("pwd2", pwd2)
                    .addParam("sessionId", sessionId)
                    .execute(new HttpDialogCallBack<RegisterBean>() {
                        @Override
                        public void onSuccess(RegisterBean result) {

                            if (result.getState() == 1) {

//                                EventBus.getDefault().post(new Event<RegisterBean>(EventBusTag.UPDATE_LOGIN, result));
//                                finish();


                            }
                            showToast(result.getMessage());
                        }

                        @Override
                        public void onError(String e) {

                        }


                    });


//            OkHttpClient client = new OkHttpClient();
//            /**
//             * 传参数
//             */
//            FormBody formBody = new FormBody.Builder()
//                    .add("phone", phone)
//                    .add("code", code)
//                    .add("pwd", pwd)
//                    .add("pwd2", pwd2)
//                    .build();
//
//            final Request request = new Request.Builder()
//                    .url(ApiUtils.REGISTER)
//                    .post(formBody)
//                    .header("cookie", sessionId)
//                    .build();
//
//            Call call = client.newCall(request);
//
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Toast.makeText(RegisterActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    final String res = response.body().string();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
//                }
//            });

        }


    }


    private boolean checkCodeParams(String phone) {
        if (phone.isEmpty()) {

            showToast("请输入手机号");
            return false;

        }

        if (!RegexUtils.checkPhone(phone)) {
            showToast("请输入正确的手机号");
            return false;
        }
        return true;
    }

    public void getCode() {

        String phone = etPhone.getText().toString().trim();
        if (checkCodeParams(phone)) {
            btnCode.setEnabled(false);
            timer.start();
            HttpUtils.with(this)
                    .url(ApiUtils.CODE)
                    .addParam("phone", phone)
                    .post()
                    .execute(new HttpDialogCallBack<CodeBean>() {
                        @Override
                        public void onSuccess(CodeBean result) {

                            if (result.getState() == 1) {
                                sessionId = result.getModel();
                                showToast("发送成功，请注意查收");
                            } else {
                                showToast("发送失败");
                            }
                        }

                        @Override
                        public void onError(String e) {


                        }


                    });
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }


}
