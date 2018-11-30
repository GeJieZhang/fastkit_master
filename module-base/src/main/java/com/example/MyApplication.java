package com.example;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.utls.application_deal.UIUtils;
import com.example.fastkit.http.ok.HttpUtils;
import com.example.fastkit.http.ok.OkHttpEngine;

import com.example.fastkit.utils.log.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.smtt.sdk.QbSdk;

import butterknife.ButterKnife;
import timber.log.Timber;

public class MyApplication extends Application {

    //==============================================================================================
    //====================================================================================系统开关====
    //==============================================================================================
    private static MyApplication applicationInstance;
    //是否开启日志
    private boolean isLog = true;
    //是否开启内存检测
    private boolean isLeakCanary = true;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;
        //组件化开发
        initRouter(this);

        //网络请求
        initHttp();

        //阿里热修复
        //queryHotFix();

        //腾讯浏览器内核
        initWebViewX5();

        //Timber日志
        initTimber();

        //内存检测
        initLeakCanary();
    }

    /**
     * 初始化内存检测
     */
    private void initLeakCanary() {
        if (isLeakCanary) {


            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    /**
     * 初始化Timber日志
     */
    private void initTimber() {

        if (isLog) {

            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(true);
        }
    }

    /**
     * 初始化腾讯X5浏览器内核
     */
    private void initWebViewX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。

                Timber.d("onViewInitFinished");
            }

            @Override
            public void onCoreInitFinished() {


            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    /**
     * 查询HotFix补丁
     */
    private void queryHotFix() {
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    /**
     * 得到Application对象
     *
     * @return
     */
    public static Context getInstance() {
        return applicationInstance;
    }

    /**
     * 初始化网络请求
     */
    private void initHttp() {
        HttpUtils.init(new OkHttpEngine());
    }


    /**
     * 初始化阿里组件化开发
     *
     * @param mApplication
     */
    private void initRouter(MyApplication mApplication) {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (UIUtils.isApkInDebug(applicationInstance)) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！
            //线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
            // 打印日志的时候打印线程堆栈
            ARouter.printStackTrace();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(mApplication);

    }

    /**
     * 初始化MultiDex
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);

        // initHotFix();
    }

    /**
     * 阿里热修复初始化
     */
    private void initHotFix() {
        String appVersion = "1.0.0";
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0";
        }


        Timber.d("版本号=" + appVersion);
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            // Toast.makeText(getApplicationContext(), "补丁加载成功,以为您修复Bug", Toast.LENGTH_SHORT).show();

                            Timber.d("补丁加载成功" + "Code=" + code);

                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            //Toast.makeText(getApplicationContext(), "补丁生效需要重启", Toast.LENGTH_SHORT).show();
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3

                        } else if (code == PatchStatus.CODE_DOWNLOAD_SUCCESS) {
                            Timber.d("补丁加载成功" + "Code=" + code);
                        } else {

                            Timber.d("补丁加载成功" + "Code=" + code);
                        }

                    }


                }).initialize();

    }

    /**
     * 阿里热修复提示框
     */
    private void showFixDialog() {
        new MaterialDialog.Builder(applicationInstance)
                .title("补丁修复")
                .content("请重新启动应用")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        // TODO
                        restartApp();

                    }
                })

                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        // TODO

                    }
                })
                .build()
                .show();
    }

    /**
     * 重启应用
     */
    public void restartApp() {
        final Intent intent = applicationInstance.getPackageManager()
                .getLaunchIntentForPackage(applicationInstance.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationInstance.startActivity(intent);
    }
}