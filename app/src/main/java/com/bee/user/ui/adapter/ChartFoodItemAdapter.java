package com.bee.user.ui.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  16：29
 * 描述：
 */
public class ChartFoodItemAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
    public ChartFoodItemAdapter(@Nullable List<FoodBean> data) {
        super(R.layout.item_chart_food, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodBean foodBean) {
        TextView pricepast = baseViewHolder.findView(R.id.iv_goods_price_past);
        pricepast.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );

        TextView iv_goods_name = baseViewHolder.findView(R.id.iv_goods_name);
        iv_goods_name.setText("精品海鲜捞面");

    }
}
