package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  17：29
 * 描述：
 *
 *
 */
public class OrderDetailAdapter extends BaseQuickAdapter<List<FoodBean>, BaseViewHolder> {
    public OrderDetailAdapter( ) {
        super(R.layout.item_ordering);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,  List<FoodBean> storeBean) {
        TextView tv_store = baseViewHolder.findView(R.id.tv_store);
        FoodBean orderItemListBean = storeBean.get(0);
        tv_store.setText(orderItemListBean.storeName);

        TextView tv_time_value = baseViewHolder.findView(R.id.tv_time_value);
        tv_time_value.setVisibility(View.GONE);

        RecyclerView recyclerView = baseViewHolder.findView(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        OrderFoodAdapter adapter = new OrderFoodAdapter(storeBean);
        recyclerView.setAdapter(adapter);

    }
}
