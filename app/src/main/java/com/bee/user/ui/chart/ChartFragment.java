package com.bee.user.ui.chart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddressBean2;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.HomeBean;
import com.bee.user.event.MainEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.ChartAdapter;
import com.bee.user.ui.adapter.ChartUnavalabeRecyclerviewAdapter;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.NetWorkUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/03  21：42
 * 描述：
 */
public class ChartFragment extends BaseFragment {


    @BindView(R.id.status_bar1)
    View status_bar1;

    Unbinder bind;
    @BindView(R.id.ll_nonet)
    LinearLayout ll_nonet;
    @BindView(R.id.ll_nodata)
    LinearLayout ll_nodata;
    @BindView(R.id.ll_havedata)
    RelativeLayout ll_havedata;

    @BindView(R.id.swiperefresh_tuijian)
    SwipeRefreshLayout swiperefresh_tuijian;
    @BindView(R.id.recyclerview_tuijian)
    RecyclerView recyclerview_tuijian;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @BindView(R.id.checkbox)
    CheckBox checkbox;

    private  ChartAdapter adapter;

    LoadmoreUtils loadmoreUtils;

    HashMap<Integer, List<ChartBean>> integerListHashMap = new HashMap<>();
    private List<ChartBean> mAvalableBeans;
    private List<ChartBean> mUnAvalableBeans;
    private AddressBean2 mAddress;


    @OnClick({R.id.tv_confirm,R.id.tv_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_confirm:
                if(null != mAvalableBeans){
                    ArrayList<Integer> intss = new ArrayList<>();
                    for(int i = 0; i< mAvalableBeans.size(); i++){
                        intss.add(mAvalableBeans.get(i).getId());
                    }
                    Intent intent = new Intent(getContext(), OrderingActivity.class);
                    intent.putExtra("operationType",2);
                    intent.putExtra("ids",intss);
                    intent.putIntegerArrayListExtra("storeIds",new ArrayList<>(integerListHashMap.keySet()));
                    startActivity(intent);
                }

                break;

            case R.id.tv_clear:
                adapter.setNewInstance(new ArrayList<>());


                ll_nonet.setVisibility(View.GONE);
                ll_nodata.setVisibility(View.VISIBLE);
                ll_havedata.setVisibility(View.GONE);

//                initNoNet();
                initNoDatas();
//                initDatas();

                List<String> ints = new ArrayList<String>();
                for(ChartBean bean: mAvalableBeans){
                    ints.add(bean.getStoreId()+"");
                }

                Api.getClient(HttpRequest.baseUrl_member).clearCartInfo(ints).
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
                break;
            default:
                break;
        }

    }

    @Override
    protected void getDatas() {
        loadmoreUtils.refresh(adapter);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart_nodata, container, false);
        bind = ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();

    }

    private void initViews() {
        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);

        ll_nonet.setVisibility(View.GONE);
        ll_nodata.setVisibility(View.GONE);
        ll_havedata.setVisibility(View.VISIBLE);

        initNoNet();
        initNoDatas();
        initDatas();

        getInitDatas();


    }

    private void getInitDatas() {
        if(NetWorkUtil.isNetConnected(getContext())){
            ll_nonet.setVisibility(View.GONE);
            ll_nodata.setVisibility(View.VISIBLE);
            ll_havedata.setVisibility(View.GONE);
            getAddress();
        }else{
            ll_nonet.setVisibility(View.VISIBLE);
            ll_nodata.setVisibility(View.GONE);
            ll_havedata.setVisibility(View.GONE);
        }
        swiperefresh_tuijian.setRefreshing(false);
    }

    private void getAddress() {
        Api.getClient(HttpRequest.baseUrl_member).listAddress().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<AddressBean2>>() {
                    @Override
                    public void onSuccess(List<AddressBean2> addressBean2) {
                        if(null != addressBean2 && addressBean2.size()>0){
                            mAddress = addressBean2.get(0);

                            getCartDatas();
                        }
                    }
                });
    }

    private void initDatas() {
        adapter     = new ChartAdapter();

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);

        adapter.addChildClickViewIds(R.id.tv_clear, R.id.tv_store);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                ChartBean item = (ChartBean) adapter.getItem(position);

                switch (view.getId()){
                    case R.id.tv_store:
                        startActivity(new Intent(getContext(), StoreActivity.class));
                        break;
                    case R.id.tv_clear:
                        adapter.removeAt(position);
                        break;
                }
            }
        });


        View foot = View.inflate(getContext(), R.layout.foot_chart, null);
        ImageView imageview = foot.findViewById(R.id.imageview);
        Picasso.with(getContext()).
                load(R.drawable.banner).
                fit().
                transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),10),0, PicassoRoundTransform.CornerType.ALL)).
                into(imageview);
        adapter.addFooterView(foot);

        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setItemViewCacheSize(200);
        RecyclerView.RecycledViewPool recycledViewPool = new
                RecyclerView.RecycledViewPool();
        recyclerview.setRecycledViewPool(recycledViewPool);

//        ArrayList<ChartBean> beans = new ArrayList<>();
//        beans.add(new ChartBean());
//        beans.add(new ChartBean());
//        beans.add(new ChartBean());
//        beans.add(new ChartBean());
//        beans.add(new ChartBean());
//        adapter.setNewInstance(beans);


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (List<ChartBean> beans : adapter.getData()){
                    for(ChartBean bean :beans){
                        bean.selectedAll = b;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });


        loadmoreUtils = new LoadmoreUtils(ChartBean.class){
            @Override
            protected boolean getDatas(int page) {
                getCartDatas();

                return true;
            }
        };
        loadmoreUtils.initLoadmore(adapter);
    }

    private void getCartDatas() {
        if(null == mAddress){
            return ;
        }
        List<String> integers = new ArrayList<>();
//                integers.add(16+"");
        Api.getClient(HttpRequest.baseUrl_member).getCart(mAddress.getId(),integers)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<ChartBean>>() {
                    @Override
                    public void onSuccess(List<ChartBean> beans) {

                        if(beans!= null && beans.size()>0 && beans.get(0).distributionStatus == 1){
                            ll_nonet.setVisibility(View.GONE);
                            ll_nodata.setVisibility(View.GONE);
                            ll_havedata.setVisibility(View.VISIBLE);

                            caculate(beans);
                        }else{
                            ll_nonet.setVisibility(View.GONE);
                            ll_nodata.setVisibility(View.VISIBLE);
                            ll_havedata.setVisibility(View.GONE);

                            setNoDataViews(beans);
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(adapter,fail);
                    }
                });
    }

    private void setNoDataViews(List<ChartBean> beans) {
        HashMap<Integer, List<ChartBean>> objectObjectHashMap = new HashMap<>();
        List<ChartBean> chartBeans = null;
        for(ChartBean item : beans){
            if(null == objectObjectHashMap.get(item.getStoreId())  ){
                chartBeans = new ArrayList<>();
                chartBeans.add(item);
                objectObjectHashMap.put(item.getStoreId(),chartBeans);
            }else{
                List<ChartBean> beans1 = objectObjectHashMap.get(item.getStoreId());
                beans1.add(item);
            }
        }
        chartBeans = new ArrayList<>();
        Collection<List<ChartBean>> values = objectObjectHashMap.values();
        for(List<ChartBean> lists : values){
            chartBeans.addAll(lists);
        }

        mUnAvalableBeans = chartBeans;
        chartUnavalabeRecyclerviewAdapter.setNewInstance(chartBeans);

        recyclerView_unavalable_data.setVisibility(View.VISIBLE);
        tv_shang.setVisibility(View.VISIBLE);
    }

    private void caculate(List<ChartBean> beans) {
        List<ChartBean> avalableBeans = new ArrayList<>();
        List<ChartBean> unAvalableBeans = new ArrayList<>();
        for(ChartBean bean : beans){
            if(bean.distributionStatus == 1){
                avalableBeans.add(bean);
            }else{
                unAvalableBeans.add(bean);
            }
        }
        mAvalableBeans = avalableBeans;
        mUnAvalableBeans = unAvalableBeans;


        integerListHashMap.clear();
        List<ChartBean> chartBeans = null;
        for(ChartBean item : avalableBeans){
            if(null == integerListHashMap.get(item.getStoreId())  ){
                chartBeans = new ArrayList<>();
                chartBeans.add(item);
                integerListHashMap.put(item.getStoreId(),chartBeans);
            }else{
                List<ChartBean> beans1 = integerListHashMap.get(item.getStoreId());
                beans1.add(item);
            }
        }
        ArrayList<List<ChartBean>> lists = new ArrayList<>(integerListHashMap.values());
        adapter.setNewInstance(lists);
        loadmoreUtils.onSuccess(adapter,lists);
    }

    private void initNoNet() {

    }
    TextView tv_xia;
    RecyclerView recyclerView_unavalable_data;
    TextView tv_shang;
    ChartUnavalabeRecyclerviewAdapter chartUnavalabeRecyclerviewAdapter;
    private void initNoDatas() {

        swiperefresh_tuijian.setColorSchemeResources(com.huaxiafinance.www.crecyclerview.R.color.colorPrimary,
                com.huaxiafinance.www.crecyclerview.R.color.colorPrimaryDark);
        swiperefresh_tuijian.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInitDatas();
            }
        });

        HomeAdapter homeAdapter = new HomeAdapter();
        recyclerview_tuijian.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerview_tuijian.setAdapter(homeAdapter);

        View head = View.inflate(getContext(), R.layout.head_fragment_chart_nodata, null);
        View tv_guangguang = head.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainEvent mainEvent = new MainEvent(MainEvent.TYPE_set_index);
                mainEvent.index = 1;
                EventBus.getDefault().post(mainEvent);
            }
        });
        tv_xia = head.findViewById(R.id.tv_xia);
        recyclerView_unavalable_data = head.findViewById(R.id.recyclerView_unavalable_data);
        recyclerView_unavalable_data.setLayoutManager(new LinearLayoutManager(getActivity()));
        chartUnavalabeRecyclerviewAdapter = new ChartUnavalabeRecyclerviewAdapter();
        recyclerView_unavalable_data.setAdapter(chartUnavalabeRecyclerviewAdapter);
        tv_shang = head.findViewById(R.id.tv_shang);
        homeAdapter.addHeaderView(head);

        homeAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(getContext(), FoodActivity.class));
            }
        });

        ArrayList<HomeBean> homeBeans = new ArrayList<>();
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeAdapter.setNewInstance(homeBeans);
    }
}
