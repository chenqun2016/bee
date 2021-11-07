package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.bee.user.utils.DisplayUtil;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/7 下午2:46
 */
public class NewsAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> {


    public NewsAdapter() {
        super(R.layout.item_news);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NewsBean newsBean) {
        //SYSTEM,系统通知
        //REWARD,红包助手
        //ORDER,订单消息
        //MERCHANT,商家消息

        ImageView imageview = baseViewHolder.getView(R.id.imageview);
        ImageView imageview2 = baseViewHolder.getView(R.id.imageview2);
        if(ObjectUtils.isEmpty(newsBean.getMessagePictureUrl())) {
            imageview2.setVisibility(View.GONE);
        }else {
            imageview2.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(newsBean.getMessagePictureUrl())
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),10),0, PicassoRoundTransform.CornerType.ALL))
                    .into(imageview2);
        }
        Picasso.with(getContext())
                .load(newsBean.getMessageIcon())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),10),0, PicassoRoundTransform.CornerType.ALL))
                .into(imageview);
        baseViewHolder.setGone(R.id.imageview_point,newsBean.getIsReaded()==1)
        .setText(R.id.tv_title,newsBean.getMessageTitle())
        .setText(R.id.tv_time,newsBean.getCreateTime())
        .setText(R.id.tv_des,newsBean.getMessageBody());
    }
}
