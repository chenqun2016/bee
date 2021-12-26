package com.bee.user.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bee.user.R;

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
	protected int getStrokeColor() {
		return R.color.color_FF5050;
	}

	@Override
	protected int getTextColor() {
		return R.color.color_FFFEFE;
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
	protected int getBackgroundColor() {
		return R.color.color_FF5050;
	}
	
}
