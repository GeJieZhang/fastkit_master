package com.sibao;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.app.RouteUtils;
import com.example.fastkit.views.dialog.bottom_dialog.BottomDialogs;
import com.example.fastkit.views.dialog.zenhui.AlertDialog;
import com.example.fastkit.views.navigationbar.DefaultNavigationBar;
import com.example.fastkit.views.select_picture.zenghui.ImageSelector;
import com.example.fastkit.views.select_picture.zenghui.SelectImageActivity;
import com.example.ui.activity.BaseAppActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class GuideActivity extends BaseAppActivity {

    Handler handler = new Handler();
    @BindView(R.id.btn_next)
    Button btnNext;


    @Override
    protected void onCreateView() {
        setContentView(R.layout.activity_guide);

        initTitle();
        ButterKnife.bind(this);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //requestWritePermissions();


                ARouter.getInstance().build(RouteUtils.User_TestActivity).navigation();
            }
        });
    }

    private void sleepTime() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);//3秒后执行Runnable中的run方法
    }

    protected void initTitle() {
        DefaultNavigationBar navigationBar = new
                DefaultNavigationBar.Builder(this)
                .setTitle("投稿")
                .builder();
    }

    private void requestWritePermissions() {
        RxPermissions rxPermission = new RxPermissions(GuideActivity.this);
        rxPermission
                .requestEach(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new io.reactivex.functions.Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            //用户同意了该权限
//
//                            ACache aCache = ACache.get(GuideActivity.this);
//                            if (aCache.getAsString(CacheUtils.USER_ID) == null || aCache.getAsString(CacheUtils.USER_ID).isEmpty() || aCache.getAsString(CacheUtils.USER_ID).equals("")) {
//                                ARouter.getInstance().build(RouteUtils.User_LoginActivity).navigation();
//                                finish();
//                            } else {
//                                startActivity(MainActivity.class);
//                                finish();
//                            }


                            // ARouter.getInstance().build(RouteUtils.User_LoginActivity).navigation();
//
//                            final String str[] = {"测试数据1", "测试数据2"};
//
//                            BottomDialogs.getInstance(GuideActivity.this)
//                                    .setTitle("标题")
//                                    .setData(str)
//                                    .setOnClickListener(new BottomDialogs.OnClickListener() {
//                                        @Override
//                                        public void click(int position) {
//
//
//                                            showToast(str[position]);
//                                        }
//                                    })
//                                    .show();


//                            ImageSelector.create().count(9).multi().origin(mImageList)
//                                    .showCamera(true).start(GuideActivity.this, SELECT_IMAGE_REQUEST);

                            Timber.e("===================111111111");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Toast.makeText(GuideActivity.this, "请打开内存写入权限", Toast.LENGTH_SHORT);
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Toast.makeText(GuideActivity.this, "请打开内存写入权限", Toast.LENGTH_SHORT);
                        }
                    }
                });


    }

    private ArrayList<String> mImageList;

    private int SELECT_IMAGE_REQUEST = 987;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE_REQUEST && data != null) {
                mImageList = data.getStringArrayListExtra(SelectImageActivity.EXTRA_RESULT);
                // 做一下显示
                //Log.e("TAG",mImageList.toString());
            }
        }
    }
}
