package com.bee.user.entity;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.YouhuiquanBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CMultiRecyclerView;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/29  21：25
 * 描述：
 */
public class CouponEntity extends BaseCEntity<YouhuiquanBean> {

    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<YouhuiquanBean, BaseViewHolder> mAdapter, int position) {

    }

    @Override
    public int getItemLayou() {
        return R.layout.item_coupon;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, YouhuiquanBean item, int position) {
        super.convert(adapter, helper, item, position);
        TextView tv_money = helper.findView(R.id.tv_money);
        TextView tv_money_value = helper.findView(R.id.tv_money_value);
        TextView tv_limit = helper.findView(R.id.tv_limit);
        TextView tv_des1 = helper.findView(R.id.tv_des1);
        TextView tv_des2 = helper.findView(R.id.tv_des2);
        TextView tv_des3 = helper.findView(R.id.tv_des3);

        if(item.type == 0){
            tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_282525));
            tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_282525));
            tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
            tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_282525));
            tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
            tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));


        }else{
            tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
            tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
            tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
            tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
            tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
            tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
        }
    }
}
