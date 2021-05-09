package com.bee.user.ui.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.utils.DisplayUtil;
import com.bigkoo.convenientbanner.holder.Holder;
import com.squareup.picasso.Picasso;

/**
 * Created by chenqun on 2017/6/15.
 * 首页Banner的 holder
 */

public class BannerImageHolder extends Holder<BannerBean> {
    private ImageView imageView;

    public BannerImageHolder(View itemView) {
        super(itemView);
    }


    @Override
    protected void initView(View itemView) {
        imageView =  itemView.findViewById(R.id.tv_image);
    }

    @Override
    public void updateUI(BannerBean data) {
        if(TextUtils.isEmpty(data.url)){
            Picasso.with(imageView.getContext())
                    .load(R.drawable.banner)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(imageView.getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                    .into(imageView);
        }else{
            Picasso.with(imageView.getContext())
                    .load(data.url)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(imageView.getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                    .into(imageView);
        }

    }
}
