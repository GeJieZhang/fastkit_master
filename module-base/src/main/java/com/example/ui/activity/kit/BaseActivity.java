package com.example.ui.activity.kit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fastkit.R;
import com.example.fastkit.ui.base.control.ActivityCollector;

/**
 * Created by Eagle510 on 2017/8/10.
 * 功能：
 * 1.Activity的固定方法流程
 * 2.状态栏的显示与隐藏
 * 3.透明状态栏
 * 4.沉静式状态栏
 * 5.标题栏的显示与隐藏
 * 6.状态栏的主题颜色修改
 * 7.Activity的管理
 * 8.日志打印与Toast打印
 * 9.键盘的显示与隐藏
 * 10.点击屏幕隐藏键盘
 * 11.启动Activity
 * 12.findViewById
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initSystemBarTint();
        addActivity();
        onCreateView();
    }


    //==============================================================================================
    //====================================================================Activity的固定方法流程====
    //==============================================================================================
    //Activity的管理
    private void addActivity() {
        ActivityCollector.addActivity(this);
    }

    //Activity的操作
    protected abstract void onCreateView();
    //==============================================================================================
    //============================================================================显示与隐藏键盘====
    //==============================================================================================

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .toggleSoftInput(
                        InputMethodManager.RESULT_UNCHANGED_SHOWN,
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //==============================================================================================
    //==========================================================================处理键盘点击隐藏====
    //==============================================================================================

    /**
     * 设置点击屏幕隐藏键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // get current focus,Generally it is EditText
            View view = getCurrentFocus();
            if (isShouldHideSoftKeyBoard(view, ev)) {
                hideSoftKeyBoard(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Judge what situation hide the soft keyboard,click EditText view should show soft keyboard
     *
     * @param
     * @param event
     * @return
     */
    private boolean isShouldHideSoftKeyBoard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] l = {0, 0};
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + view.getHeight(), right = left
                    + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // If click the EditText event ,ignore it
                return false;
            } else {
                return true;
            }
        }
        // if the focus is EditText,ignore it;
        return false;
    }

    /**
     * hide soft keyboard
     *
     * @param token
     */
    private void hideSoftKeyBoard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //==============================================================================================
    //================================================================================处理状态栏====
    //==============================================================================================

    /**
     * 设置状态栏颜色
     * 沉静式状态栏和透明状态栏只能同时存在一个
     * 默认为沉静式
     */
    protected void initSystemBarTint() {

        if (!noStatusBar()) {
            if (!translucentStatusBar()) {

                if (calmStatusBar()) {


                    /**
                     * 沉浸式状态栏
                     */
                    // 代表 5.0 及以上
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        try {
                            this.getWindow().setStatusBarColor(this.getResources().getColor(setStatusBarColor()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    // versionCode > 4.4  and versionCode < 5.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        ViewGroup androidContainer = (ViewGroup) this.findViewById(android.R.id.content);
                        // 留出高度 setFitsSystemWindows  true代表会调整布局，会把状态栏的高度留出来
                        View contentView = androidContainer.getChildAt(0);
                        if (contentView != null) {
                            contentView.setFitsSystemWindows(true);
                        }
                        // 在原来的位置上添加一个状态栏
                        View statusBarView = createStatusBarView(this);
                        androidContainer.addView(statusBarView, 0);
                        statusBarView.setBackgroundColor(setStatusBarColor());
                    }
                }


            } else {

                /**
                 * 设置透明状态栏
                 */
                // 代表 5.0 及以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View decorView = this.getWindow().getDecorView();
                    int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                    decorView.setSystemUiVisibility(option);
                    this.getWindow().setStatusBarColor(Color.TRANSPARENT);
                }

                // versionCode > 4.4  and versionCode < 5.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            }
        } else {
            /**
             * 无状态栏
             */
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


    }

    /**
     * 创建一个需要填充statusBarView
     */
    private static View createStatusBarView(Activity activity) {
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams statusBarParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(statusBarParams);
        return statusBarView;
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    //==============================================================================================
    //==========================================================================打印日志和弹出框====
    //==============================================================================================

    /**
     * Toast
     *
     * @param text
     */
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    /**
     * Toast
     *
     * @param resId
     */
    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    /**
     * Log
     *
     * @param msg
     */
    public static void showLog(String msg) {
        Log.e("调试日志", msg);
    }


    //==============================================================================================
    //======================================================================BaseActivity常用方法====
    //==============================================================================================

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();


    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 透明状态栏
     *
     * @return
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 无状态栏
     *
     * @return
     */
    protected boolean noStatusBar() {
        return false;
    }

    /**
     * 沉静式状态栏
     *
     * @return
     */
    protected boolean calmStatusBar() {
        return true;
    }

    /**
     * 返回主页面
     */
    public void toMainActivity() {
        ActivityCollector.toMainActivity();
    }

    /**
     * 启动Activity
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * findViewById
     *
     * @return View
     */
    protected <T extends View> T viewById(int viewId) {
        return (T) findViewById(viewId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
