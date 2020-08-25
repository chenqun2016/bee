package com.huaxiafinance.www.crecyclerview.cindicatorview.indicator;


import androidx.viewpager.widget.ViewPager;

public interface Indicatorable {
    interface IconPageAdapter {
        int getIconResId(int position);
    }

    BaseIndicator setViewPager(ViewPager view);

    void setViewPager(ViewPager view, int initialPosition);

    void setCurrentItem(int item);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    void notifyDataSetChanged();
}
