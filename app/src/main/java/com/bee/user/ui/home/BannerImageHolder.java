package com.bee.user.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bigkoo.convenientbanner.holder.Holder;

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
        imageView.setImageResource(data.url);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
