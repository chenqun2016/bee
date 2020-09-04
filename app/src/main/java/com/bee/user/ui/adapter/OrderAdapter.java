package com.bee.user.ui.adapter;

import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  21：47
 * 描述：
 */
public class OrderAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public OrderAdapter( ) {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StoreBean storeBean) {

    }
}
