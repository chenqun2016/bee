package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.adapter.NearbyStoreFoodAdapter;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/25  22：46
 * 描述：
 */
public class NearbyEntity extends BaseCEntity<StoreBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, StoreBean item,int position) {
        super.onClick(context, item,position);

        context.startActivity(new Intent(context, StoreActivity.class));
    }


    @Override
    public int getItemLayou() {
        return R.layout.item_nearby;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, StoreBean item, int position) {
        super.convert(adapter, helper, item, position);

        ImageView iv_icon = helper.getView(R.id.iv_icon);

        Picasso.with(mContext)
                .load(R.drawable.food2)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(mContext,5),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_icon);

        RecyclerView recyclerview = helper.getView(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
//        recyclerview.addItemDecoration(dividerItemDecoration);
        List<StoreBean.StoreFood> storeFoods = new ArrayList<>();
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","10"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","11"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","12"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","13"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","14"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","15"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","16"));

        NearbyStoreFoodAdapter homeFooterAdapter = new NearbyStoreFoodAdapter(storeFoods);
        homeFooterAdapter.setOnItemClickListener(new NearbyStoreFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
                recyclerview.getContext().startActivity(new Intent(recyclerview.getContext(), StoreActivity.class));
            }
        });
        recyclerview.setAdapter(homeFooterAdapter);




//        标签

        LinearLayout ll_mark = helper.getView(R.id.ll_mark);
        CommonUtil.initTAGViews(ll_mark);



    }
}
