package com.bee.user.ui.trade;

import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2021/7/16
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class TradeListAdapter extends BaseQuickAdapter<TradeRecordBean, BaseViewHolder> implements LoadMoreModule {
    public TradeListAdapter() {
        super(R.layout.item_trade_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, TradeRecordBean item) {
        View view = helper.findView(R.id.view_line);
        if(0 == helper.getLayoutPosition()){
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
        TextView tv_integral_date = helper.findView(R.id.tv_integral_date);
        tv_integral_date.setText(CommonUtil.getNomalTime(item.createTime));

        TextView tv_trade_type = helper.findView(R.id.tv_trade_type);
        tv_trade_type.setText(CommonUtil.getTradeType(item.status));

        TextView tv_integral_count = helper.findView(R.id.tv_integral_count);
        tv_integral_count.setText(CommonUtil.getTradeType(item.tranType)+CommonUtil.getNomalMoneyType(item.orderAmount));

        TextView tv_integral_title = helper.findView(R.id.tv_integral_title);
        tv_integral_title.setText(CommonUtil.getBizTypeStr(item.bizType));
    }
}
