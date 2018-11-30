package com.sibao;

import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.ui.activity.BaseAppActivity;
import com.example.app.ApiUtils;
import com.example.app.RouteUtils;
import com.example.app.CacheUtils;
import com.example.fastkit.http.ok.HttpUtils;
import com.example.fastkit.http.ok.extension.HttpDialogCallBack;
import com.example.fastkit.db.sample_cache.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouteUtils.App_TestActivity)
public class TestActivity extends BaseAppActivity {

    @BindView(R.id.btn)
    Button btn;


    @OnClick(R.id.btn)
    public void onViewClicked() {
        HttpUtils.with(this)
                .url(ApiUtils.GET_ALL_CUSTOMER)
                .addParam("personnelId", ACache.get(this).getAsString(CacheUtils.USER_ID))
                .post()
                .execute(new HttpDialogCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {


                    }

                    @Override
                    public void onError(String e) {


                    }
                });

    }


    @Override
    protected void onCreateView() {
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);

    }
}
