package com.bee.user.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2021/12/12
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class DragRecyclerView extends RecyclerView {
    public DragRecyclerView(@NonNull @NotNull Context context) {
        super(context);
    }

    public DragRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(computeVerticalScrollOffset() > 0){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        LogUtil.d("getScrollY()=="+computeVerticalScrollOffset());
        return super.dispatchTouchEvent(ev);
    }
}
