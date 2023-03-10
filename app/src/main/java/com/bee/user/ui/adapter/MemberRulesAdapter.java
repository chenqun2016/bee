package com.bee.user.ui.adapter;

import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.MemberCenterBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/04  15：20
 * 描述：
 */
public class MemberRulesAdapter  extends BaseQuickAdapter<MemberCenterBean.RuleVOBean, BaseViewHolder> {
    public MemberRulesAdapter( @Nullable List<MemberCenterBean.RuleVOBean> data) {
        super(R.layout.item_member_rules, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MemberCenterBean.RuleVOBean bean) {
        TextView tv_1 = baseViewHolder.getView(R.id.tv_1);
        tv_1.setText(bean.getLevelName());
        TextView tv_2 = baseViewHolder.getView(R.id.tv_2);
        tv_2.setText(bean.getExpiredDate());
        TextView tv_3 = baseViewHolder.getView(R.id.tv_3);
        tv_3.setText(bean.getGrowScope());


        RelativeLayout background = baseViewHolder.getView(R.id.background);
        if(0 == baseViewHolder.getLayoutPosition()){
            background.setBackgroundColor(background.getResources().getColor(R.color.color_FF6200_10));

            tv_1.setTypeface(Typeface.DEFAULT_BOLD);
            tv_2.setTypeface(Typeface.DEFAULT_BOLD);
            tv_3.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            background.setBackgroundColor(background.getResources().getColor(R.color.white));

            tv_1.setTypeface(Typeface.DEFAULT);
            tv_2.setTypeface(Typeface.DEFAULT);
            tv_3.setTypeface(Typeface.DEFAULT);
        }


    }
}
