package com.bee.user.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.bean.PointDayBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/21  12：37
 * 描述：
 */
public class BuyCardAdapter extends BaseQuickAdapter<PeiSongCardBean, BaseViewHolder> {

    public int current  = -1;

    public BuyCardAdapter() {
        super(R.layout.item_buy_peisong_card);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PeiSongCardBean peiSongCardBean) {
        int layoutPosition = baseViewHolder.getLayoutPosition();

        LinearLayout ll_content = baseViewHolder.getView(R.id.ll_content);
        ImageView iv_bg = baseViewHolder.getView(R.id.iv_bg);
        TextView tv_text1 = baseViewHolder.getView(R.id.tv_text1);
        TextView tv_text2 = baseViewHolder.getView(R.id.tv_text2);
        TextView tv_text3 = baseViewHolder.getView(R.id.tv_text3);
        TextView tv_text4 = baseViewHolder.getView(R.id.tv_text4);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iv_bg.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
        if(0 == layoutPosition){

            layoutParams.leftMargin = DisplayUtil.dip2px(ll_content.getContext(),10);
            layoutParams2.leftMargin = DisplayUtil.dip2px(ll_content.getContext(),10);
        }else{
            layoutParams.leftMargin = 0;
            layoutParams2.leftMargin = 0;
        }
        iv_bg.setLayoutParams(layoutParams);
        ll_content.setLayoutParams(layoutParams2);


        tv_text1.setText(peiSongCardBean.text1);
        tv_text2.setText(peiSongCardBean.text2);
        tv_text3.setText(peiSongCardBean.text3);
        tv_text4.setText(peiSongCardBean.text4);

        if(current == layoutPosition){
            iv_bg.setAlpha(1f);
            tv_text4.setAlpha(1f);
        }else{
            iv_bg.setAlpha(0.7f);
            tv_text4.setAlpha(0.7f);
        }
        switch (peiSongCardBean.type){
            case 0:
                iv_bg.setImageResource(R.drawable.bg_nian);
                break;
            case 1:
                iv_bg.setImageResource(R.drawable.bg_ji);
                break;
            case 2:
                iv_bg.setImageResource(R.drawable.bg_yue);
                break;

        }
    }
}
