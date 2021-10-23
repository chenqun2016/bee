package com.huaxiafinance.lc.bottomindicator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.huaxiafinance.lc.bottomindicator.viewpager.CustomViewPager;

import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 *
 */
public class IconTabPageIndicator extends LinearLayout implements PageIndicator {
    /**
     * Title text used when no title is provided by the adapter.
     */
    private static final CharSequence EMPTY_TITLE = "";
    private int mOldIndex = -1;
    private IOnTab3Click iOnTab3Click;

    public void setOnTab3ClickListener(IOnTab3Click click) {
        iOnTab3Click = click;
    }

    public void updateIcon(List<MenuItemBean> stateListDrawables,int type) {
        final int tabCount = mTabLayout.getChildCount();
        if(tabCount <= stateListDrawables.size()){
            for (int i = 0; i < tabCount; i++) {
                 View child = mTabLayout.getChildAt(i);
                if(child instanceof  TabView){
                    TabView child1 = (TabView) child;
                    child1.setText(stateListDrawables.get(i).title);
                    child1.setIcon(stateListDrawables.get(i).drawable,stateListDrawables.get(i).width,stateListDrawables.get(i).height,type);
                    if(null != stateListDrawables.get(i).textColor){
                        child1.setTextColor(stateListDrawables.get(i).textColor);
                    }
                }
            }
        }
    }

    /**
     * Interface for a callback when the selected tab has been reselected.
     */
    public interface OnTabReselectedListener {
        /**
         * Callback when the selected tab has been reselected.
         *
         * @param position Position of the current center item.
         */
        void onTabReselected(int position);
    }

    private Runnable mTabSelector;

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            TabView tabView = (TabView) view;
            final int newSelected = tabView.getIndex();

            //TODO 设置tabMine的点击事件
            if(null != iOnTab3Click && 3 == newSelected && iOnTab3Click.onTab3Click()){
                return;
            }

            final int oldSelected = mViewPager.getCurrentItem();

            mOldIndex = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(newSelected, false);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }

            if(oldSelected != newSelected ){
                Drawable[] compoundDrawables = tabView.getCompoundDrawables();
                Drawable s =  compoundDrawables[1];
                if( s instanceof StateListDrawable){
                    StateListDrawable ss = (StateListDrawable) s;
                    Drawable current = ss.getCurrent();
                    if(current instanceof GifDrawable){
                        GifDrawable gif = (GifDrawable) current;
                        gif.reset();
                        gif.start();
                    }
                }
            }

        }
    };

    public final LinearLayout mTabLayout;

    private CustomViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private int mSelectedTabIndex;

    private OnTabReselectedListener mTabReselectedListener;

    private int mTabWidth;

    public IconTabPageIndicator(Context context) {
        this(context, null);
    }

    public IconTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);

        mTabLayout = new LinearLayout(context, null);
        mTabLayout.setBackgroundColor(Color.TRANSPARENT);
        addView(mTabLayout, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        mTabReselectedListener = listener;
    }

    public int getOldeIndex(){
        return mOldIndex;
    }
    public int getCurrentIndex(){
        return mSelectedTabIndex;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;

        final int childCount = mTabLayout.getChildCount();

        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            mTabWidth = MeasureSpec.getSize(widthMeasureSpec) / childCount;
        } else {
            mTabWidth = -1;
        }

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
        }
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private void addTab(int index, CharSequence text, int iconResId) {
        final TabView tabView = new TabView(getContext());
        tabView.mIndex = index;
        tabView.setOnClickListener(mTabClickListener);
        tabView.setText(text);
        tabView.setGravity(Gravity.CENTER);
        tabView.setPadding(0,dip2px(getContext(),5),0,0);
        tabView.setTextSize(11);
        tabView.setBackgroundColor(Color.TRANSPARENT);
        tabView.setTextColor(AppCompatResources.getColorStateList(getContext(),R.color.tab_text_selector));
        if (iconResId > 0) {
            tabView.setIcon(iconResId);
        }
        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        tabView.setGravity(Gravity.CENTER);
        mTabLayout.addView(tabView, layoutParams);
    }

    // 将dip或dp值转换为px值，保证尺寸大小不变
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentItemInside(arg0);
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    @Override
    public void setViewPager(CustomViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        IconPagerAdapter iconAdapter = null;
        if (adapter instanceof IconPagerAdapter) {
            iconAdapter = (IconPagerAdapter) adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            int iconResId = 0;
            if (iconAdapter != null) {
                iconResId = iconAdapter.getIconResId(i);
            }
            addTab(i, title, iconResId);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    @Override
    public void setViewPager(CustomViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        final int tabCount = mTabLayout.getChildCount();
        if(item >= tabCount){
            item = tabCount-1;
        }

        mOldIndex = mViewPager.getCurrentItem();
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item, false);


        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
    }

    public void setCurrentItemInside(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item, false);

        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    private class TabView extends pl.droidsonroids.gif.GifTextView {
        private int mIndex;

        private int iconWidth;
        private int iconHeight;
        private int iconWidth2;
        private int iconHeight2;

        public TabView(Context context) {
            super(context);
        }


        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
            if (mTabWidth > 0) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mTabWidth, MeasureSpec.EXACTLY),
                        heightMeasureSpec);
            }
        }

        public void setIcon(int resId) {
            if (resId > 0) {
                final Resources resources = getContext().getResources();
                Drawable icon = resources.getDrawable(resId);
                int width = iconWidth == 0 ? icon.getIntrinsicWidth() : iconWidth;
                int height = iconHeight == 0 ? icon.getIntrinsicHeight() : iconHeight;
                icon.setBounds(0, 0, width, height);
                setCompoundDrawables(null, icon, null, null);
                setCompoundDrawablePadding(dip2px(getContext(),0));
            }
        }

        public void setIcon(StateListDrawable icon,int widths,int heights,int type) {
            if (null != icon) {
                if(type == 3){//加载gif图片  宽高比不一样
                    icon.setBounds(0, 0, iconWidth2*8/9, iconWidth2*8/9);
                }else if(0 == widths || 0 == heights){
                    icon.setBounds(0, 0, iconWidth2, iconHeight2);
                }else{
                    icon.setBounds(0, 0, widths * iconHeight2 / heights, iconHeight2);
//                    icon.setBounds(0, 0, widths * iconHeight / heights, iconHeight);
                }
                setCompoundDrawables(null, icon, null, null);
                setCompoundDrawablePadding(dip2px(getContext(),0));
            }
        }

        public int getIndex() {
            return mIndex;
        }
    }
}
