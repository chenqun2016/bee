package com.bee.user.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.BeeApplication;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.AddRemoveView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
        ImageView iv_goods_img = holder.findView(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(foodBean.getProductPic())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(), 10), 0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_goods_img);

        TextView iv_goods_price_past = holder.findView(R.id.iv_goods_price_past);
        DisplayUtil.setXiexian(iv_goods_price_past);

        TextView iv_goods_name = holder.findView(R.id.iv_goods_name);
        iv_goods_name.setText(foodBean.getProductName());


        holder.findView(R.id.iv_goods_comment).setVisibility(View.INVISIBLE);

        TextView iv_goods_detail = holder.findView(R.id.iv_goods_detail);
        if(!TextUtils.isEmpty(foodBean.attributes)){
            String s =  foodBean.attributes.replaceAll(",", "/");
            iv_goods_detail.setText(s);
        }

        TextView iv_goods_price = holder.findView(R.id.iv_goods_price);
        AddRemoveView iv_goods_add = holder.findView(R.id.iv_goods_add);

        iv_goods_price.setText(foodBean.getPrice().intValue() + "");
        iv_goods_price_past.setText(foodBean.getPrice() + "");

        CheckBox cb_1 = holder.findView(R.id.cb_1_item);
        if (cb_1 != null) {
            cb_1.setChecked(foodBean.isSelected);
            LogUtil.d("isChecked2 ==" + cb_1.isChecked());
            cb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LogUtil.d("onCheckedChanged2 ==" + isChecked);
                    foodBean.isSelected = isChecked;
                    int totalMoney = 0;
                    if (isChecked) {
                        totalMoney += foodBean.getPrice().intValue() * foodBean.getQuantity();
                    } else {
                        totalMoney -= foodBean.getPrice().intValue() * foodBean.getQuantity();
                    }
                    LogUtil.d("totalmoney ==" + totalMoney);
                    BeeApplication.appVMStore().chartData.setValue(totalMoney);
//                    EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_MONEY,totalMoney));
                }
            });
        }

        iv_goods_add.setNum(foodBean.getQuantity());
        iv_goods_add.setOnNumChangedListener(new AddRemoveView.OnNumChangedListener() {
            @Override
            public boolean onAddListener(int num) {
                foodBean.setQuantity(num);
                if(cb_1.isChecked()) {
                    BeeApplication.appVMStore().chartData.postValue(foodBean.getPrice().intValue());
                }
                Api.getClient(HttpRequest.baseUrl_member).updateQuantity(foodBean.getId() + "", num + "").
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
                return  true;

            }

            @Override
            public boolean onRemoveListener(int num) {
                foodBean.setQuantity(num);
                if(cb_1.isChecked()) {
                    BeeApplication.appVMStore().chartData.postValue(-foodBean.getPrice().intValue());
                }
                if (num == 0) {
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(foodBean.getId());
                    Api.getClient(HttpRequest.baseUrl_member).deleteCartItem(ids).
                            subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSubscriber<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    if (null != getData() && null != foodBean) {
                                        getData().remove(foodBean);
                                        notifyItemRemoved(getData().indexOf(foodBean));
                                    }
                                }

                                @Override
                                public void onFail(String fail) {
                                    super.onFail(fail);
                                }
                            });
                } else {
                    Api.getClient(HttpRequest.baseUrl_member).updateQuantity(foodBean.getId() + "", num + "").
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
                return true;
            }
        });
    }


}
