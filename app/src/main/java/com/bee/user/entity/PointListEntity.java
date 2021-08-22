package com.bee.user.entity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.PointDetailBen;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.ui.trade.TradeDetailActivity;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.squareup.picasso.Picasso;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/14  19：18
 * 描述：
 */
public class PointListEntity extends BaseCEntity<PointDetailBen> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, PointDetailBen item, int position) {
        super.onClick(context, item, position);
//        context.startActivity(new Intent(context, TradeDetailActivity.class));
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_point_list;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, PointDetailBen item, int position) {
        super.convert(adapter, helper, item, position);
        View view = helper.findView(R.id.view_line);
        ImageView iconType = helper.getView(R.id.icon_type);
        TextView tvIntegralCount =helper.getView(R.id.tv_integral_count);
        if(0 == position){
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_integral_title,item.getActivityName())
        .setText(R.id.tv_integral_date,item.getCreateTime())
        .setText(R.id.tv_integral_count,item.getPointsType()==1?"+"+item.getPoints():"-"+item.getPoints())
        .setTextColorRes(R.id.tv_integral_count,item.getPointsType()==1?R.color.color_FF564A:R.color.color_2DD28A);
        Picasso.with(helper.itemView.getContext())
                .load(item.getIconUrl())
                .error(R.drawable.icon_jiangli)
                .placeholder(R.drawable.icon_jiangli)
                .fit()
                .into(iconType);
    }
}
