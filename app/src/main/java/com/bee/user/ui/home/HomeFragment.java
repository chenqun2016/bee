package com.bee.user.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocation;
import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.HomeGridview2Bean;
import com.bee.user.entity.LunchEntity;
import com.bee.user.entity.NearbyEntity;
import com.bee.user.event.MainEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.CRecyclerViewActivity;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.adapter.HomeGridview2Adapter;
import com.bee.user.ui.adapter.HomeGridviewAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.location.SelectLocationActivity;
import com.bee.user.ui.mine.AcountSafeActivity;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.search.SearchActivity;
import com.bee.user.utils.BannerUtils;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.MyGridView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/23  13：42
 * 描述：
 */
public class HomeFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipe_refresh_layout;

    @BindView(R.id.appbar)
    public AppBarLayout appbar;

    @BindView(R.id.status_bar1)
    View status_bar1;

    @BindView(R.id.collapsing)
    public CollapsingToolbarLayout collapsing;
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerview;

    @BindView(R.id.tv_dingwei)
    TextView tv_dingwei;

    private ConvenientBanner mBanner;

    private HomeAdapter homeAdapter;

    private List<BannerBean> bannerList = new ArrayList<>();//banner数据
    private List<BannerBean> bannerList3 = new ArrayList<>();//banner3数据
    private DingweiDialog dingweiDialog;



    public static HomeFragment newInstance(int arg) {
        Bundle arguments = new Bundle();
        arguments.putInt("arg", arg);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void getDatas() {
        EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reLocation));

        BannerUtils.getBannerDatas(Constants.BANNER_TYPE_HOME_TOP,mBanner,bannerList);
        BannerUtils.getBannerDatas(Constants.BANNER_TYPE_HOME_MIDELE,banner3,bannerList3);
        getTuijian();
    }




    @OnClick({R.id.ll_tongzhi, R.id.iv_msg, R.id.ll_search, R.id.tv_dingwei})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dingwei:

                startActivity(new Intent(getContext(), SelectLocationActivity.class));

                break;
            case R.id.ll_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.iv_msg:
                if(!ObjectUtils.isEmpty(SPUtils.geTinstance().getUserInfo())) {
                    startActivity(new Intent(getActivity(), NewsActivity.class));
                }else {
                    EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_login));
                }
                break;
            case R.id.ll_tongzhi:
//                if(null != SPUtils.geTinstance().getLocation()){
//                    if (null == dingweiDialog) {
//                        dingweiDialog = new DingweiDialog(getActivity(), new DingweiDialog.OnDingWei() {
//                            @Override
//                            public void onDingWei() {
//
//                                EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reLocation));
//                            }
//                        });
//                    }
//
//                    dingweiDialog.show();
//                }


                break;
        }
    }

    public void  onLocationChanged(){
        if(null != dingweiDialog ){
            dingweiDialog.onMapLoaded();
        }
        AMapLocation amapLocation = SPUtils.geTinstance().getLocation();
        if(null != amapLocation){
            tv_dingwei.setText(amapLocation.getPoiName() );
        }
    }
    public void  onLocationChanged(String detailAddress){
        if(!TextUtils.isEmpty(detailAddress)){
            tv_dingwei.setText(detailAddress );
        }
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        if (null != dingweiDialog && dingweiDialog.isShowing()) {
            dingweiDialog.dismiss();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.activity_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        collapsing.setMinimumHeight(ImmersionBar.getStatusBarHeight(this));

        Drawable background = appbar.getBackground();
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset==" + verticalOffset + "--scrollRangle==" + scrollRangle);

                if (verticalOffset == 0) {
                    collapsing.setAlpha(1);
                    background.setAlpha(255);
                } else if (verticalOffset >= scrollRangle) {
                    collapsing.setAlpha(0);
                    background.setAlpha(0);
                } else {
                    //保留一位小数
                    float alpha = (scrollRangle - Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    collapsing.setAlpha(alpha);

                    background.setAlpha((int)(alpha*255));
                    appbar.setBackground(background);
                }

                //滑动冲突
                swipe_refresh_layout.setEnabled(verticalOffset >= 0);
            }
        });


        View headerViewBanner = View.inflate(getContext(), R.layout.item_home_banner, null);
        View headerView2 = View.inflate(getContext(), R.layout.item_home_2, null);
        View headerView3 = View.inflate(getContext(), R.layout.item_home_3, null);
        initHeaderViewBanner(headerViewBanner);
        initHeaderView2(headerView2);
        initHeaderView3(headerView3);


        initSwipeRefreshLayout(swipe_refresh_layout);
        homeAdapter = new HomeAdapter();
        MainActivity activity = (MainActivity) getActivity();
        homeAdapter.setAddChartAnimatorView(activity.fl_content,activity.getAddChartAnimatorEndView());
        homeAdapter.addHeaderView(headerViewBanner);
        homeAdapter.addHeaderView(headerView2);
        homeAdapter.addHeaderView(headerView3);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager( 2, VERTICAL));
        recyclerview.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HomeBean bean = (HomeBean) adapter.getData().get(position);
                Intent intent = new Intent(getContext(), FoodActivity.class);
                intent.putExtra("shopProductId", bean.shopProductId);
                intent.putExtra("storeId",bean.storeId);
                intent.putExtra("skuId",bean.skuId);
                startActivity(intent);
            }
        });
    }

    private void getTuijian() {
        Api.getClient(HttpRequest.baseUrl_shop).homeRecommand().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<HomeBean>>() {
                    @Override
                    public void onSuccess(List<HomeBean> data) {
                        homeAdapter.setNewInstance(data);
                        endSwipeRefreshLayout();
                    }

                    @Override
                    public void onFail(String fail) {
                        endSwipeRefreshLayout();
                        super.onFail(fail);
                    }
                });
    }

    ConvenientBanner banner3;
    private void initHeaderView3(View headerView3) {
        banner3 = headerView3.findViewById(R.id.banner3);
        BannerUtils.initBanner(banner3,bannerList3);

        MyGridView gridview = headerView3.findViewById(R.id.gridview);

        List<HomeGridview2Bean> homeGridview2Beans = new ArrayList<>();
        HomeGridview2Bean bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "00:00:00";
        bean.money = "10.0";
        bean.title = "限时秒杀";

        homeGridview2Beans.add(bean);
        bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "";
        bean.money = "10.0";
        bean.title = "精选午餐";
        homeGridview2Beans.add(bean);
        bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "";
        bean.money = "10.0";
        bean.title = "销量排行榜";
        homeGridview2Beans.add(bean);
        bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "";
        bean.money = "10.0";
        bean.title = "附近好店";
        homeGridview2Beans.add(bean);

        gridview.setAdapter(new HomeGridview2Adapter(getActivity(), homeGridview2Beans));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getContext(), MiaoshaActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent1.putExtra("title", "精选午餐");
                        intent1.putExtra("entity", LunchEntity.class.getName());
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent2.putExtra("title", "销量排行榜");
                        intent2.putExtra("entity", LunchEntity.class.getName());
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent3.putExtra("title", "附近好店");
                        intent3.putExtra("entity", NearbyEntity.class.getName());
                        startActivity(intent3);
                        break;
                }
            }
        });
    }

    private void initHeaderView2(View headerView2) {
        MyGridView gridview = headerView2.findViewById(R.id.gridview);
        HomeGridviewAdapter homeGridviewAdapter = new HomeGridviewAdapter(getActivity());
        gridview.setAdapter(homeGridviewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), FoodByTypeActivity.class);
                intent.putExtra("title", homeGridviewAdapter.strs[i]);
                intent.putExtra("type", i);
                startActivity(intent);
            }
        });
    }

    private void initHeaderViewBanner(View headerViewBanner) {
        mBanner = (ConvenientBanner) headerViewBanner.findViewById(R.id.banner2);
        BannerUtils.initBanner(mBanner,bannerList);
    }

}
