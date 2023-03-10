package com.bee.user.ui.adapter;

import android.graphics.Paint;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.GoodsBySectionBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/12/12 下午2:47
 */
public class LunchAdapter extends BaseQuickAdapter<GoodsBySectionBean.RecordBean, BaseViewHolder>  {

    public LunchAdapter() {
        super(R.layout.item_food_chart);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GoodsBySectionBean.RecordBean bean) {

        AppCompatImageView tvImage = baseViewHolder.getView(R.id.tv_image);
        AppCompatImageView ivIcon = baseViewHolder.getView(R.id.iv_icon);
        AppCompatTextView tvMoneypast = baseViewHolder.getView(R.id.tv_moneypast);
        tvMoneypast.getPaint().setFlags( Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        Picasso.with(getContext())
                .load(bean.getPic())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),10),0, PicassoRoundTransform.CornerType.TOP))
                .centerCrop()
                .into(tvImage);
        Picasso.with(getContext())
                .load(bean.getStoreLogo())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                .into(ivIcon);
        baseViewHolder.setText(R.id.tv_money,"￥"+bean.getPrice())
                .setText(R.id.tv_moneypast,"￥"+bean.getOriginalPrice())
                .setText(R.id.tv_title,bean.getSubTitle())
                .setText(R.id.tv_tag,String.format(getContext().getString(R.string.tv_remain_bumber),bean.getStock()+""))
                .setText(R.id.tv_des,bean.getDistanceAndTime())
                .setText(R.id.tv_sells,String.format(getContext().getString(R.string.tv_sale_number),bean.getSale()+""));
    }
}
