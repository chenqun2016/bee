package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.bee.user.R;
import com.bee.user.bean.BillBean;
import com.bee.user.ui.order.OrderCommentActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/02  17：00
 * 描述：
 */
public class BillEntity extends BaseCEntity<BillBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BillBean item, int position) {
        super.onClick(context, item, position);

    }

    @Override
    public int getItemLayou() {
        return R.layout.item_bill_list;
    }


    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, BillBean item, int position) {
        super.convert(adapter, helper, item, position);

    }
}

