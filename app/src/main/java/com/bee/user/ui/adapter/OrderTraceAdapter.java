package com.bee.user.ui.adapter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.TraceBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/26  14：50
 * 描述：
 */
public class OrderTraceAdapter extends BaseQuickAdapter<TraceBean, BaseViewHolder> {
    public OrderTraceAdapter( ) {
        super(R.layout.item_order_detail_trace);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TraceBean traceBean) {
        View left_line = baseViewHolder.findView(R.id.left_line);
        ImageView dian = baseViewHolder.findView(R.id.dian);
        ImageView dian2 = baseViewHolder.findView(R.id.dian2);

        TextView step_title = baseViewHolder.findView(R.id.step_title);
        TextView step_time = baseViewHolder.findView(R.id.step_time);
        TextView step_msg = baseViewHolder.findView(R.id.step_msg);

        step_msg.setText("系统取消了订单，理由是“超过15分钟未支付”");

        if(baseViewHolder.getLayoutPosition() == getData().size() -1){
            left_line.setVisibility(View.GONE);
        }else{
            left_line.setVisibility(View.VISIBLE);
        }

        if(traceBean.type == 0){
            dian.setVisibility(View.VISIBLE);
            dian2.setVisibility(View.GONE);

            step_title.setTextSize(12);
            step_title.setTextColor(step_title.getResources().getColor(R.color.color_7C7877));
            step_time.setTextColor(step_title.getResources().getColor(R.color.color_7C7877));
            step_msg.setVisibility(View.INVISIBLE);
        }else if(traceBean.type == 1){
            dian.setVisibility(View.GONE);
            dian2.setVisibility(View.VISIBLE);
//            startAnimation(dian2);

            step_title.setTextSize(13);
            step_title.setTextColor(step_title.getResources().getColor(R.color.color_282626));
            step_time.setTextColor(step_title.getResources().getColor(R.color.color_282626));
            step_msg.setVisibility(View.VISIBLE);
        }
    }

    public void startAnimation(ImageView imageView){
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.scale_amim);
        shake.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(shake);
    }
}
