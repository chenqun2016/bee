package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bee.user.R;
import com.bee.user.bean.MyCommentBean;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.ui.order.OrderCommentActivity;
import com.bee.user.ui.trade.TradeDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/26  16：50
 * 描述：
 */
public class MyCommentEntity extends BaseCEntity<MyCommentBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, MyCommentBean item, int position) {
        super.onClick(context, item, position);
        context.startActivity(new Intent(context, OrderCommentActivity.class));
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_mycomment_list;
    }


    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, MyCommentBean item, int position) {
        super.convert(adapter, helper, item, position);

        helper.findView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.removeAt(position);
            }
        });
    }
}
