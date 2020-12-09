package com.bee.user.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bee.user.R;
import com.bee.user.ui.base.fragment.BaseViewPagerAdapter;
import com.huaxiafinance.lc.bottomindicator.IconPagerAdapter;

import java.util.List;


/**
 * Created by chenqun on 2017/5/11.
 */

public class MainAdapter extends BaseViewPagerAdapter implements IconPagerAdapter {
    private static String[] strs = {"首页", "附近",  "购物车","我的"};
    private static int[] ints = {R.drawable.tab_home_selector, R.drawable.tab_nearby_selector,  R.drawable.tab_shopping_selector,  R.drawable.tab_mine_selector};
    private List<Fragment> mFragments;

    public MainAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getIconResId(int index) {
        return ints[index];
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strs[position];
    }

    @Override
    public int getItemPosition(Object object) {


        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
