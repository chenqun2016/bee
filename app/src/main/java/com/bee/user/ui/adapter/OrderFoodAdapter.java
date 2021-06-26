package com.bee.user.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/05  20：18
 * 描述：
 */
public class OrderFoodAdapter extends BaseQuickAdapter<FoodBean,BaseViewHolder> {

    public OrderFoodAdapter( @Nullable List<FoodBean> data) {
        super(R.layout.item_ordering_food, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodBean foodBean) {
        TextView iv_goods_name = baseViewHolder.findView(R.id.iv_goods_name);
        iv_goods_name.setText(foodBean.productName+"");

        TextView iv_goods_price_past = baseViewHolder.findView(R.id.iv_goods_price_past);
        DisplayUtil.setXiexian(iv_goods_price_past);

        ImageView iv_goods_img = baseViewHolder.findView(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(foodBean.productPic)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_goods_img);
    }
}
