package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.HelpTypeItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 - @Description:
 - @Author:  bixueyun
 - @Time:  2021/6/20 下午12:09
 */
public class HelpTypeItemAdapter extends BaseQuickAdapter<HelpTypeItemBean, BaseViewHolder> {


    public HelpTypeItemAdapter() {
        super(R.layout.item_helpcentr);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, HelpTypeItemBean helpTypeItemBean) {
        View line = helper.findView(R.id.line);

        TextView tv_text = helper.findView(R.id.tv_text);
        tv_text.setText(helpTypeItemBean.getTitle());

        if(0 == helper.getLayoutPosition()){
            line.setVisibility(View.GONE);
        }else{
            line.setVisibility(View.VISIBLE);
        }
    }
}
