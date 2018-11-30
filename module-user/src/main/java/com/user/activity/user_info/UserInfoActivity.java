package com.user.activity.user_info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.ui.activity.BaseAppActivity;
import com.example.app.ApiUtils;
import com.example.app.RouteUtils;
import com.example.view.navigationbar.NomalNavigationBar;
import com.example.MyApplication;
import com.example.app.CacheUtils;

import com.example.fastkit.views.dialog.bottom_dialog.BottomDialogs;
import com.example.fastkit.http.ok.HttpUtils;
import com.example.fastkit.http.ok.extension.HttpDialogCallBack;
import com.example.fastkit.http.ok.extension.HttpNormalCallBack;
import com.example.fastkit.views.file_size.PcUtils;
import com.example.fastkit.db.sample_cache.ACache;
import com.example.fastkit.utils.string_deal.empty.EmptyUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.user.R;
import com.user.R2;
import com.user.activity.user_info.bean.UserHeadChangeBean;
import com.user.activity.user_info.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouteUtils.User_UserInfoActivity)
public class UserInfoActivity extends BaseAppActivity {

    String[] a = {"相册", "相机"};
    @BindView(R2.id.iv_head)
    CircleImageView ivHead;
    @BindView(R2.id.lin_tx)
    LinearLayout linTx;
    @BindView(R2.id.tv_sjh)
    TextView tvSjh;
    @BindView(R2.id.lin_sjh)
    LinearLayout linSjh;
    @BindView(R2.id.tv_nc)
    TextView tvNc;
    @BindView(R2.id.lin_nc)
    LinearLayout linNc;
    @BindView(R2.id.tv_xb)
    TextView tvXb;
    @BindView(R2.id.lin_xb)
    LinearLayout linXb;
    @BindView(R2.id.tv_yqm)
    TextView tvYqm;
    @BindView(R2.id.lin_yqm)
    LinearLayout linYqm;
    @BindView(R2.id.tv_smrz)
    TextView tvSmrz;
    @BindView(R2.id.lin_smrz)
    LinearLayout linSmrz;

    @Override
    protected void onCreateView() {
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
        initData();


    }

    private UserInfoBean userInfoBean;

    private void initData() {
        HttpUtils.with(this)
                .post()
                .url(ApiUtils.GET_USER_INFO)
                .addParam("personnelId", ACache.get(this).getAsString(CacheUtils.USER_ID))
                .execute(new HttpNormalCallBack<UserInfoBean>() {
                    @Override
                    public void onSuccess(UserInfoBean result) {

                        userInfoBean = result;
                        if (!EmptyUtil.isEmpty(result.getModel().getPhone())) {
                            tvSjh.setText(result.getModel().getPhone());
                        } else {
                            tvSjh.setText("未知");
                        }

                        if (!EmptyUtil.isEmpty(result.getModel().getPersonnelName())) {
                            tvNc.setText(result.getModel().getPersonnelName());
                        } else {
                            tvNc.setText("未知");
                        }

                        if (!EmptyUtil.isEmpty(result.getModel().getGender())) {
                            tvXb.setText(result.getModel().getGender());
                        } else {
                            tvXb.setText("未知");
                        }

                        if (!EmptyUtil.isEmpty(result.getModel().getJobNumber())) {
                            tvYqm.setText(result.getModel().getJobNumber());
                        } else {
                            tvYqm.setText("未知");
                        }

                        if (!EmptyUtil.isEmpty(result.getModel().getHeadImg())) {

                            ACache.get(UserInfoActivity.this).put(CacheUtils.USER_HEAD, result.getModel().getHeadImg() + "");
                            Glide.with(MyApplication.getInstance())
                                    .load(ApiUtils.IMAGE_PATH + ACache.get(UserInfoActivity.this).getAsString(CacheUtils.USER_HEAD))
                                    .into(ivHead);
                        }


                        if (!EmptyUtil.isEmpty(result.getModel().getIdCard())) {
                            tvSmrz.setText("已认证");
                        } else {
                            tvSmrz.setText("未认证");
                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    private MaterialDialog materialDialog;

    private void initView() {


        initTitle();

        materialDialog = new MaterialDialog.Builder(UserInfoActivity.this)
                .title("返回")
                .content("你确定要放弃修改么？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        finish();


                    }
                })

                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {


                    }
                })
                .build();
    }

    private void showChose() {
        BottomDialogs.getInstance(this)
                .setTitle("请选择图片源")
                .setData(a)
                .setOnClickListener(new BottomDialogs.OnClickListener() {
                    @Override
                    public void click(int position) {

                        //相册
                        if (position == 0) {

                            selectPicture();

                        }
                        //相机
                        if (position == 1) {
                            takePicture();

                        }
                    }
                })
                .show();


    }

    private NomalNavigationBar navigationBar;

    private void initTitle() {
        navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("用户信息")
                .setRightText("确定")

                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!path.equals("")) {

                            materialDialog.show();
                        } else {
                            finish();
                        }

                    }
                })

                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!path.equals("")) {
                            updateHead();
                        }

                    }
                })
                .builder();

        navigationBar.getRightTextView().setVisibility(View.GONE);
    }

    private void updateHead() {
        Observable.just(path)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String path) throws Exception {

                        String base64 = PcUtils.pathToBase64(path);
                        return base64;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String base64) throws Exception {
                        changeHead(base64);


                    }
                });
    }

    //==============================================================================================
    //==============================================================================图片选择工具====
    //==============================================================================================


    private List<LocalMedia> selectMedia = new ArrayList<>();

    private String path = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectMedia = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    //LogUtil.e("原文件大小=" + FileSizeUtil.getAutoFileOrFilesSize(selectMedia.get(0).getPath()));


                    if (selectMedia.get(0).isCut()) {
                        // 裁剪过
                        //LogUtil.e("剪切大小=" + FileSizeUtil.getAutoFileOrFilesSize(selectMedia.get(0).getCutPath()));
                        // String newPath = JpegUtils.compressImage(path);
                        //LogUtil.e("Jpeg压缩=" + FileSizeUtil.getAutoFileOrFilesSize(newPath));
                        path = selectMedia.get(0).getCutPath();

                        if (!EmptyUtil.isEmpty(path)) {
                            navigationBar.getRightTextView().setVisibility(View.VISIBLE);
                        }
                        Bitmap bm = PcUtils.getZipBitmap(path);
                        ivHead.setImageBitmap(bm);

                    }


                    break;
            }
        }
    }

    private void changeHead(final String base64) {


        HttpUtils.with(this)
                .post()
                .url(ApiUtils.CHANGE_USER_INFO)
                .addParam("personnelId", ACache.get(getApplication()).getAsString(CacheUtils.USER_ID))
                .addParam("imgStr", base64)
                .execute(new HttpDialogCallBack<UserHeadChangeBean>() {
                    @Override
                    public void onSuccess(UserHeadChangeBean result) {

                        showToast(result.getMessage());
                        path = "";

                        if (result.getState() == 1) {
                            ACache.get(UserInfoActivity.this).put(CacheUtils.USER_HEAD, result.getModel().getHeadImg());

//                            EventBus.getDefault().post(new Event<>(EventBusTag.UPDATE_ME_FRAGMENT, ""));
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }


    private void selectPicture() {

        PictureSelector.create(UserInfoActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .compressGrade(Luban.FIRST_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(false)// 是否压缩 true or false
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .selectionMedia(selectMedia)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .compressMaxKB(200)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                .compressWH(200, 200) // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


    }


    private void takePicture() {

        PictureSelector.create(UserInfoActivity.this)
                .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .compressGrade(Luban.FIRST_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(false)// 是否压缩 true or false
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .selectionMedia(selectMedia)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .compressMaxKB(200)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                .compressWH(200, 200) // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


    }


    @OnClick({R2.id.lin_tx, R2.id.lin_sjh, R2.id.lin_nc, R2.id.lin_xb, R2.id.lin_yqm, R2.id.lin_smrz})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_tx) {
            showChose();
        } else if (i == R.id.lin_sjh) {
        } else if (i == R.id.lin_nc) {
        } else if (i == R.id.lin_xb) {
        } else if (i == R.id.lin_yqm) {
        } else if (i == R.id.lin_smrz) {

            if (!EmptyUtil.isEmpty(userInfoBean.getModel().getIdCard())) {
                //tvSmrz.setText("已认证");
            } else {
                // tvSmrz.setText("未认证");

                ARouter.getInstance().build(RouteUtils.User_RealNameActivity).navigation();
            }
        }
    }

}
