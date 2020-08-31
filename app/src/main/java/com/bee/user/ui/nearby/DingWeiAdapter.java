package com.bee.user.ui.nearby;

import com.bee.user.R;
import com.bee.user.bean.DingWeiBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  17：10
 * 描述：
 */
public class DingWeiAdapter extends BaseQuickAdapter<DingWeiBean, BaseViewHolder> {
    public DingWeiAdapter( @Nullable List<DingWeiBean> data) {
        super(R.layout.item_dingwei, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DingWeiBean dingWeiBean) {

    }
}
