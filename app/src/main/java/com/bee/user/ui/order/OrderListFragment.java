package com.bee.user.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.OrderBean;
import com.bee.user.bean.OrderListBean;
import com.bee.user.params.OrderListParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.OrderFragmentAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  14：13
 * 描述：
 */
public class OrderListFragment extends BaseFragment {

//    public  CRecyclerView crecyclerview;


    public RecyclerView recyclerview;
    int type;
    OrderFragmentAdapter mAdapter;
    LoadmoreUtils loadmoreUtils;


    public static OrderListFragment newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type", type);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected void getDatas() {
        loadmoreUtils.refresh(mAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.model_crecyclerview,container,false);
//        crecyclerview = view.findViewById(R.id.crecyclerview);


        View view = inflater.inflate(R.layout.crecyclerview_base, container, false);
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

                CommonUtil.showOrderDetailActivity(recyclerview.getContext(),mAdapter.getData().get(position).getOrderItemType());

            }
        });
        recyclerview.setAdapter(mAdapter);


        initLoadMore();
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
            protected boolean getDatas(int page) {
                getDatasNew(page);
                return true;
            }

            @Override
            protected List getSampleData(int lenth) {

                ArrayList<OrderBean> beans = new ArrayList<OrderBean>();
//                int t = type == OrderBean.type2?OrderBean.type2 :OrderBean.type1;

//                if(type == 0){
//                    beans.add(new OrderBean(Constants.TYPE_READY));
//                    beans.add(new OrderBean(Constants.TYPE_PEISONG));
//                    beans.add(new OrderBean(Constants.TYPE_CANCELED));
//
//                    beans.add(new OrderBean(Constants.TYPE_PAY_WAITE));
//                    beans.add(new OrderBean(Constants.TYPE_TUIKUAN));
//                    beans.add(new OrderBean(Constants.TYPE_TO_BE_COMMENTED));
//
//                }else{
//                    beans.add(new OrderBean(type));
//                    beans.add(new OrderBean(type));
//                    beans.add(new OrderBean(type));
//                    beans.add(new OrderBean(type));
//                }

                return beans;
            }
        };
        loadmoreUtils.initLoadmore(mAdapter);
    }

    private void getDatasNew(int page){
        OrderListParams params = new OrderListParams();
        params.pageNum = page;
        params.pageSize = LoadmoreUtils.PAGE_SIZE;
        params.data = new OrderListParams.OrderListInnerDataClass(type);
        Api.getClient(HttpRequest.baseUrl_order).orderList(Api.getRequestBody(params)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderListBean>() {
                    @Override
                    public void onSuccess(OrderListBean o) {
                        List<OrderBean> beans = o.records;
                        loadmoreUtils.onSuccess(mAdapter,beans);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(mAdapter,fail);
                    }
                });
    }

    /**
     * 刷新
     */
    private void refresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载

        loadmoreUtils.refresh(mAdapter);
    }
}
