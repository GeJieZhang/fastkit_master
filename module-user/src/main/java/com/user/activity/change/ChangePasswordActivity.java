package com.user.activity.change;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.ui.activity.BaseAppActivity;
import com.example.app.ApiUtils;
import com.example.app.RouteUtils;
import com.example.view.navigationbar.NomalNavigationBar;
import com.example.app.CacheUtils;
import com.example.fastkit.http.ok.HttpUtils;
import com.example.fastkit.http.ok.extension.HttpDialogCallBack;
import com.example.fastkit.db.sample_cache.ACache;
import com.user.R;
import com.user.R2;
import com.user.activity.change.bean.ChangePasswordBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouteUtils.User_ChangePasswordActivity)
public class ChangePasswordActivity extends BaseAppActivity {
    @BindView(R2.id.et_old_password)
    EditText etOldPassword;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreateView() {
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle();
    }

    private void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("修改密码")
                .builder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.btn_sure)
    public void onViewClicked() {
        toChangePassword();
    }

    private void toChangePassword() {

        String oldPassword = etOldPassword.getText().toString().trim();

        String password = etPassword.getText().toString().trim();

        if (checkChangePasswordParams(oldPassword, password)) {

            HttpUtils.with(this)
                    .url(ApiUtils.CHANGE_PASSWORD)
                    .post()
                    .addParam("personnelId", ACache.get(this).getAsString(CacheUtils.USER_ID))
                    .addParam("oldPwd", oldPassword)
                    .addParam("newPwd", password)
                    .execute(new HttpDialogCallBack<ChangePasswordBean>() {
                        @Override
                        public void onSuccess(ChangePasswordBean result) {

                            showToast(result.getMessage());

                        }

                        @Override
                        public void onError(String e) {

                        }
                    });

        }

    }

    private boolean checkChangePasswordParams(String oldPassword, String password) {

        if (oldPassword.isEmpty()) {

            showToast("请输入旧密码");
            return false;

        }

        if (password.isEmpty()) {

            showToast("请输入新密码");
            return false;

        }
        return true;
    }
}
