package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.AddRemoveView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  16：29
 * 描述：
 */
public class ChartFoodItemAdapter extends BaseQuickAdapter<ChartBean, BaseViewHolder> {
    public ChartFoodItemAdapter(@Nullable List<ChartBean> data) {
        super(R.layout.item_chart_food, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ChartBean foodBean) {
        TextView iv_goods_price_past = holder.findView(R.id.iv_goods_price_past);
        DisplayUtil.setXiexian(iv_goods_price_past);

        TextView iv_goods_name = holder.findView(R.id.iv_goods_name);
        iv_goods_name.setText(foodBean.getProductName());



        holder.findView(R.id.iv_goods_comment).setVisibility(View.INVISIBLE);

        TextView iv_goods_detail = holder.findView(R.id.iv_goods_detail);
        iv_goods_detail.setText(foodBean.getSp1()+"/"+foodBean.getSp2()+"/"+foodBean.getSp3());

        TextView iv_goods_price = holder.findView(R.id.iv_goods_price);
        AddRemoveView iv_goods_add = holder.findView(R.id.iv_goods_add);

        iv_goods_price.setText(foodBean.getPrice()+"");
        iv_goods_price_past.setText(foodBean.getPrice()+"");

        iv_goods_add.setNum(foodBean.getQuantity());
        iv_goods_add.setOnNumChangedListener(new AddRemoveView.OnNumChangedListener() {
            @Override
            public void onNumChangedListener(int num) {
                foodBean.setQuantity(num);
                Api.getClient(HttpRequest.baseUrl_member).updateQuantity(foodBean.getId()+"",num+"").
                        subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>() {
                            @Override
                            public void onSuccess(String s) {
                            }

                            @Override
                            public void onFail(String fail) {
                                super.onFail(fail);
                            }
                        });

            }
        });

        CheckBox cb_1 = holder.findView(R.id.cb_1);
        cb_1.setChecked(foodBean.isSelected);
        cb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                foodBean.isSelected = isChecked;

                int totalMoney = 0;
                if(isChecked){
                    totalMoney += foodBean.getPrice()*foodBean.getQuantity();
                }else{
                    totalMoney -= foodBean.getPrice()*foodBean.getQuantity();
                }
                EventBus.getDefault().post(new ChartFragmentEvent(totalMoney));
            }
        });
    }
}
