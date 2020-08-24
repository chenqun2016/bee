package com.bee.user.ui.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.HomeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/23  20：48
 * 描述：
 */
public class HomeAdapter extends BaseQuickAdapter<HomeBean,BaseViewHolder> {
    public HomeAdapter() {
        super(R.layout.item_home);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeBean o) {

    }

}
