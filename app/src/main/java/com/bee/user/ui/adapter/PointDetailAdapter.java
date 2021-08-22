package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import com.bee.user.R;
import com.bee.user.bean.PointDetailBen;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/8/22 下午3:58
 */
public class PointDetailAdapter extends BaseQuickAdapter<PointDetailBen, BaseViewHolder> implements LoadMoreModule {

    public PointDetailAdapter() {
        super(R.layout.item_point_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, PointDetailBen item) {
        View view = helper.findView(R.id.view_line);
        ImageView iconType = helper.getView(R.id.icon_type);
        int itemPosition = getItemPosition(item);
        if(0 == itemPosition){
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_integral_title,item.getActivityName())
                .setText(R.id.tv_integral_date,item.getCreateTime())
                .setText(R.id.tv_integral_count,item.getPointsType()==1?"+"+item.getPoints():"-"+item.getPoints())
                .setTextColorRes(R.id.tv_integral_count,item.getPointsType()==1?R.color.color_FF564A:R.color.color_2DD28A);
        Picasso.with(helper.itemView.getContext())
                .load(item.getIconUrl())
                .error(R.drawable.icon_jiangli)
                .placeholder(R.drawable.icon_jiangli)
                .fit()
                .into(iconType);
    }
}
