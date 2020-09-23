package com.bee.user.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bee.user.R;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.OrderBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.entity.OrderEntity;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.nearby.NearbyFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  14：13
 * 描述：
 */
public class OrderFragment extends BaseFragment {

    public  CRecyclerView crecyclerview;
    int type;



    public static OrderFragment newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type",type);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected void getDatas() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.model_crecyclerview,container,false);
        crecyclerview = view.findViewById(R.id.crecyclerview);


        Bundle arguments = getArguments();
        type = arguments.getInt("type",0);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        crecyclerview.setView(OrderEntity.class).start();


        ArrayList<OrderBean> beans = new ArrayList<OrderBean>();
        beans.add(new OrderBean(type));
        beans.add(new OrderBean(type));
        beans.add(new OrderBean(type));
        beans.add(new OrderBean(type));

        crecyclerview.getBaseAdapter().setList(beans);
    }


}
