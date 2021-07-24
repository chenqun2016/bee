package com.bee.user.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.DingWeiBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  17：10
 * 描述：
 */
public class DingWeiAdapter extends BaseQuickAdapter<DingWeiBean, BaseViewHolder> {
    public int current = 0;

    public DingWeiAdapter( ) {
//        super(R.layout.item_dingwei);
        super(R.layout.item_location_nearby);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, DingWeiBean dingWeiBean) {
        ImageView image = holder.getView(R.id.image);

        if(current == holder.getAbsoluteAdapterPosition()){
            image.setImageResource(R.drawable.icon_dingwei_cheng);
        }else{
            image.setImageResource(R.drawable.icon_circle_grey);
        }
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_dizhi = holder.getView(R.id.tv_dizhi);

        tv_name.setText(dingWeiBean.name);
        tv_dizhi.setText(dingWeiBean.address);
    }
}
