package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.ui.trade.TradeDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/18  13：19
 * 描述：
 */
public class TradeListEntity extends BaseCEntity<TradeRecordBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, TradeRecordBean item, int position) {
        super.onClick(context, item, position);
        context.startActivity(new Intent(context, TradeDetailActivity.class));
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_trade_list;
    }


    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, TradeRecordBean item, int position) {
        super.convert(adapter, helper, item, position);
        View view = helper.findView(R.id.view_line);
        if(0 == position){
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
    }
}
