package com.bee.user.entity;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.YouhuiquanBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
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
public class YouhuiquanEntity extends BaseCEntity<YouhuiquanBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, YouhuiquanBean item) {
        super.onClick(context, item);
        if(item.type == 0){

        }
    }



    @Override
    public void addItemType(CMultiRecyclerView.MultipleItemQuickAdapter adapter) {
        super.addItemType(adapter);
        adapter.addItemTypes(YouhuiquanBean.type1,R.layout.item_youhuiquan);
        adapter.addItemTypes(YouhuiquanBean.type2,R.layout.item_youhuiquan_2);
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, YouhuiquanBean item, int position) {
        super.convert(adapter, helper, item, position);

        switch (helper.getItemViewType()) {
            case YouhuiquanBean.type1:
                TextView tv_money = helper.findView(R.id.tv_money);
                TextView tv_money_value = helper.findView(R.id.tv_money_value);
                TextView tv_limit = helper.findView(R.id.tv_limit);
                TextView tv_des1 = helper.findView(R.id.tv_des1);
                TextView tv_des2 = helper.findView(R.id.tv_des2);
                TextView tv_des3 = helper.findView(R.id.tv_des3);
                CheckBox cb_1 = helper.findView(R.id.cb_1);

                if(item.type == 0){
                    tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_FF5549));
                    tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_FF5549));
                    tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                    tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_272525));
                    tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                    tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                    cb_1.setButtonDrawable(R.drawable.checkbutton_chart);
                }else{
                    tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_CBCBCB));
                    cb_1.setButtonDrawable(R.drawable.icon_gouxuan_gouwuche);
                }



                break;
            case YouhuiquanBean.type2:
                break;
            default:
                break;
        }
    }
}
