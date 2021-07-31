package com.bee.user.ui.adapter;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/21  12：37
 * 描述：
 */
public class BuyCardAdapter extends BaseQuickAdapter<PeiSongCardBean, BaseViewHolder> {

    public int current  = -1;

    public BuyCardAdapter() {
        super(R.layout.item_buy_peisong_card);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PeiSongCardBean peiSongCardBean) {
        int layoutPosition = baseViewHolder.getLayoutPosition();



        LinearLayout ll_content = baseViewHolder.getView(R.id.ll_content);
        ImageView iv_bg = baseViewHolder.getView(R.id.iv_bg);
        TextView tv_text1 = baseViewHolder.getView(R.id.tv_text1);
        TextView tv_text2 = baseViewHolder.getView(R.id.tv_text2);
        TextView tv_text3 = baseViewHolder.getView(R.id.tv_text3);
        TextView tv_text4 = baseViewHolder.getView(R.id.tv_text4);

        tv_text1.setText(peiSongCardBean.cardName+"");
        tv_text2.setText(peiSongCardBean.faceValue+"");
        tv_text3.setText("0元/月");

        Picasso.with(getContext())
                .load(peiSongCardBean.backgroudImageUrl)
                .fit()
//                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),5),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_bg);


        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iv_bg.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
        if(0 == layoutPosition){

            layoutParams.leftMargin = DisplayUtil.dip2px(ll_content.getContext(),10);
            layoutParams2.leftMargin = DisplayUtil.dip2px(ll_content.getContext(),10);
        }else{
            layoutParams.leftMargin = 0;
            layoutParams2.leftMargin = 0;
        }
        iv_bg.setLayoutParams(layoutParams);
        ll_content.setLayoutParams(layoutParams2);



        if(current == layoutPosition){
            iv_bg.setAlpha(1f);
            tv_text4.setAlpha(1f);
        }else{
            iv_bg.setAlpha(0.7f);
            tv_text4.setAlpha(0.7f);
        }
        //coupon_type '卡类型[M.月卡 Q.季卡 Y.年卡 SM.连续包月卡 SQ.连续包季卡 SY.连续包年卡]',
        switch (peiSongCardBean.couponType){
            case "M":
            case "SM":
                tv_text4.setText("月");
                break;
            case "Q":
            case "SQ":
                tv_text4.setText("季");
                break;
            case "Y":
            case "SY":
                tv_text4.setText("年");
                break;
            default:
                tv_text4.setText("月");
                break;
        }
    }
}
