package com.bee.user.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.utils.DisplayUtil;

/**
 * 创建时间：2019/6/24
 * 编写人： chenqun
 * 功能描述：
 */
public class PinnedSectionDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "PinnedSectionDecoration";

    private DecorationCallback callback;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private Paint.FontMetrics fontMetrics;


    public PinnedSectionDecoration(Context context, DecorationCallback decorationCallback) {
        Resources res = context.getResources();
        this.callback = decorationCallback;

        paint = new Paint();
        paint.setColor(res.getColor(R.color.background_default));

        textPaint = new TextPaint();
//        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DisplayUtil.dip2px(context,14));
        textPaint.setColor(context.getResources().getColor(R.color.color_282525));
        textPaint.getFontMetrics(fontMetrics);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap = res.getDimensionPixelSize(R.dimen.dimen_45dp);

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
//        String groupId = callback.getGroupText(pos);
//        if (TextUtils.isEmpty(groupId) ) return;
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;

        String preGroupId, groupId = "";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupText(position);
            if (TextUtils.isEmpty(groupId) || groupId.equals(preGroupId) ) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                String nextGroupId = callback.getGroupText(position + 1);
                if (!nextGroupId.equals(groupId)  && viewBottom < textY ) {//组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
            c.drawRect(left, textY - topGap, right, textY, paint);

            float y = textY -  topGap * 0.5f +  getFontHeight(textPaint)*0.5f;
            c.drawText(groupId, left+DisplayUtil.dip2px(parent.getContext(),15), y, textPaint);
        }

    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }




    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String prevGroupId = callback.getGroupText(pos - 1);
            String groupId = callback.getGroupText(pos);
            return !prevGroupId .equals( groupId );
        }
    }

    public interface DecorationCallback {

        String getGroupText(int position);
    }
}