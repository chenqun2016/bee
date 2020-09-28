package com.bee.user.ui.adapter;

import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  15：45
 * 描述：
 */
public class ShouHouItemAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
    public ShouHouItemAdapter(@Nullable List<FoodBean> data) {
        super(R.layout.item_shouhou_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodBean foodBean) {
//        TextView pricepast = baseViewHolder.findView(R.id.iv_goods_price_past);
//
//        DisplayUtil.setXiexian(pricepast);

        TextView iv_goods_name = baseViewHolder.findView(R.id.iv_goods_name);
        iv_goods_name.setText("精品海鲜捞面");

    }
}