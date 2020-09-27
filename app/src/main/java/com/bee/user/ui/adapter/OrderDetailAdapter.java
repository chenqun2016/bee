package com.bee.user.ui.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.xiadan.YouhuiquanActivity;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  17：29
 * 描述：
 */
public class OrderDetailAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public OrderDetailAdapter( ) {
        super(R.layout.item_ordering);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StoreBean storeBean) {
        TextView tv_store = baseViewHolder.findView(R.id.tv_store);

        RecyclerView recyclerView = baseViewHolder.findView(R.id.recyclerview);

        CommonUtil.initOrderFoodAdapter(recyclerView);





        TextView tv_youhuiquan_value = baseViewHolder.findView(R.id.tv_youhuiquan_value);
        tv_youhuiquan_value.setTextColor(tv_youhuiquan_value.getResources().getColor(R.color.color_282525));
        tv_youhuiquan_value.setCompoundDrawables(null,null,null,null);
        tv_youhuiquan_value.setText("¥1");
        tv_youhuiquan_value.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
