package com.bee.user.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.bee.user.utils.LogUtil;


/**
 * Created by chenqun on 2016/10/28.
 */
public class DragDialogLayout extends LinearLayout {
    /* 拖拽工具类 */
    private final ViewDragHelper mDragHelper;
    private GestureDetectorCompat gestureDetector;
    private View childView;
    private int viewHeight;
    private static final int VEL_THRESHOLD = 300; // 滑动速度的阈值，超过这个绝对值认为是上下
    private static final int DISTANCE_THRESHOLD = 300; // 单位是像素，当上下滑动速度不够时，通过这个阈值来判定是应该粘到顶部还是底部
    private int downTop1; // 手指按下的时候，frameView1的getTop值
    private int mTop;
    private int mBotoom;

    public void setmCanDrag(boolean mCanDrag) {
        this.mCanDrag = mCanDrag;
    }

    private boolean mCanDrag = true;

    public DragDialogLayout(Context context) {
        this(context,null);
    }

    public DragDialogLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragDialogLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper
                .create(this, 10f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
        gestureDetector = new GestureDetectorCompat(context,new YScrollDetector());
    }
    @Override
    protected void onFinishInflate() {
        childView = getChildAt(0);
        super.onFinishInflate();
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        childView = getChildAt(0);
        invalidate();
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
                                float dy) {
            // 垂直滑动时dy>dx，才被认定是上下拖动
            if(Math.abs(dy) > Math.abs(dx)){
                return e2.getY()>e1.getY();
            }
            return  false;
        }
    }
    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        if(null != childView){
            viewHeight = childView.getMeasuredHeight();

//        childView.layout(l,b-viewHeight,r,b);
            mTop = childView.getTop();
            mBotoom = childView.getBottom();
        }
    }

    /**
     * 这是View的方法，该方法不支持android低版本（2.2、2.3）的操作系统，所以手动复制过来以免强制退出
     */
    public static int resolveSizeAndState(int size, int measureSpec,
                                          int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result | (childMeasuredState & MEASURED_STATE_MASK);
    }

    /**
     * 这是拖拽效果的主要逻辑
     */
    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            if(top == mBotoom){
                if (null != nextPageListener) {
                    nextPageListener.onDragNext();
                }
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 两个子View都需要跟踪，返回true
            return true;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            // 这个用来控制拖拽过程中松手后，自动滑行的速度，暂时给一个随意的数值
            return 1;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 滑动松开后，需要向上或者乡下粘到特定的位置
            animTopOrBottom(releasedChild, yvel);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int finalTop = top;
                if (top < mTop) {
                    finalTop = mTop;
                }
            // finalTop代表的是理论上应该拖动到的位置。此处计算拖动的距离除以一个参数(3)，是让滑动的速度变慢。数值越大，滑动的越慢
            return child.getTop() + (finalTop - child.getTop());
        }
    }


    private void animTopOrBottom(View releasedChild, float yvel) {
        if (null != nextPageListener) {
            nextPageListener.onAnimTopOrBottom();
        }
        int finalTop = mTop; // 默认是粘到最顶端
       if (releasedChild == childView){
            // 拖动第二个view松手
            if (yvel > VEL_THRESHOLD
                    || (releasedChild.getTop()-mTop > DISTANCE_THRESHOLD)) {
                // 保持原地不动
                finalTop = viewHeight+mTop;

            }
        }

        if (mDragHelper.smoothSlideViewTo(releasedChild, 0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }
    /* touch事件的拦截与处理都交给mDraghelper来处理 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (childView != null && childView.getBottom() > mBotoom && childView.getTop() < mBotoom) {
            // view粘到顶部或底部，正在动画中的时候，不处理touch事件
            return false;
        }

        boolean yScroll = gestureDetector.onTouchEvent(ev);
        boolean shouldIntercept = false;
        try {
            shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        }
        catch (Exception e) {e.printStackTrace();}
        int action = ev.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            // action_down时就让mDragHelper开始工作，否则有时候导致异常 他大爷的
            mDragHelper.processTouchEvent(ev);
            if(null != childView){
                downTop1 = childView.getTop();
            }
        }

        return shouldIntercept && yScroll  && mCanDrag;
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // 统一交给mDragHelper处理，由DragHelperCallback实现拖动效果
        try {
            mDragHelper.processTouchEvent(e); // 该行代码可能会抛异常，正式发布时请将这行代码加上try catch
        }catch (Exception es){
            LogUtil.e(es.getMessage());
        }
        return true;
    }
    private ShowNextPageNotifier nextPageListener;
    public void setNextPageListener(ShowNextPageNotifier nextPageListener) {
        this.nextPageListener = nextPageListener;
    }

    public interface ShowNextPageNotifier {
        void onDragNext();
        void onAnimTopOrBottom();
    }
}
