package com.bee.user.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bee.user.R;
import com.bee.user.event.MainEvent;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.home.HomeFragment;
import com.bee.user.ui.home.MiaoshaFragment;
import com.bee.user.ui.login.LoginActivity;
import com.bee.user.ui.nearby.NearbyFragment;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.lc.bottomindicator.IOnTab3Click;
import com.huaxiafinance.lc.bottomindicator.IconTabPageIndicator;
import com.huaxiafinance.lc.bottomindicator.viewpager.CustomViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：app主页
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, IOnTab3Click {

    @BindView(R.id.tab_indicator)
    IconTabPageIndicator mIndicator;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;

    private ArrayList<Fragment> fragments;

//    @Override
//    protected void initImmersionBar() {
//         ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
//    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

        EventBus.getDefault().register(this);


        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance(0));
        fragments.add(NearbyFragment.newInstance());
        fragments.add(MiaoshaFragment.newInstance(0));
        fragments.add(MiaoshaFragment.newInstance(0));

        MainAdapter myAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setCanScroll(false);
        mViewPager.setAdapter(myAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size() - 1);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnTab3ClickListener(this);

        onPageSelected(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onStop() {
        super.onStop();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainEvent event) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
                break;
            case 1:
            case 2:
            case 3:
                status_bar.setBackgroundResource(R.color.white);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onTab3Click() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        return true;
    }
}