package com.huaxiafinance.lc.bottomindicator.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by chenqun on 2016/8/18.
 */
public class CustomViewPager extends ViewPager {

	private boolean canScroll;

	public CustomViewPager(Context context) {
		super(context);
		canScroll = true;
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (canScroll) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	public void toggleLock() {
		canScroll = !canScroll;
	}

	public void setCanScroll(boolean canScroll) {
		this.canScroll = canScroll;
	}

	public boolean isCanScroll() {
		return canScroll;
	}

//    @Override
//    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if (v != this && v instanceof ViewPager) {
//            return true;
//        }
//        return super.canScroll(v, checkV, dx, x, y);
//    }
}
