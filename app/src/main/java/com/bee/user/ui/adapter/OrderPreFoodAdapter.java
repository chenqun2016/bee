package com.bee.user.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 创建时间：2021/5/15
 * 编写人： 陈陈陈
 * 功能描述：
 */
class OrderPreFoodAdapter extends BaseQuickAdapter<OrderingConfirmBean.StoreOrderConfirmItemsBean.ProductsBean,BaseViewHolder> {

    public OrderPreFoodAdapter( @Nullable List<OrderingConfirmBean.StoreOrderConfirmItemsBean.ProductsBean> data) {
        super(R.layout.item_ordering_food, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderingConfirmBean.StoreOrderConfirmItemsBean.ProductsBean foodBean) {
        TextView iv_goods_name = baseViewHolder.findView(R.id.iv_goods_name);
        iv_goods_name.setText(""+foodBean.getProductName());


        TextView iv_goods_price_past = baseViewHolder.findView(R.id.iv_goods_price_past);
        DisplayUtil.setXiexian(iv_goods_price_past);

        ImageView iv_goods_img = baseViewHolder.findView(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(foodBean.getProductPic())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(),10),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_goods_img);
        TextView iv_goods_detail = baseViewHolder.findView(R.id.iv_goods_detail);
        TextView iv_goods_price = baseViewHolder.findView(R.id.iv_goods_price);
        iv_goods_price.setText(foodBean.getPrice()+"");
        TextView iv_goods_num = baseViewHolder.findView(R.id.iv_goods_num);
        iv_goods_num.setText(""+foodBean.getQuantity());
    }
}
