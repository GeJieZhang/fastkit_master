package com.user.activity.find;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

@Route(path = RouteUtils.User_FindPasswordActivity)
public class FindPasswordActivity extends BaseAppActivity {
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
    @BindView(R2.id.btn_sure)
    Button btnSure;
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
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("找回密码")
                .builder();
    }


    @OnClick({R2.id.btn_code, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_code) {
            getCode();
        } else if (i == R.id.btn_sure) {

            toFindPassword();
        }
    }


    private boolean checkForgetParams(String phone, String code, String pwd, String pwd2) {
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


        return true;

    }

    private void toFindPassword() {

        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        String pwd2 = etPasswordAgain.getText().toString().trim();


        if (checkForgetParams(phone, code, pwd, pwd2)) {

            HttpUtils.with(this)
                    .url(ApiUtils.FORGET_PASSWORD)
                    .post()
                    .addParam("phone", phone)
                    .addParam("code", code)
                    .addParam("newPwd", pwd)
                    .execute(new HttpDialogCallBack<RegisterBean>() {
                        @Override
                        public void onSuccess(RegisterBean result) {

                            if (result.getState() == 1) {

                                finish();

                            }
                            showToast(result.getMessage());


                        }

                        @Override
                        public void onError(String e) {

                        }


                    });


        }


    }


    public void getCode() {

        String phone = etPhone.getText().toString().trim();
        if (checkCodeParams(phone)) {
            btnCode.setEnabled(false);
            timer.start();
            //DialogUtils.showToRequest(this);
            HttpUtils.with(this)
                    .url(ApiUtils.GET_FORGER_CODE)
                    .addParam("phone", phone)
                    .post()
                    .execute(new HttpDialogCallBack<CodeBean>() {
                        @Override
                        public void onSuccess(CodeBean result) {


                            showToast(result.getMessage());

                        }

                        @Override
                        public void onError(String e) {


                        }


                    });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}
