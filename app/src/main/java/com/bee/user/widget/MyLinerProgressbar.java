package com.bee.user.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.bee.user.R;


/**
 * Created by chenqun on 2016/8/5.
 */
public class MyLinerProgressbar extends View {

    private float mAnimDuration = 3000;
    private float progress = 0;
    private Context mContext;
    private Paint mPaint;// 渐变色环画笔
    private Paint mPaintBg;
    private Paint mPaintText;

    private final int[] mColors = new int[]{
            0xff729dfe, 0xff95c2ff};// 渐变色环颜色
    private float mWidth;
    private float mHeight;

    private RectF rNew = new RectF();
    private RectF rBackground = new RectF();

    private ViewAnim mViewAnim;

    private int newProgress;
    private int mAlpha = 0;
    private float oriProgress = 0;


    private float padding = 0;//距离左右多少
    private float radio ;//圆边半径

    public MyLinerProgressbar(Context context) {
        super(context, null);
    }

    public MyLinerProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MyLinerProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(int prog) {
        if (prog < 0) prog = 0;
        if (prog > 100) prog = 100;
        Log.e("chenqun", "progress==" + prog);
        mAlpha = 0;
        float newDuration;
        float more = prog - progress;
        if (more < 50) {
            newDuration = mAnimDuration / 2f;
        } else {
            newDuration = Math.abs(more * mAnimDuration / 100f);
        }

        this.newProgress = prog;
        this.oriProgress = progress;

        if (null != mViewAnim)
            clearAnimation();

        assert mViewAnim != null;
        mViewAnim.setDuration((long) newDuration);
        startAnimation(mViewAnim);
    }

    private void init() {
        radio = dip2px(2);

        mPaint = new Paint();
        mPaintBg = new Paint();
        mPaintText = new Paint();

        mViewAnim = new ViewAnim();
        mViewAnim.setDuration((long) mAnimDuration);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(40);
        mPaint.setColor(Color.WHITE);


        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaintBg.setStrokeWidth(40);
        mPaintBg.setColor(getResources().getColor(R.color.color_B4785B));

        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setStrokeWidth(4);
        mPaintText.setTextSize(dip2px(13));
        mPaintText.setColor(getResources().getColor(R.color.color_B77A5C));


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
//
//        Shader shader = new LinearGradient(getPaddingLeft(), 0, mWidth - getPaddingRight(), mHeight, mColors, null, Shader.TileMode.CLAMP);
//        mPaint.setShader(shader);
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        padding = getPaddingLeft();

        mPaintText.setAlpha(255);
        String curlv = ""+(int) progress ;

        float fontLength = getFontlength(mPaintText, curlv);
        float right = (progress * (mWidth - padding - padding) / 100) + padding;
        float textRight;
        if (right <= fontLength / 2f) {
            textRight = padding;
        } else if (right >= (mWidth - padding - (fontLength / 2f))) {
            textRight = mWidth - padding - fontLength ;
        } else {
            textRight = right - (fontLength / 2f);
        }
        canvas.drawText(curlv, textRight, mHeight - dip2px(14), mPaintText);

        rBackground.left = padding;
        rBackground.right = mWidth - padding;
        rBackground.top = mHeight - dip2px(4);
        rBackground.bottom = mHeight;

//        canvas.drawRect(rBackground, mPaintBg);
        canvas.drawRoundRect(rBackground, radio, radio, mPaintBg);//绘制圆角矩形

        rNew.left = padding;
        rNew.right = right;
        rNew.top = mHeight - dip2px(4);
        rNew.bottom = mHeight;
//        canvas.drawRect(rNew, mPaint);
        canvas.drawRoundRect(rNew, radio, radio, mPaint);
    }

    private class ViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float v = newProgress - oriProgress;
            progress = oriProgress + v * interpolatedTime;
            invalidate();
        }
    }

    // 将dip或dp值转换为px值，保证尺寸大小不变
    public int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

}