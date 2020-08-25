package com.huaxiafinance.www.crecyclerview.cindicatorview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CViewPager extends ViewPager {

    private boolean canScroll;

    public CViewPager(Context context) {
        super(context);
        canScroll = true;
    }

    public CViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        canScroll = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canScroll) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (!canScroll) {
            return false;
        }
        return super.onTouchEvent(evt);
    }

//    @Override
//    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if (v != this && v instanceof ViewPager) {
//            return true;
//        }
//        return super.canScroll(v, checkV, dx, x, y);
//    }

}
