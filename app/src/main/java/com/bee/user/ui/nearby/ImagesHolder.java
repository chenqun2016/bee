package com.bee.user.ui.nearby;

import android.view.View;
import android.widget.ImageView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.ImageBean;
import com.bee.user.utils.DisplayUtil;
import com.bigkoo.convenientbanner.holder.Holder;
import com.squareup.picasso.Picasso;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/03  16：29
 * 描述：
 */
public class ImagesHolder extends Holder<ImageBean> {
    private ImageView imageView;

    public ImagesHolder(View itemView) {
        super(itemView);
    }


    @Override
    protected void initView(View itemView) {
        imageView =  itemView.findViewById(R.id.tv_image);
    }

    @Override
    public void updateUI(ImageBean data) {
        Picasso.with(imageView.getContext())
                .load(data.url)
                .into(imageView);
    }
}
