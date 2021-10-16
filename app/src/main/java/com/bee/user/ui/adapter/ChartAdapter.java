package com.bee.user.ui.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  16：08
 * 描述：
 */
public class ChartAdapter extends BaseQuickAdapter<List<ChartBean>, BaseViewHolder> implements LoadMoreModule {

    public ChartAdapter() {
        super(R.layout.fragment_chart_data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, List<ChartBean> storeBean) {
        if(null == storeBean){
            return;
        }
        ChartBean chartBean = storeBean.get(0);

        TextView tv_store = holder.findView(R.id.tv_store);
        tv_store.setText(chartBean.getStoreName()+"");

        RecyclerView recyclerview = holder.findView(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        recyclerview.setHasFixedSize(true);
        ChartFoodItemAdapter chartFoodItemAdapter = new ChartFoodItemAdapter(storeBean);
        recyclerview.setAdapter(chartFoodItemAdapter);

        CheckBox cb_1 = holder.findView(R.id.cb_1);
        if (cb_1 != null) {
            cb_1.setChecked(chartBean.selectedAll);
            LogUtil.d("isChecked1 ==" +  cb_1.isChecked());
            cb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    chartBean.selectedAll = b;
                    for (ChartBean bean : storeBean){
                        bean.isSelected = b;
                    }
                    chartFoodItemAdapter.notifyDataSetChanged();
                    LogUtil.d("onCheckedChanged1 ==" + b);
                }
            });
        }
        holder.findView(R.id.tv_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> ints = new ArrayList<String>();
                ints.add(chartBean.getStoreId()+"");

                Api.getClient(HttpRequest.baseUrl_member).clearCartInfo(ints).
                        subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>() {
                            @Override
                            public void onSuccess(String s) {
                                ChartAdapter.this.remove(storeBean);
                                if(ChartAdapter.this.getData().size()==0){
                                    EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_REFLUSH));
                                }
                            }

                            @Override
                            public void onFail(String fail) {
                                super.onFail(fail);
                            }
                        });
            }
        });
    }
}
