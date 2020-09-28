package com.bee.user.ui.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.StoreBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  15：23
 * 描述：
 */
public class ShouHouAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {

    public ShouHouAdapter() {
        super(R.layout.item_shouhou);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StoreBean storeBean) {

        RecyclerView recyclerview = baseViewHolder.findView(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));

        ArrayList<FoodBean> foodBeans = new ArrayList<>();
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());

        recyclerview.setAdapter(new ShouHouItemAdapter(foodBeans));
    }
}
