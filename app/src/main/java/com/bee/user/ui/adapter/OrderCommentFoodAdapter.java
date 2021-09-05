package com.bee.user.ui.adapter;

import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.widget.RadioGroupPlus;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  20：01
 * 描述：
 */
public class OrderCommentFoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
    public OrderCommentFoodAdapter() {
        super(R.layout.item_order_comment_food);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodBean bean) {
        TextView view = baseViewHolder.findView(R.id.tv_name);
        view.setText(bean.productName+"");

        RadioGroupPlus rgp = baseViewHolder.findView(R.id.rgp);
        rgp.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {
                if(checkedId == R.id.rb_1){
                    bean.commentType = 0;
                }
                if(checkedId == R.id.rb_2){
                    bean.commentType = 1;
                }
            }
        });
    }
}
