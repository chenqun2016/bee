package com.bee.user.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.nearby.StoreActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  16：08
 * 描述：
 */
public class ChartAdapter extends BaseQuickAdapter<ChartBean, BaseViewHolder> implements LoadMoreModule {

    public ChartAdapter() {
        super(R.layout.fragment_chart_data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ChartBean storeBean) {
        TextView tv_store = holder.findView(R.id.tv_store);
        tv_store.setText(storeBean.getBuildingAreaName()+"");


        RecyclerView recyclerview = holder.findView(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));

        ArrayList<FoodBean> foodBeans = new ArrayList<>();
        foodBeans.add(new FoodBean());
//        foodBeans.add(new FoodBean());

        ChartFoodItemAdapter chartFoodItemAdapter = new ChartFoodItemAdapter(foodBeans);
        recyclerview.setAdapter(chartFoodItemAdapter);


        CheckBox cb_1 = holder.findView(R.id.cb_1);
        cb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (FoodBean bean : foodBeans){
                    bean.isSelected = b;
                }
                chartFoodItemAdapter.notifyDataSetChanged();

            }
        });

        cb_1.setChecked(storeBean.selectedAll);
    }
}
