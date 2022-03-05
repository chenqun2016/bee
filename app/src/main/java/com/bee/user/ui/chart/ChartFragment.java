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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bee.user.BeeApplication;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.event.MainEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.adapter.ChartAdapter;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.NetWorkUtil;
import com.bee.user.utils.ToastUtil;
import com.bee.user.widget.ChartNoDataDrawerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.tv_clear)
    TextView tv_clear;

    @BindView(R.id.status_bar1)
    View status_bar1;

    Unbinder bind;
    @BindView(R.id.ll_nonet)
    LinearLayout ll_nonet;
    @BindView(R.id.ll_havedata)
    RelativeLayout ll_havedata;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.tv_heji_money)
    TextView tv_heji_money;

    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @BindView(R.id.cl_qujiesuan)
    ConstraintLayout cl_qujiesuan;

    HomeAdapter homeAdapter;
    ChartNoDataDrawerView ll_products;
    private  ChartAdapter adapter;

    View ll_empty;
    RecyclerView rv_chat_datas;

    HashMap<Integer, List<ChartBean>> integerListHashMap = new HashMap<>();
    private List<ChartBean> mAvalableBeans;
    private List<ChartBean> mUnAvalableBeans;
    private AddressBean mAddress;


    @OnClick({R.id.tv_confirm,R.id.tv_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_confirm:
                if(null != mAvalableBeans){
                    ArrayList<Integer> intss = new ArrayList<>();
                    ArrayList<Integer>  storeIds = new ArrayList<>();
                    for(int i = 0; i< mAvalableBeans.size(); i++){
                        if(mAvalableBeans.get(i).isSelected){
                            intss.add(mAvalableBeans.get(i).getId());
                            if(!storeIds.contains(mAvalableBeans.get(i).getStoreId())){
                                storeIds.add(mAvalableBeans.get(i).getStoreId());
                            }
                        }
                    }
                    if(intss.size()==0){
                        ToastUtil.showSafeToast(getActivity(),"请选择商品");
                        return;
                    }
                    Intent intent = new Intent(getContext(), OrderingActivity.class);
                    intent.putExtra("operationType",2);
                    intent.putExtra("ids",intss);
                    intent.putIntegerArrayListExtra("storeIds",storeIds);
                    startActivity(intent);
                }

                break;

            case R.id.tv_clear:

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
                                adapter.setNewInstance(new ArrayList<>());

                                setNoDataViews(mUnAvalableBeans);
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
        getInitDatas();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        EventBus.getDefault().unregister(this);
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
        EventBus.getDefault().register(this);
        initViews();
    }


    private void initViews() {
        BeeApplication.appVMStore().chartData.observe(this,new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(null != integer){
                    totalMoney += integer;
                    LogUtil.d("totalMoney = "+totalMoney);
                    tv_heji_money.setText("¥"+totalMoney);
                }
            }
        });
        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        ll_nonet.setVisibility(View.GONE);
        ll_havedata.setVisibility(View.VISIBLE);
        initView();

    }

    private void getInitDatas() {
        if(NetWorkUtil.isNetConnected(getContext())){
            ll_nonet.setVisibility(View.GONE);
            ll_havedata.setVisibility(View.VISIBLE);
            getAddress();
            getTuijian();
        }else{
            ll_nonet.setVisibility(View.VISIBLE);
            ll_havedata.setVisibility(View.GONE);
        }
    }

    //TODO 有没有查询地址单独的接口？
    private void getAddress() {
        Api.getClient(HttpRequest.baseUrl_member).listAddress().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<AddressBean>>() {
                    @Override
                    public void onSuccess(List<AddressBean> addressBean2) {
                        if(null != addressBean2 && addressBean2.size()>0){
                            mAddress = addressBean2.get(0);
                            getCartDatas();
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg, int errorCode) {
                        super.onFail(errorMsg, errorCode);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isChartDataDurty){
            getAddress();
            isChartDataDurty = false;
        }
    }

    private void getCartDatas() {
        if(null == mAddress){
            swiperefresh.setRefreshing(false);
            return ;
        }
        List<String> integers = new ArrayList<>();
//                integers.add(16+"");
        Api.getClient(HttpRequest.baseUrl_member).getCart(mAddress.id,integers)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<ChartBean>>() {
                    @Override
                    public void onSuccess(List<ChartBean> beans) {

                        if(beans!= null && beans.size()>0){
                            totalMoney = 0;
                            tv_heji_money.setText("¥"+totalMoney);
                            caculate(beans);
                        }else{
                            setNoDataViews(beans);
                        }
                        swiperefresh.setRefreshing(false);
                    }

                    @Override
                    public void onFail(String fail) {
                        swiperefresh.setRefreshing(false);
                        super.onFail(fail);
                    }
                });
    }

    private void setHasDatas(Boolean hasDatas){
        if(hasDatas){
            ll_empty.setVisibility(View.GONE);
            rv_chat_datas.setVisibility(View.VISIBLE);
            tv_clear.setVisibility(View.VISIBLE);
            cl_qujiesuan.setVisibility(View.VISIBLE);
        }else{
            ll_empty.setVisibility(View.VISIBLE);
            rv_chat_datas.setVisibility(View.GONE);
            tv_clear.setVisibility(View.GONE);
            cl_qujiesuan.setVisibility(View.GONE);
        }
    }

    private void setNoDataViews(List<ChartBean> beans) {
        setHasDatas(false);

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
        ll_products.setDatas(chartBeans);
    }

    private void caculate(List<ChartBean> beans) {
        List<ChartBean> avalableBeans = new ArrayList<>();
        List<ChartBean> unAvalableBeans = new ArrayList<>();
        for(ChartBean bean : beans){
            if(bean.distributionStatus != 0){
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
        if(lists.size()>0){
            setHasDatas(false);
            adapter.setNewInstance(lists);
            if(mUnAvalableBeans.size()>0){
                ll_products.setDatas(mUnAvalableBeans);
            }
        }else{
            setNoDataViews(unAvalableBeans);
        }
    }

    private void initView() {
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (List<ChartBean> beans : adapter.getData()){
                    if(beans.size()>0){
                        beans.get(0).selectedAll = b;
                    }
                }
                adapter.notifyDataSetChanged();

                LogUtil.d("totalMoney = 0");
                totalMoney = 0;
                if(b){
                    for (List<ChartBean> beans : adapter.getData()){
                        for(ChartBean  bean : beans){
                            totalMoney += bean.getPrice().intValue()*bean.getQuantity();
                        }
                    }
                }
                tv_heji_money.setText("¥"+totalMoney);
            }
        });
        swiperefresh.setColorSchemeResources(com.huaxiafinance.www.crecyclerview.R.color.colorPrimary,
                com.huaxiafinance.www.crecyclerview.R.color.colorPrimaryDark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInitDatas();
            }
        });
        View head = View.inflate(getContext(), R.layout.head_fragment_chart_nodata, null);
        ImageView imageview = head.findViewById(R.id.imageview);
        Picasso.with(getContext()).
                load(R.drawable.banner).
                fit().
                transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),10),0, PicassoRoundTransform.CornerType.ALL)).
                into(imageview);
        ll_empty = head.findViewById(R.id.ll_empty);
        rv_chat_datas = head.findViewById(R.id.rv_chat_datas);
        adapter = new ChartAdapter();
        rv_chat_datas.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_chat_datas.setHasFixedSize(false);
        rv_chat_datas.setAdapter(adapter);
        adapter.addChildClickViewIds(R.id.tv_clear, R.id.tv_store);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                ChartBean item = (ChartBean) adapter.getItem(position);

                switch (view.getId()){
                    case R.id.tv_store:
                        Intent intent = new Intent(getContext(), StoreActivity.class);
                        List<ChartBean> data = (List<ChartBean>) adapter.getData().get(position);

                        if(null != data && null != data.get(0)){
                            intent.putExtra("id",data.get(0).getStoreId()+"");
                        }

                        startActivity(intent);
                        break;
                    case R.id.tv_clear:
                        adapter.removeAt(position);
                        break;
                    default:
                        break;
                }
            }
        });
        View tv_guangguang = head.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainEvent mainEvent = new MainEvent(MainEvent.TYPE_set_index);
                mainEvent.index = 1;
                EventBus.getDefault().post(mainEvent);
            }
        });
        ll_products = head.findViewById(R.id.ll_products);

        homeAdapter = new HomeAdapter();
        homeAdapter.addHeaderView(head);
        MainActivity activity = (MainActivity) getActivity();
        homeAdapter.setListener(new HomeAdapter.OnAddChartListener() {
            @Override
            public void onAddChart() {
                getCartDatas();
            }
        });
        homeAdapter.setAddChartAnimatorView(activity.fl_content,activity.getAddChartAnimatorEndView());
        homeAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                StoreFoodItem2Bean bean = (StoreFoodItem2Bean) adapter.getData().get(position);
                FoodActivity.newInstance(getContext(),bean.shopProductId,bean.storeId,bean.skuId);
            }
        });
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL));
        recyclerview.setAdapter(homeAdapter);
        homeAdapter.setNewInstance(new ArrayList<>());
    }

    private int totalMoney;

    private boolean isChartDataDurty = false;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChartFragmentEvent(ChartFragmentEvent event) {
        switch (event.type){
            case ChartFragmentEvent.TYPE_REFLUSH:
                if(isResumed()){
                    getAddress();
                }else{
                    isChartDataDurty = true;
                }
                break;
        }
    }


    private void getTuijian() {
        Api.getClient(HttpRequest.baseUrl_shop).homeRecommand().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<StoreFoodItem2Bean>>() {
                    @Override
                    public void onSuccess(List<StoreFoodItem2Bean> data) {
                        homeAdapter.setNewInstance(data);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }
}
