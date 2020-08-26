package com.huaxiafinance.www.crecyclerview.cindicatorview.indicator;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.huaxiafinance.www.crecyclerview.R;

public class TitleIndicator extends BaseIndicator {
    private int mTextSize = 16;
    private int mTextColorResId;
    private int mTextSelectedSize = 16;
    private boolean mBold;

    public TitleIndicator(Context context) {
        this(context, null);
    }

    public TitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getTabItemView(PagerAdapter adapter, int position) {
        String pageTitle = adapter.getPageTitle(position).toString();
        if (pageTitle.contains("/")) {
            String[] split = pageTitle.split("/");
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.setLayoutParams(layoutParams);


            TextView view = new TextView(getContext());
            view.setText(split[0]+"");
            view.setTextSize(19);
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.WHITE);
            linearLayout.addView(view);

            view = new TextView(getContext());
            view.setText(split[1]+"");
            view.setTextSize(13);
            view.setBackgroundResource(R.drawable.bg_round_white_nexttitle);
            view.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.topMargin = dip2px(getContext(),4);
            view.setPadding(dip2px(getContext(),7),dip2px(getContext(),1),dip2px(getContext(),7),dip2px(getContext(),1));
            view.setLayoutParams(layoutParams);

            view.setTextColor(getResources().getColor(R.color.FF6200));
            linearLayout.addView(view);
            return linearLayout;
        } else {
            TextView view = new TextView(getContext());
            view.setText(adapter.getPageTitle(position));
            view.setTextSize(mTextSize);
            view.setGravity(Gravity.CENTER);
            if (mTextColorResId != 0) {
                view.setTextColor(getResources().getColorStateList(mTextColorResId));
            }
            return view;
        }
    }

    // 将dip或dp值转换为px值，保证尺寸大小不变
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // 将px值转换为sp值，保证文字大小不变
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    @Override
    protected void drawItemUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        if (paint != null) {
            float s = (right - left) / 4;
            canvas.drawRect(left + s, top - 10, right - s, bottom - 10, paint);
        }
    }

    @Override
    protected void drawTabUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    protected void drawDivider(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public int getTextColorResId() {
        return mTextColorResId;
    }

    public void setTextColorResId(int mTextColorResId) {
        this.mTextColorResId = mTextColorResId;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setTextSelectedSize(int mTextSelectedSize) {
        this.mTextSelectedSize = mTextSelectedSize;
    }

    public void setTextBold(boolean bold) {
        this.mBold = bold;
    }

    @Override
    public void onTabSelected(View child, int i, int mCurrentPosition) {
        super.onTabSelected(child, i, mCurrentPosition);
        if (child instanceof TextView) {
            TextView view = (TextView) child;
            if (i == mCurrentPosition) {
                view.setTextSize(mTextSelectedSize);
            } else {
                view.setTextSize(mTextSize);
            }
            view.getPaint().setFakeBoldText(mBold);
        }else if(child instanceof LinearLayout){
            LinearLayout parent = (LinearLayout) child;
            TextView child1 = (TextView) parent.getChildAt(0);
            TextView child2 = (TextView) parent.getChildAt(1);
            if (i == mCurrentPosition) {
                child1.setTextColor(Color.WHITE);
                child2.setTextColor(getResources().getColor(R.color.FF6200));
                child2.setBackgroundResource(R.drawable.bg_round_white_nexttitle);
            } else {
                child1.setTextColor(getResources().getColor(R.color.white_half));
                child2.setTextColor(getResources().getColor(R.color.white_half));
                child2.setBackgroundDrawable(null);
            }
        }
    }
}
