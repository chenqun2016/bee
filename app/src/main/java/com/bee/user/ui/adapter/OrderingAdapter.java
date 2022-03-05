package com.bee.user.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.event.OrderingEvent;
import com.bee.user.ui.nearby.StoreActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  21：47
 * 描述：
 */
public class OrderingAdapter extends BaseQuickAdapter<OrderingConfirmBean.StoreOrderConfirmItemsBean, BaseViewHolder> {
    private final boolean isSingleStore;

    public OrderingAdapter(boolean isSingleStore) {
        super(R.layout.item_ordering);
        this.isSingleStore = isSingleStore;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderingConfirmBean.StoreOrderConfirmItemsBean storeBean) {
        TextView tv_time_value = baseViewHolder.findView(R.id.tv_time_value);
        if(isSingleStore){
            tv_time_value.setVisibility(View.GONE);
        }else{
            tv_time_value.setVisibility(View.VISIBLE);
            tv_time_value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new OrderingEvent(storeBean.getStoreId()+""));
                }
            });
            tv_time_value.setText("大约"+(storeBean.currentDay==0?"今天":"明天")+storeBean.feightTemplateDetail+"送到");
        }

        TextView tv_store = baseViewHolder.findView(R.id.tv_store);
        tv_store.setText(""+storeBean.getStoreName());
        tv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tv_store.getContext(), StoreActivity.class);
                tv_store.getContext().startActivity(intent);

            }
        });

        RecyclerView recyclerView = baseViewHolder.findView(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(tv_store.getContext()));

        List<OrderingConfirmBean.StoreOrderConfirmItemsBean.ProductsBean> products = storeBean.getProducts();
        OrderPreFoodAdapter adapter = new OrderPreFoodAdapter(products);
        recyclerView.setAdapter(adapter);


        OrderingConfirmBean.StoreOrderConfirmItemsBean.CalcAmountBean calcAmount = storeBean.getCalcAmount();


        TextView tv_total_money_value = baseViewHolder.findView(R.id.tv_total_money_value);
        tv_total_money_value.setText(""+calcAmount.getTotalAmount());
        TextView tv_baozhuangfei_value = baseViewHolder.findView(R.id.tv_baozhuangfei_value);
        tv_baozhuangfei_value.setText(""+calcAmount.getPackingFeeAmount());
        TextView tv_peisongfei_value = baseViewHolder.findView(R.id.tv_peisongfei_value);
        tv_peisongfei_value.setText(""+calcAmount.getFreightAmount());
        TextView tv_youhuiquan_value = baseViewHolder.findView(R.id.tv_youhuiquan_value);
        int size = storeBean.couponList ==null?0:storeBean.couponList.size();
        tv_youhuiquan_value.setText(size + "张可用");
        TextView tv_total_value = baseViewHolder.findView(R.id.tv_total_value);
        tv_total_value.setText(""+calcAmount.getPayAmount());
        TextView tv_total_youhui_value = baseViewHolder.findView(R.id.tv_total_youhui_value);
    }
}
