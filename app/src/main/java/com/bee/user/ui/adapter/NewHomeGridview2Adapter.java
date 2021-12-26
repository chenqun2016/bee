package com.bee.user.ui.adapter;

import android.graphics.Paint;
import android.widget.ImageView;
import com.bee.user.R;
import com.bee.user.bean.HomeGridview2Bean;
import com.bee.user.widget.OnCountDownTimerListener;
import com.bee.user.widget.SecondDownTimerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/24  15：53
 * 描述：
 */
public class NewHomeGridview2Adapter extends BaseQuickAdapter<HomeGridview2Bean, BaseViewHolder> {


    public NewHomeGridview2Adapter() {
        super(R.layout.item_home_gridview2);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, HomeGridview2Bean bean) {
       ImageView tvImage =  viewHolder.getView(R.id.tv_image);
        viewHolder.setText(R.id.tv_title,bean.title)
                .setText(R.id.tv_name,bean.name);
        switch (bean.title){
            case "限时秒杀":
                AppCompatTextView tvMoneypast = viewHolder.getView(R.id.tv_moneypast);
                SecondDownTimerView tvTime = viewHolder.getView(R.id.tv_time);
                tvMoneypast.getPaint().setFlags( Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
                viewHolder.setVisible(R.id.tv_moneypast,true)
                        .setGone(R.id.tv_time,false)
                        .setText(R.id.tv_moneypast,"￥"+bean.moneypast)
                .setText(R.id.tv_money,"￥"+bean.money)
                 .setTextColor(R.id.tv_money,getContext().getResources().getColor(R.color.color_FF5050));
                if(tvTime.getCountDownTimer() != null) {
                    tvTime.cancelDownTimer();
                }
                tvTime.setDownTime(bean.time);
                if(bean.time>0) {
                    tvTime.startDownTimer();
                }
                break;
            case "精选午餐":
            case "销量排行榜":
                viewHolder.setGone(R.id.tv_moneypast,true)
                        .setGone(R.id.tv_time,true)
                        .setText(R.id.tv_money,"￥"+bean.money)
                        .setTextColor(R.id.tv_money,getContext().getResources().getColor(R.color.color_FF5050));
                break;
            case "附近好店":
                viewHolder.setGone(R.id.tv_moneypast,true)
                        .setGone(R.id.tv_time,true)
                        .setText(R.id.tv_money,bean.distance)
                        .setTextColor(R.id.tv_money,getContext().getResources().getColor(R.color.color_7C7877));
                break;
        }
        Picasso.with(getContext())
                .load(bean.image)
                .fit()
                .into(tvImage);

    }

}
