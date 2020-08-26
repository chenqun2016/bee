package com.huaxiafinance.www.crecyclerview.cindicatorview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.huaxiafinance.www.crecyclerview.R;
import com.huaxiafinance.www.crecyclerview.cindicatorview.indicator.TitleIndicator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chenqun on 2017/2/20.
 */

public class CIndicatorView extends FrameLayout {
    private CViewPager mViewpager;
    private TitleIndicator mIndicator;
    private static final int pageLimits = 3;//viewpage单边最多缓存页面的数量

    public CIndicatorView(Context context) {
        super(context);
        init();
    }

    public CIndicatorView(@NonNull Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private LinearLayout ll_content;

    private void init() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.crecyclerview_indicator, this, true);
        ll_content = (LinearLayout) layout.findViewById(R.id.ll_content);
        mIndicator = (TitleIndicator) layout.findViewById(R.id.indicator);
        mViewpager = (CViewPager) layout.findViewById(R.id.cindicator_viewpager);

        mIndicator.setTextSize(14);
        mIndicator.setItemUnderHeight(4);
        mIndicator.setIsAllInScreen(true);
//        mIndicator.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator.setItemUnderColor(getResources().getColor(R.color.tab_text_selecteda));
        mIndicator.setTabUnderColor(Color.WHITE);
//        mIndicator.setTabUnderHeight(1);
        mIndicator.setTextColorResId(R.color.tab_text_selector_cr);

    }

    public int getCurrentPosition() {
        return mViewpager.getCurrentItem();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mIndicator.setOnPageChangeListener(listener);
    }

    public void setCurrentIndex(int index) {
        mIndicator.setCurrentItem(index);
    }

    public void addView(View view, int index) {
        ll_content.addView(view, index);
    }

    // 将dip或dp值转换为px值，保证尺寸大小不变
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public TitleIndicator getIndicator() {
        return mIndicator;
    }


    /**
     * TODO 初始化数据 必须调用
     *
     * @param fragments
     * @param titles
     * @param manager   父布局是1.Fragment 使用getChildFragmentManager()  不可使用 getFragmentManager()
     *                  2.Activity 使用getSupportFragmentManager()
     *                  Fragment 中嵌套 Fragment 会出现问题的解决方案
     * @return
     */
    public CIndicatorView initDatas(List<Fragment> fragments, String[] titles, FragmentManager manager) {
        mViewpager.setAdapter(new FragmentAdapter(manager, fragments, Arrays.asList(titles)));
        mViewpager.setOffscreenPageLimit(Math.min(fragments.size() - 1, pageLimits));
        mIndicator.setViewPager(mViewpager);
        return this;
    }
    public ViewPager getViewPager(){
        return mViewpager;
    }

    public void setIndicatorVisible(boolean b) {
        if (null != mIndicator) {
            mIndicator.setVisibility(b ? VISIBLE : GONE);
        }
    }


    final class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;
        private List<String> mTitles;

        FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.mFragments = fragments;
            this.mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments==null?0:mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

    }
}
