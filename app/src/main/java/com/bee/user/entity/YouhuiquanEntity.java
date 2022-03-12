package com.bee.user.entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CMultiRecyclerView;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/05  21：14
 * 描述：
 */
public class YouhuiquanEntity extends BaseCEntity<OrderingConfirmBean.OrderingCouponBean> {

    private int index = -1;
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<OrderingConfirmBean.OrderingCouponBean, BaseViewHolder> mAdapter, int position) {
        OrderingConfirmBean.OrderingCouponBean item = mAdapter.getItem(position);
        if(item.status == 0){

            if(index != -1 && index != position){
                OrderingConfirmBean.OrderingCouponBean itemPre = mAdapter.getItem(index);
                if(itemPre.isSelected){
                    itemPre.isSelected = false;
                    mAdapter.notifyItemChanged(index);
                }
            }
            item.isSelected = !item.isSelected;
            this. index = position;
            mAdapter.notifyItemChanged(position);

            if(context instanceof Activity){
                Intent intent = new Intent();
                intent.putExtra("index",position);
                ((Activity) context).setResult(55,intent);
                ((Activity) context).finish();
            }
        }
    }

    @Override
    public void onClick(Context context, OrderingConfirmBean.OrderingCouponBean item,int position) {
        super.onClick(context, item,position);

    }



    @Override
    public void addItemType(CMultiRecyclerView.MultipleItemQuickAdapter adapter) {
        super.addItemType(adapter);
        adapter.addItemTypes(OrderingConfirmBean.OrderingCouponBean.type1,R.layout.item_youhuiquan);
//        adapter.addItemTypes(OrderingConfirmBean.OrderingCouponBean.type2,R.layout.item_youhuiquan_2);
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, OrderingConfirmBean.OrderingCouponBean item, int position) {
        super.convert(adapter, helper, item, position);

        switch (helper.getItemViewType()) {
            case OrderingConfirmBean.OrderingCouponBean.type1:
                TextView tv_money = helper.findView(R.id.tv_money);
                TextView tv_money_value = helper.findView(R.id.tv_money_value);
                TextView tv_limit = helper.findView(R.id.tv_limit);
                TextView tv_des1 = helper.findView(R.id.tv_des1);
                TextView tv_des2 = helper.findView(R.id.tv_des2);
                TextView tv_des3 = helper.findView(R.id.tv_des3);
                ImageView cb_1 = helper.findView(R.id.cb_1);

                tv_money_value.setText(item.faceValue + "");
                tv_limit.setText("满" + item.minAmount + "元可用");
                tv_des1.setText(item.cardName + "");
                tv_des2.setText(CommonUtil.getNomalTime(item.expireTime));
                tv_des3.setText("通用");

                if(item.status == 0){
                    tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_FF5549));
                    tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_FF5549));
                    tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                    tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_272525));
                    tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                    tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                    if(item.isSelected){
                        cb_1.setImageResource(R.drawable.icon_xuanzhong_gouwuche);
                    }else{
                        cb_1.setImageResource(R.drawable.icon_gouxuan_gouwuche);
                    }

                }else{
                    tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    cb_1.setImageResource(R.drawable.icon_gouxuan_gouwuche);
                }



                break;
            case OrderingConfirmBean.OrderingCouponBean.type2:
                break;
            default:
                break;
        }
    }
}
