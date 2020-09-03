package com.bee.user.ui.adapter;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  15：32
 * 描述：
 */
public class SelectedFoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {

    public SelectedFoodAdapter( @Nullable List<FoodBean> data) {
        super(R.layout.item_store_food, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodBean foodBean) {

    }
}
