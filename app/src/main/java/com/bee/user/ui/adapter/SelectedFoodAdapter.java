package com.bee.user.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.AddRemoveView;
import com.bee.user.widget.ChartBottomDialogView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  15：32
 * 描述：
 */
public class SelectedFoodAdapter extends BaseQuickAdapter<ChartBean, BaseViewHolder> {

    public ChartBottomDialogView.ChartBottomDialogListener listener;

    public SelectedFoodAdapter(@Nullable List<ChartBean> data, ChartBottomDialogView.ChartBottomDialogListener listener) {
        super(R.layout.item_store_food, data);
        this.listener = listener;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ChartBean foodBean) {
        TextView iv_goods_price_past = holder.findView(R.id.iv_goods_price_past);
        DisplayUtil.setXiexian(iv_goods_price_past);

        ImageView iv_goods_img = holder.findView(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(foodBean.getProductPic())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(), 10), 0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_goods_img);
        TextView iv_goods_name = holder.findView(R.id.iv_goods_name);
        iv_goods_name.setText(foodBean.getProductName());




        holder.findView(R.id.iv_goods_comment).setVisibility(View.INVISIBLE);

        TextView iv_goods_detail = holder.findView(R.id.iv_goods_detail);
        if(!TextUtils.isEmpty(foodBean.attributes)){
            String s =  foodBean.attributes.replaceAll(",", "/");
            iv_goods_detail.setText(s);
        }

        TextView iv_goods_price = holder.findView(R.id.iv_goods_price);
        AddRemoveView iv_goods_add = holder.findView(R.id.iv_goods_add);

        iv_goods_price.setText(foodBean.getPrice().intValue()+"");
        if(null != foodBean.marketPrice){
            iv_goods_price_past.setText(foodBean.marketPrice.intValue()+"");
        }

        iv_goods_add.setNum(foodBean.getQuantity());
        iv_goods_add.setOnNumChangedListener(new AddRemoveView.OnNumChangedListener() {
            @Override
            public boolean onAddListener(int num) {
                if(null != listener){
                    listener.onAdd(num,iv_goods_add,foodBean);
                }
                return false;
            }

            @Override
            public boolean onRemoveListener(int num) {
                if(null != listener){
                    listener.onRemove(num,iv_goods_add,foodBean);
                }
                return false;
            }
        });
    }
}
