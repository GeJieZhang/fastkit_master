package com.user.activity.real_name;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.ui.activity.BaseAppActivity;
import com.example.app.ApiUtils;
import com.example.app.CacheUtils;
import com.example.app.RouteUtils;
import com.example.view.navigationbar.NomalNavigationBar;

import com.example.fastkit.http.ok.HttpUtils;
import com.example.fastkit.http.ok.extension.HttpDialogCallBack;
import com.example.fastkit.db.sample_cache.ACache;
import com.example.fastkit.utils.string_deal.regex.RegexUtils;
import com.user.R;
import com.user.R2;
import com.user.activity.real_name.bean.RealNameBean;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = RouteUtils.User_RealNameActivity)
public class RealNameActivity extends BaseAppActivity {
    @BindView(R2.id.tv_name)
    EditText tvName;
    @BindView(R2.id.tv_number)
    EditText tvNumber;
    @BindView(R2.id.male)
    RadioButton male;
    @BindView(R2.id.femle)
    RadioButton femle;
    @BindView(R2.id.rg)
    RadioGroup rg;

    @Override
    protected void onCreateView() {

        setContentView(R.layout.activity_real_name);

        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        initTitle();


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.male) {
                    sex = "男";
                } else {
                    sex = "女";
                }
            }
        });

    }

    private void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("信息完善")
                .setRightText("确定")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        postInfo();

                    }
                })
                .builder();
    }

    private String sex = "男";

    private void postInfo() {
        final String personnelName = tvName.getText().toString().trim();
        final String idCard = tvNumber.getText().toString().trim();


        if (checkInfoParams(personnelName, idCard, sex)) {
            HttpUtils.with(this)
                    .post()
                    .url(ApiUtils.POST_USER_INFO)
                    .addParam("id", ACache.get(this).getAsString(CacheUtils.USER_ID))
                    .addParam("personnelName", personnelName)
                    .addParam("idCard", idCard)
                    .addParam("gender", sex)
                    .execute(new HttpDialogCallBack<RealNameBean>() {
                        @Override
                        public void onSuccess(RealNameBean result) {

                            if (result.getState() == 1) {
                                ACache aCache = ACache.get(RealNameActivity.this);
                                aCache.put(CacheUtils.USER_NAME, personnelName);
                                aCache.put(CacheUtils.USER_ID_CARD_NUMBER, idCard);
//                                EventBus.getDefault().post(new Event<>(EventBusTag.UPDATE_HOME_FRAGMENT, ""));
//
//                                EventBus.getDefault().post(new Event<>(EventBusTag.UPDATE_ME_FRAGMENT, ""));

                                showToast(result.getMessage());
                            } else {
                                showToast(result.getMessage());
                            }


                        }

                        @Override
                        public void onError(String e) {

                        }
                    });

        }
    }

    private boolean checkInfoParams(String personnelName, String idCard, String sex) {
        if (personnelName.isEmpty()) {

            showToast("请输入姓名");
            return false;

        }

        if (!RegexUtils.checkIdCard(idCard)) {
            showToast("请输入正确的身份证号");
            return false;
        }


        if (sex.isEmpty()) {
            showToast("请选择性别");
            return false;
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
