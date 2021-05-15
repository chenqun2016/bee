package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.ChooseTimeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/15  20：25
 * 描述：
 */
public class ChooseTimeAdapter extends BaseQuickAdapter<ChooseTimeBean.ChooseTimeItemBean, BaseViewHolder> {

    public ChooseTimeAdapter( ) {
        super(R.layout.item_choose_time);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ChooseTimeBean.ChooseTimeItemBean chooseTimeBean) {

        View icon = baseViewHolder.findView(R.id.iv_choosetype);

        TextView tv_time = baseViewHolder.findView(R.id.tv_time);
        if(chooseTimeBean.choosed){
            tv_time.setTextColor(tv_time.getResources().getColor(R.color.color_FF6200));
            icon.setVisibility(View.VISIBLE);
        }else{
            tv_time.setTextColor(tv_time.getResources().getColor(R.color.color_7C7877));
            icon.setVisibility(View.INVISIBLE);
        }
        tv_time.setText(chooseTimeBean.getArriveTime()+"（"+chooseTimeBean.getDeliverFee()+"元配送费）");
    }
}
