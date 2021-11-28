package com.bee.user.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.CollectionStoreBean;
import com.bee.user.bean.CommentBean;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/28 下午3:19
 */
public class CollectionStoreAdapter extends BaseQuickAdapter<CollectionStoreBean.RecordBean, BaseViewHolder> implements LoadMoreModule {

    public CollectionStoreAdapter() {
        super(R.layout.item_collection_store);
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
                .setText(R.id.tv_fraction,recordBean.getScore()+"分")
                .setText(R.id.tv_inform,recordBean.getSales()+ "  "+ recordBean.getArriveMinutes())
        .setText(R.id.tv_top,recordBean.getTop() == 0?"置顶店铺":"取消置顶");
    }


}
