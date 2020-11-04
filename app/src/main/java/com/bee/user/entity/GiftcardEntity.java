package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.bee.user.R;
import com.bee.user.bean.GiftcardBean;
import com.bee.user.bean.MyCommentBean;
import com.bee.user.ui.order.OrderCommentActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/04  20：13
 * 描述：
 */
public class GiftcardEntity extends BaseCEntity<GiftcardBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, GiftcardBean item, int position) {
        super.onClick(context, item, position);

    }

    @Override
    public int getItemLayou() {
        return R.layout.item_giftcard;
    }


    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, GiftcardBean item, int position) {
        super.convert(adapter, helper, item, position);

    }
}
