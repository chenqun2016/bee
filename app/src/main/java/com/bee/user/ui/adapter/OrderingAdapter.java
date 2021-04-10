package com.bee.user.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.ui.xiadan.YouhuiquanActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  21：47
 * 描述：
 */
public class OrderingAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public OrderingAdapter( ) {
        super(R.layout.item_ordering);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StoreBean storeBean) {
        TextView tv_youhuiquan_value = baseViewHolder.findView(R.id.tv_youhuiquan_value);
        tv_youhuiquan_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(tv_youhuiquan_value.getContext(), YouhuiquanActivity.class);
                tv_youhuiquan_value.getContext().startActivity(intent);

            }
        });


        TextView tv_store = baseViewHolder.findView(R.id.tv_store);
        tv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tv_store.getContext(), StoreActivity.class);
                tv_store.getContext().startActivity(intent);

            }
        });

        RecyclerView recyclerView = baseViewHolder.findView(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(tv_store.getContext()));

        List<FoodBean> foodBeans = new ArrayList<>();
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());


        OrderFoodAdapter adapter = new OrderFoodAdapter(foodBeans);
        recyclerView.setAdapter(adapter);




    }
}
