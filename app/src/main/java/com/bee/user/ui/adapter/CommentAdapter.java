package com.bee.user.ui.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.ImageBean;
import com.bee.user.ui.nearby.ImagesActivity;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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

        CommonUtil.initCommentAdapter(images);


    }


}
