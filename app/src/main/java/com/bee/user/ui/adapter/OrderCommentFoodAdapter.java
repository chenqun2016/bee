package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  20：01
 * 描述：
 */
public class OrderCommentFoodAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OrderCommentFoodAdapter() {
        super(R.layout.item_order_comment_food);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        TextView view = baseViewHolder.findView(R.id.tv_name);
        view.setText(s);
    }
}
