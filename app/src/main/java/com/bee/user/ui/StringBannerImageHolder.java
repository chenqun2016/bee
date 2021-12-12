package com.bee.user.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bee.user.R;
import com.bigkoo.convenientbanner.holder.Holder;
import com.squareup.picasso.Picasso;

/**
 * 创建时间：2021/12/12
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class StringBannerImageHolder extends Holder<String> {
    private ImageView imageView;

    public StringBannerImageHolder(View itemView) {
        super(itemView);
    }


    @Override
    protected void initView(View itemView) {
        imageView =  itemView.findViewById(R.id.tv_image);
    }

    @Override
    public void updateUI(String data) {
        if(!TextUtils.isEmpty(data)){
            Picasso.with(imageView.getContext())
                    .load(data)
                    .fit()
//                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(imageView.getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                    .into(imageView);
        }
    }
}

