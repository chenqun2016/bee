package com.bee.user.entity;

import android.content.Context;
import android.widget.ImageView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.squareup.picasso.Picasso;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/25  22：46
 * 描述：
 */
public class StoreEntity extends BaseCEntity<StoreBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, StoreBean item) {
        super.onClick(context, item);
        ToastUtil.ToastShort(context,"嘿嘿");
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_food;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, StoreBean item, int position) {
        super.convert(adapter, helper, item, position);

        ImageView tv_image = helper.getView(R.id.tv_image);

        Picasso.with(mContext)
                .load(R.drawable.food)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(mContext,10),0, PicassoRoundTransform.CornerType.TOP))
                .into(tv_image);
    }
}
