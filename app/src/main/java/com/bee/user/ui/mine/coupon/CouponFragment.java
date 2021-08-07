package com.bee.user.ui.mine.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bee.user.R;
import com.bee.user.bean.CouponBean;
import com.bee.user.bean.YouhuiquanBean;
import com.bee.user.event.MainEvent;
import com.bee.user.params.CouponParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * 创建人：进京赶考
 * 创建时间：2020/10/29  20：50
 * 描述：
 */
public class CouponFragment extends BaseFragment {
    public SwipeRefreshLayout swipe_refresh_layout;
    public RecyclerView recyclerview;

    BaseQuickAdapter myAdapter;
    LoadmoreUtils loadmoreUtils;
    int status;

    public static CouponFragment newInstance(int status) {
        Bundle arguments = new Bundle();
        //状态 0->未使用；1->已使用;2->已失效
        arguments.putInt("status", status);
        CouponFragment fragment = new CouponFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void getDatas() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        recyclerview = view.findViewById(R.id.recyclerview);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        status = arguments.getInt("status", 0);

        //空白页
        View empty = View.inflate(getContext(), R.layout.empty_trade_list, null);
        TextView tv_guangguang = empty.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainEvent mainEvent = new MainEvent(MainEvent.TYPE_set_index);
                mainEvent.index = 1;
                EventBus.getDefault().post(mainEvent);

                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        myAdapter = new couponAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(myAdapter);
        loadmoreUtils = new LoadmoreUtils() {
            @Override
            protected void getDatas(int page) {
                super.getDatas(page);
                getCouponDatas(page);
            }
        };
        loadmoreUtils.initLoadmore(myAdapter, swipe_refresh_layout);
        loadmoreUtils.setEmptyView(empty);
        loadmoreUtils.refresh(myAdapter);
    }

    private void getCouponDatas(int page) {
        CouponParams couponParams = new CouponParams();
        couponParams.pageSize = LoadmoreUtils.PAGE_SIZE;
        couponParams.pageNum = page;
        couponParams.data = new CouponParams.DataBean(status);

        Api.getClient(HttpRequest.baseUrl_pay).couponList(Api.getRequestBody(couponParams))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponBean>() {
                    @Override
                    public void onSuccess(CouponBean bean) {
                        loadmoreUtils.onSuccess(myAdapter, bean.records);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(myAdapter, fail);
                    }
                });
    }

    public static class couponAdapter extends BaseQuickAdapter<YouhuiquanBean, BaseViewHolder> implements LoadMoreModule {

        public couponAdapter() {
            super(R.layout.item_coupon);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, YouhuiquanBean item) {
            TextView tv_money = helper.findView(R.id.tv_money);
            TextView tv_money_value = helper.findView(R.id.tv_money_value);
            TextView tv_limit = helper.findView(R.id.tv_limit);
            TextView tv_des1 = helper.findView(R.id.tv_des1);
            TextView tv_des2 = helper.findView(R.id.tv_des2);
            TextView tv_des3 = helper.findView(R.id.tv_des3);

            tv_money_value.setText(item.faceValue + "");
            tv_limit.setText("满" + item.minAmount + "元可用");
            tv_des1.setText(item.cardName + "");
            tv_des2.setText(CommonUtil.getNomalTime(item.expireTime));
            tv_des3.setText("通用");

            if (item.type == 0) {
                tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_FF6200));
                tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_FF6200));
                tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_282525));
                tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));
                tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_7C7877));


            } else {
                tv_money.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
                tv_money_value.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
                tv_limit.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
                tv_des1.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
                tv_des2.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
                tv_des3.setTextColor(tv_money.getResources().getColor(R.color.color_ccc));
            }
        }
    }
}
