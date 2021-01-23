package com.bee.user.entity;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.CardBean;
import com.bee.user.bean.YouhuiquanBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/23  15：38
 * 描述：
 */
public class CardEntity extends BaseCEntity<CardBean> {

    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<CardBean, BaseViewHolder> mAdapter, int position) {

    }

    @Override
    public int getItemLayou() {
        return R.layout.item_coupon_foot;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, CardBean cardBean, int position) {
        super.convert(adapter, helper, cardBean, position);
        RelativeLayout tl_content = helper.getView(R.id.tl_content);

        TextView tv_1 = helper.getView(R.id.tv_1);
        TextView tv_2 = helper.getView(R.id.tv_2);
        TextView tv_3 = helper.getView(R.id.tv_3);

        switch (cardBean.type){
            case 0:
              tl_content.setBackgroundResource(R.drawable.kapian_yue);
                tv_3.setText("月");
                tv_3.setTextColor(tl_content.getResources().getColor(R.color.color_1C95D7));
                break;
            case 1:
                tl_content.setBackgroundResource(R.drawable.kapian_ji);
                tv_3.setText("季");
                tv_3.setTextColor(tl_content.getResources().getColor(R.color.color_694FF0));
                break;
            case 2:
                tl_content.setBackgroundResource(R.drawable.kapian_nian);
                tv_3.setText("年");
                tv_3.setTextColor(tl_content.getResources().getColor(R.color.color_F26F30));
                break;
        }
        tv_1.setText(cardBean.str1);
        tv_2.setText(cardBean.str2);
    }
}
