package com.example.app;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;



/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class FragmentUtils {

    public static Fragment getHomeFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouteUtils.Home_Fragment_Main).navigation();
        return fragment;
    }

    public static Fragment getOrderFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouteUtils.Order_Fragment_Main).navigation();
        return fragment;
    }

    public static Fragment getProductFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouteUtils.Product_Fragment_Main).navigation();
        return fragment;
    }

    public static Fragment getMeFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouteUtils.Me_Fragment_Main).navigation();
        return fragment;
    }
}
