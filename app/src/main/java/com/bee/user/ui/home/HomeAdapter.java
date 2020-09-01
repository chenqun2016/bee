package com.bee.user.ui.home;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.HomeBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

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
        ImageView iv_image = baseViewHolder.findView(R.id.iv_image);
        Picasso.with(iv_image.getContext())
                .load(R.drawable.food2)
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_image.getContext(),10),0, PicassoRoundTransform.CornerType.TOP))
                .into(iv_image);
    }

}
