package com.bee.user.entity;

import android.content.Context;

import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/21  20：54
 * 描述：
 */
public class NewsEntity extends BaseCEntity<NewsBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<NewsBean, BaseViewHolder> mAdapter, int position) {
        super.onClick(context, mAdapter, position);
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_news;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, NewsBean item, int position) {
        super.convert(adapter, helper, item, position);
    }
}
