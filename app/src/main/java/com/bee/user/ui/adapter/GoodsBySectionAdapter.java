package com.bee.user.ui.adapter;

import android.graphics.Paint;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.GoodsBySectionBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/7 下午3:41
 */
public class GoodsBySectionAdapter extends BaseQuickAdapter<GoodsBySectionBean.RecordBean, BaseViewHolder> implements LoadMoreModule {


    public GoodsBySectionAdapter() {
        super(R.layout.item_goods_section);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GoodsBySectionBean.RecordBean bean) {
        AppCompatImageView tvImage = baseViewHolder.getView(R.id.tv_image);
        AppCompatImageView ivIcon = baseViewHolder.getView(R.id.iv_icon);
        AppCompatTextView tvStatus = baseViewHolder.getView(R.id.tv_status);
        AppCompatTextView tvMoneypast = baseViewHolder.getView(R.id.tv_moneypast);
        tvMoneypast.getPaint().setFlags( Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        int status = bean.getStatus();//状态 0-进行中，1-未开始，2-已结束
        Picasso.with(getContext())
                .load(bean.getPic())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),10),0, PicassoRoundTransform.CornerType.TOP))
                .into(tvImage);
        Picasso.with(getContext())
                .load(bean.getStoreLogo())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                .into(ivIcon);
        baseViewHolder.setText(R.id.tv_money,"￥"+bean.getOriginalPrice())
                .setText(R.id.tv_moneypast,"￥"+bean.getSalePrice())
                .setText(R.id.tv_title,bean.getProductName())
        .setText(R.id.tv_tag,String.format(getContext().getString(R.string.tv_remain_bumber),bean.getRemainNumber()+""))
        .setText(R.id.tv_des,bean.getDistanceAndTime());
        switch (status) {
            case 0:
                tvStatus.setText("去抢购");
                tvStatus.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
                tvStatus.setClickable(true);
                break;
            case 1:
                tvStatus.setText("未开始");
                tvStatus.setBackgroundResource(R.drawable.btn_gradient_grey_round);
                tvStatus.setClickable(false);
                break;
            case 2:
                tvStatus.setText("已结束");
                tvStatus.setBackgroundResource(R.drawable.btn_gradient_grey_round);
                tvStatus.setClickable(false);
                break;
        }
    }
}
