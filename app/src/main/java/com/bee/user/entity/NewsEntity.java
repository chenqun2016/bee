package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.bee.user.ui.home.NewsItemActivity;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.squareup.picasso.Picasso;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/21  20：54
 * 描述：
 */
public class NewsEntity extends BaseCEntity<NewsBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<NewsBean, BaseViewHolder> mAdapter, int position) {
        super.onClick(context, mAdapter, position);
        context.startActivity(new Intent(context, NewsItemActivity.class));
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_news;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, NewsBean item, int position) {
        super.convert(adapter, helper, item, position);

        ImageView imageview = helper.getView(R.id.imageview);
        if(position == 0){
            imageview.setImageResource(R.drawable.icon_xitongtongzhi);
        }else if(position == 1){
            imageview.setImageResource(R.drawable.icon_dingdanxiaoxi);
        }else if(position == 2){
            imageview.setImageResource(R.drawable.icon_hongbao);
        }else{
            imageview.setImageResource(R.drawable.icon_xitongtongzhi);
        }

        ImageView imageview2 = helper.getView(R.id.imageview2);
        Picasso.with(mContext)
                .load(R.drawable.food2)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(mContext,5),0, PicassoRoundTransform.CornerType.ALL))
                .into(imageview2);
    }
}
