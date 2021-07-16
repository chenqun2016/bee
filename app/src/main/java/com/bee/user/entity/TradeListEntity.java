package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.trade.TradeDetailActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/18  13：19
 * 描述：
 */
public class TradeListEntity extends BaseCEntity<TradeRecordBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        Map<String,String> map = new HashMap();
        //交易开始时间
        map.put("beginDate", "2020/07/10");
        //业务类型【C.普通账户充值 P.配送卡充值 X. 消费米粒】
//        map.put("bizType", "C");
        //交易结束时间
        map.put("endDate", "2021/07/10");
        map.put("memberId", SPUtils.geTinstance().getUserInfo().id+"");
        return Api.getClient(HttpRequest.baseUrl_pay).getPayList(Api.getRequestBody(map));
    }

    @Override
    public void onClick(Context context, TradeRecordBean item, int position) {
        super.onClick(context, item, position);
        Intent intent = new Intent(context, TradeDetailActivity.class);
        intent.putExtra("id",item.id);
        context.startActivity(intent);
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
        TextView tv_integral_date = helper.findView(R.id.tv_integral_date);
        tv_integral_date.setText(CommonUtil.getNomalTime(item.createTime));

        TextView tv_trade_type = helper.findView(R.id.tv_trade_type);
        tv_trade_type.setText(CommonUtil.getTradeType(item.status));

        TextView tv_integral_count = helper.findView(R.id.tv_integral_count);
        tv_integral_count.setText(CommonUtil.getNomalMoneyType(item.orderAmount));

        TextView tv_integral_title = helper.findView(R.id.tv_integral_title);
        tv_integral_title.setText(CommonUtil.getBizTypeStr(item.bizType));
    }
}
