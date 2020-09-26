package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/26  21：19
 * 描述：
 */
public class OrderCancelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public int selected = 0;

    public OrderCancelAdapter() {
        super(R.layout.item_order_cancel);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        TextView tv_reason = baseViewHolder.findView(R.id.tv_reason);
        tv_reason.setText(s);

        ImageView iv_selected = baseViewHolder.findView(R.id.iv_selected);

        if(selected == baseViewHolder.getLayoutPosition()){
            iv_selected.setVisibility(View.VISIBLE);
        }else{
            iv_selected.setVisibility(View.INVISIBLE);
        }
    }
}
