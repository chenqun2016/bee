package com.bee.user.ui.adapter;

import android.widget.ImageView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.CollectionStoreBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/28 下午5:54
 */
public class CollectionProductAdapter extends BaseQuickAdapter<CollectionStoreBean.RecordBean, BaseViewHolder> implements LoadMoreModule {

    public CollectionProductAdapter() {
        super(R.layout.item_collection_product);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, CollectionStoreBean.RecordBean recordBean) {
        ImageView icon = helper.getView(R.id.icon);
        MaterialRatingBar materialRatingBar =  helper.getView(R.id.ratin);
        Picasso.with(icon.getContext())
                .load(recordBean.getPictureUrl())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(icon.getContext(), 10), 0, PicassoRoundTransform.CornerType.ALL))
                .into(icon);
        materialRatingBar.setRating(recordBean.getScore());
        helper.setText(R.id.tv_name,recordBean.getName())
                .setText(R.id.tv_inform,recordBean.getArriveDistance())
                .setText(R.id.tv_amount,"￥"+recordBean.getPrice());
    }


}
