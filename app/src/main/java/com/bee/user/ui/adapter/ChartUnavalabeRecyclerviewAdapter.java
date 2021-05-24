package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2021/5/23
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class ChartUnavalabeRecyclerviewAdapter extends BaseQuickAdapter<ChartBean, BaseViewHolder> {
    public ChartUnavalabeRecyclerviewAdapter() {
        super(R.layout.item_cart_unavalable_food);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ChartBean chartBean) {
        View view_line = baseViewHolder.findView(R.id.view_line);
        View ll_title = baseViewHolder.findView(R.id.ll_title);
        TextView tv_store = baseViewHolder.findView(R.id.tv_store);
        int itemPosition = getItemPosition(chartBean);
        if(itemPosition == 0 || !getItem(itemPosition - 1).getStoreId().equals(chartBean.getStoreId())){
            view_line.setVisibility(View.VISIBLE);
            ll_title.setVisibility(View.VISIBLE);
            tv_store.setText(chartBean.getStoreName());
        }else{
            view_line.setVisibility(View.GONE);
            ll_title.setVisibility(View.GONE);
        }




        TextView iv_goods_name = baseViewHolder.findView(R.id.iv_goods_name);
        iv_goods_name.setText(""+chartBean.getProductName());


        TextView iv_goods_price_past = baseViewHolder.findView(R.id.iv_goods_price_past);
        DisplayUtil.setXiexian(iv_goods_price_past);

        ImageView iv_goods_img = baseViewHolder.findView(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(chartBean.getProductPic())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(),10),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_goods_img);
        TextView iv_goods_detail = baseViewHolder.findView(R.id.iv_goods_detail);
        TextView iv_goods_price = baseViewHolder.findView(R.id.iv_goods_price);
        iv_goods_price.setText(chartBean.getPrice()+"");
        TextView iv_goods_num = baseViewHolder.findView(R.id.iv_goods_num);
        iv_goods_num.setText(""+chartBean.getQuantity());
    }
}
