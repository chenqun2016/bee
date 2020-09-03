package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bee.user.R;
import com.bee.user.bean.ImageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/03  15：40
 * 描述：
 */
public class CommentImagesAdapter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {


    public CommentImagesAdapter(@Nullable List<ImageBean> data) {
        super(R.layout.item_comment_image, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ImageBean imageBean) {

        ImageView iv_tupian = baseViewHolder.findView(R.id.iv_tupian);
        Picasso.with(iv_tupian.getContext()).load(imageBean.url).into(iv_tupian);
    }
}
