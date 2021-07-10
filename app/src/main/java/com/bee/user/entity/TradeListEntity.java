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
        TextView tv_integral_date = helper.findView(R.id.tv_integral_date);
        tv_integral_date.setText(CommonUtil.formatData(item.createTime));

        TextView tv_trade_type = helper.findView(R.id.tv_trade_type);
        switch (item.status){
            case  1:
                tv_trade_type.setText("交易成功");
                break;
            case  0:
                tv_trade_type.setText("交易处理中");
                break;
            case  -1:
                tv_trade_type.setText("交易失败");
                break;
            default:
                tv_trade_type.setText("交易成功");
                break;
        }
        TextView tv_integral_count = helper.findView(R.id.tv_integral_count);
        tv_integral_count.setText("+"+item.orderAmount/100);

        TextView tv_integral_title = helper.findView(R.id.tv_integral_title);
        switch (item.bizType){
            case  "C":
                tv_integral_title.setText("普通账户充值");
                break;
            case  "P":
                tv_integral_title.setText("配送卡充值");
                break;
            case  "X":
                tv_integral_title.setText("消费米粒");
                break;
            default:
                tv_integral_title.setText("普通账户充值");
                break;
        }
    }
}
