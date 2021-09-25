package com.bee.user.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeBean bean) {
        ImageView iv_image = baseViewHolder.findView(R.id.iv_image);
        Picasso.with(iv_image.getContext())
                .load(bean.pic)
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_image.getContext(),10),0, PicassoRoundTransform.CornerType.TOP))
                .into(iv_image);

        TextView tv_title = baseViewHolder.findView(R.id.tv_title);
        tv_title.setText(bean.productName);
//        TextView tv_content = baseViewHolder.findView(R.id.tv_content);
//        tv_content.setText(bean.productName);

        TextView tv_money = baseViewHolder.findView(R.id.tv_money);
        tv_money.setText(bean.price+"");

        TextView tv_tag = baseViewHolder.findView(R.id.tv_tag);
        if(!TextUtils.isEmpty(bean.tags)){
            tv_tag.setText(bean.tags);
            tv_tag.setVisibility(View.VISIBLE);
        }else{
            tv_tag.setVisibility(View.GONE);
        }

        ImageView iv_chart = baseViewHolder.findView(R.id.iv_chart);
        iv_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
