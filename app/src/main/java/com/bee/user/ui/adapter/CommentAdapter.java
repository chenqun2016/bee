package com.bee.user.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/28  20：56
 * 描述：
 */
public class CommentAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> implements LoadMoreModule {

    public CommentAdapter() {
        super(R.layout.item_store_comment);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, CommentBean commentBean) {
        RecyclerView images = helper.findView(R.id.images);
        if (null == commentBean || null == commentBean.eva) {
            return;
        }
        String pics = commentBean.eva.pics;
        if (!TextUtils.isEmpty(pics)) {
            images.setVisibility(View.VISIBLE);
            String[] list = pics.split(",");
            CommonUtil.initCommentAdapter(images, list);
        } else {
            images.setVisibility(View.GONE);
        }
        TextView tv_name = helper.findView(R.id.tv_name);
        tv_name.setText(commentBean.eva.username + "");
        TextView tv_time = helper.findView(R.id.tv_time);
        tv_time.setText(CommonUtil.getNomalTime2(commentBean.eva.createTime));
        TextView tv_des = helper.findView(R.id.tv_des);
        tv_des.setText(commentBean.eva.content + "");


        //TODO
        ImageView touxiang = helper.findView(R.id.touxiang);
        Picasso.with(touxiang.getContext())
                .load(commentBean.eva.icon)
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(touxiang.getContext(), 50), 0, PicassoRoundTransform.CornerType.ALL))
                .into(touxiang);
        ImageView dengji = helper.findView(R.id.dengji);
        TextView tv_comment = helper.findView(R.id.tv_comment);
        tv_comment.setText(commentBean.eva.isAnonymous == 1 ? "赞了该商品" : "踩了该商品");
        LinearLayout ll_store_reply = helper.findView(R.id.ll_store_reply);
        ll_store_reply.setVisibility(TextUtils.isEmpty(commentBean.eva.replyContent) ? View.GONE : View.VISIBLE);
        TextView tv_store_reply = helper.findView(R.id.tv_store_reply);
        tv_store_reply.setText(commentBean.eva.replyContent);
    }


}
