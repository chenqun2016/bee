package com.bee.user.entity;

import android.content.Context;
import android.view.View;

import com.bee.user.R;
import com.bee.user.bean.GiftcardRecordBean;
import com.bee.user.bean.OrderBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/05  18：46
 * 描述：
 */
public class GiftcardRecordEntity extends BaseCEntity<GiftcardRecordBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<GiftcardRecordBean, BaseViewHolder> mAdapter, int position) {
        super.onClick(context, mAdapter, position);
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_giftcard_record;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, GiftcardRecordBean item, int position) {
        super.convert(adapter, helper, item, position);
        View iv_image = helper.getView(R.id.iv_image);
        if(0 == position){
            iv_image.setBackgroundResource(R.drawable.quan_xiao_100);
        }else if(1 == position){
            iv_image.setBackgroundResource(R.drawable.quan_xiao_300);
        }
        else if(2 == position){
            iv_image.setBackgroundResource(R.drawable.quan_xiao_500);
        }
        else if(3 == position){
            iv_image.setBackgroundResource(R.drawable.quan_xiao_1000);
        }else{
            iv_image.setBackgroundResource(R.drawable.quan_xiao_100);
        }

    }
}
