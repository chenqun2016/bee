package com.bee.user.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SecondDownTimerView extends BaseCountDownTimerView{

	public SecondDownTimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SecondDownTimerView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SecondDownTimerView(Context context) {
		this(context,null);
	}

	@Override
	protected String getStrokeColor() {
		return "FF5050";
	}

	@Override
	protected String getTextColor() {
		return "FFFEFE";
	}

	@Override
	protected int getCornerRadius() {
		return 2;
	}

	@Override
	protected int getTextSize() {
		return 25;
	}

	@Override
	protected String getBackgroundColor() {
		return "FF5050";
	}
	
}
