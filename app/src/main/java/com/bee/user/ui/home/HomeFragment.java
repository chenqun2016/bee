package com.bee.user.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.HomeGridview2Bean;
import com.bee.user.entity.LunchEntity;
import com.bee.user.entity.NearbyEntity;
import com.bee.user.ui.CRecyclerViewActivity;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.adapter.HomeGridview2Adapter;
import com.bee.user.ui.adapter.HomeGridviewAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.search.SearchActivity;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.MyGridView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/23  13：42
 * 描述：
 */
public class HomeFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.appbar)
    public AppBarLayout appbar;

    @BindView(R.id.collapsing)
    public CollapsingToolbarLayout collapsing;
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerview;

    @BindView(R.id.tv_dingwei)
    TextView tv_dingwei;


    //声明定位回调监听器
    //异步获取定位结果
    private AMapLocationListener mAMapLocationListener   = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    LogUtil.d("location=="+amapLocation.getCity()+"//code=="+amapLocation.getCityCode());
                    tv_dingwei.setText(amapLocation.getCity()+amapLocation.getDistrict()+amapLocation.getStreet()+amapLocation.getStreetNum());
                    CityPicker.from(HomeFragment.this).locateComplete(new LocatedCity(amapLocation.getCity(), amapLocation.getProvince(), amapLocation.getAdCode()), LocateState.SUCCESS);
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
    private AMapLocationClient   mLocationClient;


    private List<BannerBean> adsList = new ArrayList<>();//banner数据
    private Dialog dingweiDialog;

    public static HomeFragment newInstance(int arg) {
        Bundle arguments = new Bundle();
        arguments.putInt("arg", arg);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void getDatas() {
        initLocations();

    }



    @OnClick({R.id.ll_tongzhi,R.id.iv_msg,R.id.ll_search,R.id.tv_dingwei})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dingwei:
               showCityPicker();
                break;
            case R.id.ll_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.iv_msg:
                startActivity(new Intent(getContext(),NewsActivity.class));
                break;
            case R.id.ll_tongzhi:

                if (null == dingweiDialog) {
                    dingweiDialog = new Dialog(getActivity(), R.style.loadingDialogTheme);
                    View inflate = View.inflate(getActivity(), R.layout.dialog_home_ditu, null);
                    inflate.findViewById(R.id.tv_guangguang).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != dingweiDialog) {
                                dingweiDialog.dismiss();
                            }

                        }
                    });
                    dingweiDialog.setContentView(inflate);
                }

                dingweiDialog.show();

                break;
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

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset=="+verticalOffset+"--scrollRangle=="+scrollRangle);

                if (verticalOffset == 0) {
                    collapsing.setAlpha(1);
                } else if(verticalOffset >= scrollRangle){
                    collapsing.setAlpha(0);

                }else{
                    //保留一位小数
                    float alpha =(scrollRangle - Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    collapsing.setAlpha(alpha);
                }

            }
        });


        View headerViewBanner = View.inflate(getContext(), R.layout.item_home_banner, null);
        View headerView2 = View.inflate(getContext(), R.layout.item_home_2, null);
        View headerView3 = View.inflate(getContext(), R.layout.item_home_3, null);
        initHeaderViewBanner(headerViewBanner);
        initHeaderView2(headerView2);
        initHeaderView3(headerView3);


        HomeAdapter homeAdapter = new HomeAdapter();
        homeAdapter.addHeaderView(headerViewBanner);
        homeAdapter.addHeaderView(headerView2);
        homeAdapter.addHeaderView(headerView3);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerview.setAdapter(homeAdapter);
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


    private void initHeaderView3(View headerView3) {
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
                        Intent   intent = new Intent(getContext(), MiaoshaActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent   intent1 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent1.putExtra("title", "精选午餐");
                        intent1.putExtra("entity", LunchEntity.class.getName());
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent   intent2 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent2.putExtra("title", "销量排行榜");
                        intent2.putExtra("entity", LunchEntity.class.getName());
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent  intent3 = new Intent(getContext(), CRecyclerViewActivity.class);
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
                intent.putExtra("title",homeGridviewAdapter.strs[i]);
                intent.putExtra("type",i);
                startActivity(intent);
            }
        });
    }

    private void initHeaderViewBanner(View headerViewBanner) {
        ConvenientBanner mBanner = (ConvenientBanner) headerViewBanner.findViewById(R.id.banner2);
//        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
//        params2.width = DisplayUtil.getWindowWidth(getActivity());
//        params2.height = (int) ((params2.width - DisplayUtil.dip2px(getContext(), 30)) * Constants.RATE_HOME) + DisplayUtil.dip2px(getContext(), 35);
//        mBanner.setLayoutParams(params2);
        BannerBean bannerBean = new BannerBean();
        bannerBean.url = R.drawable.banner;
        adsList.add(bannerBean);
        adsList.add(bannerBean);
        adsList.add(bannerBean);
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new BannerImageHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_home_banner_image;
            }


        }, adsList);
        mBanner.setPageIndicator(new int[]{R.drawable.point_banner_yellow, R.drawable.point_banner_grey});
        mBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

    }

    public void showCityPicker(){
        MainActivity activity = (MainActivity) getActivity();
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));


        activity.setStatusBar(1);
        CityPicker.from(this)
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        tv_dingwei.setText(data.getName());
                        Toast.makeText(
                                getContext(),
                                String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                Toast.LENGTH_SHORT)
                                .show();

                        activity.setStatusBar(0);
                    }

                    @Override
                    public void onCancel() {
                        activity.setStatusBar(0);
                        Toast.makeText(getContext(), "取消选择", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLocate() {
                        LogUtil.d("onLocate");
                        mLocationClient.stopLocation();
//                        启动定位
                        mLocationClient.startLocation();


                    }
                })
                .show();
    }

    private void initLocations() {

        //初始化定位
        mLocationClient  = new AMapLocationClient(getActivity().getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//        mLocationOption.setInterval(1000);

        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

}
