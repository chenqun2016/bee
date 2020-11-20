package com.bee.user.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.OrderBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.adapter.OrderFragmentAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.nearby.NearbyFragment;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  14：13
 * 描述：
 */
public class OrderFragment extends BaseFragment {

//    public  CRecyclerView crecyclerview;


    public RecyclerView recyclerview;
    int type;
    OrderFragmentAdapter mAdapter;
    LoadmoreUtils loadmoreUtils;


    public static OrderFragment newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type", type);
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
//        View view = inflater.inflate(R.layout.model_crecyclerview,container,false);
//        crecyclerview = view.findViewById(R.id.crecyclerview);


        View view = inflater.inflate(R.layout.item_order_fragment, container, false);
        recyclerview = view.findViewById(R.id.recyclerview);
        Bundle arguments = getArguments();
        type = arguments.getInt("type", 0);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        crecyclerview.setView(OrderEntity.class).start();
//
//
//        ArrayList<OrderBean> beans = new ArrayList<OrderBean>();
//        beans.add(new OrderBean(type));
//        beans.add(new OrderBean(type));
//        beans.add(new OrderBean(type));
//        beans.add(new OrderBean(type));
//
//        crecyclerview.getBaseAdapter().setList(beans);


        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new OrderFragmentAdapter();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(recyclerview.getContext(), OrderDetailActivity.class);
                intent.putExtra("type",mAdapter.getData().get(position).type);
                recyclerview.getContext().startActivity(intent);
            }
        });
        recyclerview.setAdapter(mAdapter);


        initLoadMore();
        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.onDestroy();
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        loadmoreUtils = new LoadmoreUtils(OrderBean.class) {
            @Override
            protected List getSampleData(int lenth) {
                ArrayList<OrderBean> beans = new ArrayList<OrderBean>();
                int t = type == OrderBean.type2?OrderBean.type2 :OrderBean.type1;

                beans.add(new OrderBean(t));
                beans.add(new OrderBean(t));
                beans.add(new OrderBean(t));
                beans.add(new OrderBean(t));
                return beans;
            }
        };
        loadmoreUtils.initLoadmore(mAdapter);
    }

    /**
     * 刷新
     */
    private void refresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载

        loadmoreUtils.refresh(mAdapter);
    }
}
