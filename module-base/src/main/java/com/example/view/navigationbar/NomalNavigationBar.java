package com.example.view.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fastkit.R;
import com.example.fastkit.views.navigationbar.AbsNavigationBar;


/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/2/26.
 * Version 1.0
 * Description:
 */
public class NomalNavigationBar<D extends
        NomalNavigationBar.Builder.DefaultNavigationParams> extends
        AbsNavigationBar<NomalNavigationBar.Builder.DefaultNavigationParams> {

    public NomalNavigationBar(Builder.DefaultNavigationParams params) {
        super(params);
    }


    @Override
    public int bindLayoutId() {
        return R.layout.kit_title_bar;
    }

    @Override
    public void applyView() {


        setLeft(R.id.back, getParams().isHideLeft);
        // 绑定效果
        setTitleText(R.id.title, getParams().mTitle);
        setRightText(R.id.right_text, getParams().mRightText);
        setRightIcon(R.id.right_text, getParams().mRightIcon);
        setOnClickListener(R.id.right_text, getParams().mRightClickListener);
        // 左边 要写一个默认的  finishActivity
        setOnClickListener(R.id.back, getParams().mLeftClickListener);
    }


    /**
     * 设置Title文本
     *
     * @param viewId
     * @param text
     */
    protected void setTitleText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }


    /**
     * 设置Title文本
     *
     * @param viewId
     * @param text
     */
    private TextView tvRight;

    protected void setRightText(int viewId, String text) {

        if (!TextUtils.isEmpty(text)) {
            tvRight = findViewById(viewId);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
        }
    }


    protected void setRightIcon(int viewId, Integer icon) {
        if (icon!=null){
            tvRight = findViewById(viewId);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setBackgroundResource(icon);
        }



    }

    public TextView getRightTextView() {
        return tvRight;
    }

    private void setLeft(int viewId, boolean isHideLeft) {


        if (isHideLeft) {
            TextView tv = findViewById(viewId);
            tv.setVisibility(View.GONE);
        }
    }


    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams P;


        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }

        @Override
        public NomalNavigationBar builder() {
            NomalNavigationBar navigationBar = new NomalNavigationBar(P);
            return navigationBar;
        }

        // 1. 设置所有效果

        public Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        public Builder isHideLeftIcon(Boolean b) {
            P.isHideLeft = b;
            return this;
        }

        public Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        /**
         * 设置右边的点击事件
         */
        public Builder
        setRightClickListener(View.OnClickListener rightListener) {
            P.mRightClickListener = rightListener;
            return this;
        }

        /**
         * 设置左边的点击事件
         */
        public Builder
        setLeftClickListener(View.OnClickListener leftListener) {
            P.mLeftClickListener = leftListener;
            return this;
        }

        /**
         * 设置右边的图片
         */
        public Builder setRightIcon(int rightRes) {

            P.mRightIcon = rightRes;
            return this;
        }

        public static class DefaultNavigationParams extends
                AbsNavigationParams {

            public boolean isHideLeft = false;
            // 2.所有效果放置
            public String mTitle;

            public String mRightText;

            public Integer mRightIcon;

            // 后面还有一些通用的
            public View.OnClickListener mRightClickListener;

            public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 关闭当前Activity
                    ((Activity) mContext).finish();
                }
            };

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
