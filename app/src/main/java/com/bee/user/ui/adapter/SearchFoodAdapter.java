package com.bee.user.ui.adapter;

import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/29  20：45
 * 描述：
 */
public class SearchFoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> implements LoadMoreModule {
    public SearchFoodAdapter() {
        super(R.layout.item_store_food);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodBean foodBean) {
        TextView pricepast = baseViewHolder.findView(R.id.iv_goods_price_past);

        DisplayUtil.setXiexian(pricepast);

        TextView iv_goods_name = baseViewHolder.findView(R.id.iv_goods_name);
        iv_goods_name.setText("精品海鲜捞面");

    }
}
