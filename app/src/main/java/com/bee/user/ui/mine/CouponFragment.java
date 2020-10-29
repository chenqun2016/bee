package com.bee.user.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.YouhuiquanBean;
import com.bee.user.entity.CouponEntity;
import com.bee.user.entity.YouhuiquanEntity;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.NearbyFragment;
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

        crecyclerview.setView(CouponEntity.class);


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
            crecyclerview.addFooterView(foot);
        }
    }


}
