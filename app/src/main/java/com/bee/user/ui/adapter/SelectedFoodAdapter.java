package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.AddRemoveView;
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
public class SelectedFoodAdapter extends BaseQuickAdapter<ChartBean, BaseViewHolder> {

    public SelectedFoodAdapter( @Nullable List<ChartBean> data) {
        super(R.layout.item_store_food, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ChartBean foodBean) {
        TextView iv_goods_price_past = holder.findView(R.id.iv_goods_price_past);
        DisplayUtil.setXiexian(iv_goods_price_past);

        TextView iv_goods_name = holder.findView(R.id.iv_goods_name);
        iv_goods_name.setText(foodBean.getProductName());




        holder.findView(R.id.iv_goods_comment).setVisibility(View.INVISIBLE);

        TextView iv_goods_detail = holder.findView(R.id.iv_goods_detail);
        iv_goods_detail.setText(foodBean.getSp1()+"/"+foodBean.getSp2()+"/"+foodBean.getSp3());

        TextView iv_goods_price = holder.findView(R.id.iv_goods_price);
        AddRemoveView iv_goods_add = holder.findView(R.id.iv_goods_add);

        iv_goods_price.setText(foodBean.getPrice()+"");
        iv_goods_price_past.setText(foodBean.getPrice()+"");

        iv_goods_add.setNum(foodBean.getQuantity());
    }
}
