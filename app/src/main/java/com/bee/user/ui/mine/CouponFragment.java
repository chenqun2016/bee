package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.CardBean;
import com.bee.user.bean.YouhuiquanBean;
import com.bee.user.entity.CouponEntity;
import com.bee.user.entity.MyCommentEntity;
import com.bee.user.entity.YouhuiquanEntity;
import com.bee.user.ui.adapter.CouponFootListAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.NearbyFragment;
import com.bee.user.ui.order.OrderActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建人：进京赶考
 * 创建时间：2020/10/29  20：50
 * 描述：
 */
public class CouponFragment extends BaseFragment {

    public  CRecyclerView crecyclerview;

    public static CouponFragment newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type",type);
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
        crecyclerview = view.findViewById(R.id.crecyclerview);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        int type = arguments.getInt("type", 0);


        //空白页
        View empty = View.inflate(getContext(), R.layout.empty_trade_list, null);
        TextView tv_guangguang  = empty.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OrderActivity.class));
            }
        });


        crecyclerview.setView(CouponEntity.class);
        crecyclerview.setView(MyCommentEntity.class);


//        crecyclerview.start();


        List<YouhuiquanBean> lists = new ArrayList<>();
        lists.add(new YouhuiquanBean(type));
        lists.add(new YouhuiquanBean(type));
        lists.add(new YouhuiquanBean(type));
        lists.add(new YouhuiquanBean(type));
        lists.add(new YouhuiquanBean(type));
        crecyclerview.getBaseAdapter().setList(lists);
        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd(true);

        if(0 == type){
            View foot = View.inflate(getContext(), R.layout.foot_coupon, null);
            ListView list = foot.findViewById(R.id.list);


            List<CardBean> cardBeans = new ArrayList<>();
            cardBeans.add(new CardBean(0,"连续包月卡","有效期至2020-7-1至2020-9-30"));
            cardBeans.add(new CardBean(1,"连续包季卡","有效期至2020-7-1至2020-9-30"));
            cardBeans.add(new CardBean(2,"连续包年卡","有效期至2020-7-1至2020-9-30"));


            list.setAdapter(new CouponFootListAdapter(getContext(),cardBeans));
            crecyclerview.addFooterView(foot);
        }
    }


}
