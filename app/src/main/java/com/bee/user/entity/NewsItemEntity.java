package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;

import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.bee.user.bean.NewsItemBean;
import com.bee.user.ui.home.NewsItemActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  15：37
 * 描述：
 */
public class NewsItemEntity extends BaseCEntity<NewsItemBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<NewsItemBean, BaseViewHolder> mAdapter, int position) {
        super.onClick(context, mAdapter, position);
        context.startActivity(new Intent(context, NewsItemActivity.class));
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_news_item;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, NewsItemBean item, int position) {
        super.convert(adapter, helper, item, position);
    }
}
