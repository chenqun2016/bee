package com.huaxiafinance.www.crecyclerview.crecyclerView;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.io.Serializable;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by chenqun on 2017/2/17.
 * 单一条目调用方法getItemLayou（）
 * 多条目调用方法addItemType(BaseMultiItemQuickAdapter adapter)
 * 以上两个方法只能重写一个
 * 多条目情况下Bean类 必须 实现 MultiItemEntity类
 */

public abstract class BaseCEntity<T> implements Serializable {

    protected Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }

    //接口 动态参数
    protected Map<String, String> mParam;

    public void setParam(Map<String, String> param) {
        this.mParam = param;
    }


    //    获取接口的Observable
    public abstract Observable getPageAt(int page, int row);

    //每个条目的点击事件
    public void onClick(Context context, T item) {
    }

    //每个条目中 自条目 的点击事件
    public void OnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    //    给每个条目设置数据，item是数据
    public void convert(BaseViewHolder helper, T item) {
    }

    //    获取 单一 RecyclerView的item的layout
    // 返回值是0 的情况下才会调用方法  addItemType(BaseMultiItemQuickAdapter adapter)
    public int getItemLayou() {
        return 0;
    }

    //    多类型item 时设置 item  如：adapter.addItemType(MultipleItem.TEXT, R.layout.item_text_view);
    public void addItemType(CMultiRecyclerView.MultipleItemQuickAdapter adapter) {
    }

    public void convert(BaseViewHolder helper, T item, int position) {
        convert(helper, item);
    }

    public void convert(BaseQuickAdapter adapter,BaseViewHolder helper, T item, int position) {
        convert(helper, item,position);
    }
}
