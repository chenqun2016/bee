package com.bee.user.ui.nearby;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

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

    }
}
