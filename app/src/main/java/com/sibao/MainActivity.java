package com.sibao;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.app.FragmentUtils;
import com.example.ui.activity.BaseAppActivity;
import com.example.app.RouteUtils;

import com.example.fastkit.ui.base.control.ActivityCollector;


import java.util.ArrayList;

@Route(path = RouteUtils.App_MainActivity)
public class MainActivity extends BaseAppActivity {

    private ViewPager mMViewPager;
    private TabLayout mToolbarTab;
    /**
     * 图标
     */
    private int[] tabIcons = {
            R.drawable.tab_home,
            R.drawable.tab_order,
//            R.drawable.tab_product,
            R.drawable.tab_me
    };
    private String[] tab_array;
    private DemandAdapter mDemandAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    private void initData() {
        tab_array = getResources().getStringArray(R.array.tab_main);
        fragments.clear();

        fragments.add(FragmentUtils.getHomeFragment());
        fragments.add(FragmentUtils.getOrderFragment());
//        fragments.add(FragmentUtils.getProductFragment());
        fragments.add(FragmentUtils.getMeFragment());
    }

    private void initView() {
        mMViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mToolbarTab = (TabLayout) findViewById(R.id.toolbar_tab);
        mMViewPager.setOffscreenPageLimit(3);
    }

    private void setViewPagerAdapter() {
        mDemandAdapter = new DemandAdapter(getSupportFragmentManager());
        mMViewPager.setAdapter(mDemandAdapter);
    }

    private void setTabBindViewPager() {
        mToolbarTab.setupWithViewPager(mMViewPager);
    }

    private void setItem() {
        /**
         * 一定要在设置适配器之后设置Icon
         */
        for (int i = 0; i < mToolbarTab.getTabCount(); i++) {
            mToolbarTab.getTabAt(i).setCustomView(getTabView(i));
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView tab_image = view.findViewById(R.id.tab_image);
        TextView tab_text = view.findViewById(R.id.tab_text);
        tab_image.setImageResource(tabIcons[position]);
        tab_text.setText(tab_array[position]);
        return view;
    }


    @Override
    protected void onCreateView() {

        setContentView(R.layout.activity_main);
        initView();
        initData();
        setViewPagerAdapter();
        setTabBindViewPager();
        setItem();
    }


    /**
     * 适配器
     */
    public class DemandAdapter extends FragmentStatePagerAdapter {


        public DemandAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    private int count = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


            count++;

            if (count > 1) {
                //  showFinishDialog();
                ActivityCollector.finishAll();
            } else {
                showToast("再按一次退出应用");

            }

            return true;
        }
        return true;
    }

}
