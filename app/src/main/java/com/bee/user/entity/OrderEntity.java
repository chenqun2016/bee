package com.bee.user.entity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.OrderBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.order.OrderCommentActivity;
import com.bee.user.ui.order.OrderDetailActivity;
import com.bee.user.ui.order.OrderingMapActivity;
import com.bee.user.ui.order.TuiKuanActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.ui.xiadan.PayActivity;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  14：19
 * 描述：
 */
public class OrderEntity extends BaseCEntity<OrderBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, BaseQuickAdapter<OrderBean, BaseViewHolder> mAdapter, int position) {
        super.onClick(context, mAdapter, position);
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("type",mAdapter.getData().get(position).type);
        context.startActivity(intent);
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_order;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, OrderBean item, int position) {
        super.convert(adapter, helper, item, position);

        RecyclerView recyclerview = helper.findView(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        OrderItemAdapter orderItemAdapter = new OrderItemAdapter();
        recyclerview.setAdapter(orderItemAdapter);

        List<OrderBean.OrderItemBean> orderItemBeans = new ArrayList<OrderBean.OrderItemBean>();
        orderItemBeans.add(new OrderBean.OrderItemBean());
        orderItemBeans.add(new OrderBean.OrderItemBean());
        orderItemAdapter.setNewInstance(orderItemBeans);


        TextView name = helper.findView(R.id.name);
        TextView type = helper.findView(R.id.type);
        ImageView map = helper.findView(R.id.map);
        TextView tv_money = helper.findView(R.id.tv_money);
        TextView tv_total = helper.findView(R.id.tv_total);
        TextView tv_zailaiyidan = helper.findView(R.id.tv_zailaiyidan);
        TextView tv_pinglun = helper.findView(R.id.tv_pinglun);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderingMapActivity.class);
                intent.putExtra("type",item.type);
                mContext.startActivity(intent);
            }
        });

        switch (item.type){
                case 0:
                case 1:
                    map.setVisibility(View.VISIBLE);
                    tv_zailaiyidan.setText("去支付");
                    tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mContext.startActivity(PayActivity.newIntent(mContext,new ArrayList<StoreBean>()));
                        }
                    });
                    type.setText("等待支付");
                    type.setTextColor(type.getResources().getColor(R.color.FF6200));
                    tv_pinglun.setVisibility(View.GONE);
                    break;
                case 2:
                    map.setVisibility(View.GONE);
                    tv_zailaiyidan.setText("再来一单");
                    tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mContext.startActivity(OrderingActivity.newIntent(mContext,new ArrayList<StoreBean>()));
                        }
                    });

                    type.setText("已送达");
                    type.setTextColor(type.getResources().getColor(R.color.color_7C7877));

                    tv_pinglun.setText("评论得12积分");
                    tv_pinglun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, OrderCommentActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    map.setVisibility(View.GONE);
                    tv_zailaiyidan.setText("再来一单");
                    tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, OrderingActivity.class);
                            mContext.startActivity(intent);
                        }
                    });

                    type.setText("已送达");
                    type.setTextColor(type.getResources().getColor(R.color.color_7C7877));

                    tv_pinglun.setText("评论得12积分");
                    tv_pinglun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, OrderCommentActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    map.setVisibility(View.GONE);
                    tv_zailaiyidan.setText("退款进度");
                    tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, TuiKuanActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                    type.setText("退款成功");
                    type.setTextColor(type.getResources().getColor(R.color.color_7C7877));
                    tv_pinglun.setText("再来一单");

                    tv_pinglun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mContext.startActivity(new Intent(mContext, OrderingActivity.class));
                        }
                    });
                    break;

            }

    }


    public static class OrderItemAdapter extends BaseQuickAdapter<OrderBean.OrderItemBean,BaseViewHolder> {
        public OrderItemAdapter() {
            super(R.layout.item_order_item);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderBean.OrderItemBean o) {
            ImageView iv_image = baseViewHolder.findView(R.id.iv_image);
            Picasso.with(iv_image.getContext())
                    .load(R.drawable.food2)
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_image.getContext(),10),0, PicassoRoundTransform.CornerType.ALL))
                    .into(iv_image);
        }

    }
}
