package com.bee.user.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

public abstract class BaseCountDownTimerView extends LinearLayoutCompat {

	private Context mContext;

	/**
	 * 倒计时控制器
	 */
	private CountDownTimer mCountDownTimer;
	
	private OnCountDownTimerListener OnCountDownTimerListener;

	private long mMillis;

	/**
	 * 时
	 */
	private AppCompatTextView mHourTextView;

	/**
	 * 分
	 */
	private AppCompatTextView mMinTextView;

	/**
	 * 秒
	 */
	private AppCompatTextView mSecondTextView;

	/**
	 * 获取边框颜色
	 * 
	 * @return
	 */
	protected abstract int getStrokeColor();

	/**
	 * 设置背景色
	 * 
	 * @return
	 */
	protected abstract int getBackgroundColor();

	/**
	 * 获取文字颜色
	 * 
	 * @return
	 */
	protected abstract int getTextColor();

	/**
	 * 获取边框圆角
	 * 
	 * @return
	 */
	protected abstract int getCornerRadius();

	/**
	 * 获取标签文字大小
	 * 
	 * @return
	 */
	protected abstract int getTextSize();

	public BaseCountDownTimerView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public BaseCountDownTimerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseCountDownTimerView(Context context) {
		this(context, null);
	}

	private void init() {
		this.setOrientation(HORIZONTAL);// 设置布局排列方式
		createView();// 创造三个标签
		addLabelView();// 添加标签到容器中
	}

	/**
	 * 创建时、分、秒的标签
	 */
	private void createView() {
		mHourTextView = createLabel();
		mMinTextView = createLabel();
		mSecondTextView = createLabel();
	}

	/**
	 * 添加标签到容器中
	 */
	private void addLabelView() {
		removeAllViews();
		this.addView(mHourTextView);
		this.addView(createColon());
		this.addView(mMinTextView);
		this.addView(createColon());
		this.addView(mSecondTextView);
	}

	/**
	 * 创建冒号
	 * 
	 * @return
	 */
	private AppCompatTextView createColon() {
		AppCompatTextView textView = new AppCompatTextView(mContext);
		textView.setTextColor(Color.BLACK);
		textView.setText(":");
		return textView;
	}

	/**
	 * 创建标签
	 * 
	 * @return
	 */
	private AppCompatTextView createLabel() {
		AppCompatTextView textView = new GradientTextView(mContext)
				.setTextColor(mContext.getResources().getColor(getTextColor()))
				.setStrokeColor(mContext.getResources().getColor(getStrokeColor()))
				.setBackgroundColor(mContext.getResources().getColor(getBackgroundColor()))
				.setTextSize(getTextSize()).setStrokeRadius(getCornerRadius())
				.build();
		textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		return textView;
	}
	
	/**
	 * 创建倒计时
	 */
	private void createCountDownTimer() {
		mCountDownTimer = new CountDownTimer(mMillis, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				setSecond(millisUntilFinished);// 设置秒
			}

			@Override
			public void onFinish() {
				if(OnCountDownTimerListener != null) {
					OnCountDownTimerListener.onFinish();
				}
			}
		};
	}

	/**
	 * 设置秒
	 * 
	 * @param millis
	 */
	private void setSecond(long millis) {
		String second = (int) (millis / 1000) + "";// 秒
		long totalMinutes = millis / 1000;
		String minute = (int) (totalMinutes / 60) + "";// 分
		long totalHours = totalMinutes / 60;
		String hour = (int) (totalHours / 24) + "";// 时
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		if (second.length() == 1) {
			second = "0" + second;
		}
		mHourTextView.setText(hour);
		mMinTextView.setText(minute);
		mSecondTextView.setText(second);
	}

	/**
	 * 设置监听事件
	 * @param listener
	 */
	public void setDownTimerListener(OnCountDownTimerListener listener){
		this.OnCountDownTimerListener=listener;
	}
	
	/**
	 * 设置时间值
	 * 
	 * @param millis
	 */
	public void setDownTime(long millis) {
		this.mMillis = millis;
		if(mMillis==0) {
			setSecond(millis);
		}
	}

	/**
	 * 开始倒计时
	 */
	public void startDownTimer() {
		createCountDownTimer();// 创建倒计时
		mCountDownTimer.start();
	}

	public void cancelDownTimer() {
		mCountDownTimer.cancel();
		//mCountDownTimer = null;
	}

	public CountDownTimer getCountDownTimer(){
		return mCountDownTimer;
	}

}
