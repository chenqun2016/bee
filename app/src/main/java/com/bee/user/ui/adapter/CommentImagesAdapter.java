package com.bee.user.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bee.user.R;
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
public class CommentImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public CommentImagesAdapter(@Nullable List<String> data) {
        super(R.layout.item_comment_image, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String imageBean) {
        ImageView iv_tupian = baseViewHolder.findView(R.id.iv_tupian);
        if(!TextUtils.isEmpty(imageBean)){
            iv_tupian.setVisibility(View.VISIBLE);
            Picasso.with(iv_tupian.getContext()).load(imageBean).into(iv_tupian);
        }else{
            iv_tupian.setVisibility(View.GONE);
        }
    }
}
