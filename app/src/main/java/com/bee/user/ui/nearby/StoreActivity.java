package com.bee.user.ui.nearby;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddCartBean;
import com.bee.user.bean.AddChartBean;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.FoodTypeBean;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.bean.StoreFoodItem1Bean;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.event.AddChartEvent;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.event.CloseEvent;
import com.bee.user.event.StoreEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.FoodChooseTypeAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.search.SearchFoodActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.AddRemoveView;
import com.bee.user.widget.ChartBottomDialogView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/27  14：41
 * 描述：
 */
public class StoreActivity extends BaseActivity {
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.image_xuangou)
    ImageView image_xuangou;

    @BindView(R.id.app_barbar)
    AppBarLayout app_barbar;

    @BindView(R.id.status_bar1)
    View status_bar1;
    @BindView(R.id.background)
    View background;


    //titlebar
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_search_1)
    TextView tv_search_1;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_shoucang)
    ImageView iv_shoucang;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.tv_dingwei)
    ImageView tv_dingwei;


    //商家信息
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.ll_mark)
    LinearLayout ll_mark;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_sells)
    TextView tv_sells;

    @BindView(R.id.tv_store_bg)
    ImageView tv_store_bg;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;


    //底部dialog
    @BindView(R.id.chart_bottom_dialog_view)
    ChartBottomDialogView chart_bottom_dialog_view;


    //去结算
    @BindView(R.id.cl_qujiesuan)
    ConstraintLayout cl_qujiesuan;
    @BindView(R.id.tv_xuangou_tag)
    TextView tv_xuangou_tag;
    @BindView(R.id.tv_heji)
    TextView tv_heji;
    @BindView(R.id.tv_heji_money_pre)
    TextView tv_heji_money_pre;
    @BindView(R.id.tv_heji_money)
    TextView tv_heji_money;
    @BindView(R.id.tv_des)
    TextView tv_des;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    String[] titles = new String[]{"菜单", "评价", "商家"};
    StoreDetailBean storeDetailBean;
    private Fragment[] mFragments;
    private String id;
    //只要有改变就刷新购物车数据
    private boolean isChanged = false;
    //购物车
    private HashMap<String, AddChartBean> hashMap = new LinkedHashMap<>();

    @OnClick({R.id.tv_confirm, R.id.cl_qujiesuan, R.id.tv_dingwei,
            R.id.tv_search_1, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_confirm:
                ArrayList<Integer> intss = new ArrayList<>();
                ArrayList<Integer> storeIds = new ArrayList<>();
                if (null != hashMap) {
                    for (AddChartBean bean : hashMap.values()) {
                        intss.add(bean.cartItemId);
                        if (!storeIds.contains(bean.storeId)) {
                            storeIds.add(bean.storeId);
                        }
                    }
                }
                startActivity(OrderingActivity.newIntent(this, 2, intss, storeIds));
                break;
            case R.id.cl_qujiesuan:
                chart_bottom_dialog_view.showSelectedDialog();
                break;

            case R.id.tv_dingwei:

                startActivity(new Intent(this, DingWeiActivity.class));
                break;

            case R.id.iv_search:
            case R.id.tv_search_1:
                startActivity(new Intent(this, SearchFoodActivity.class));
                break;
        }

    }


    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (isChanged) {
            EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_REFLUSH));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        mFragments = new Fragment[titles.length];

        chart_bottom_dialog_view.initDatas(DisplayUtil.getWindowHeight(this));


        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);

        ViewGroup.LayoutParams layoutParams1 = background.getLayoutParams();
        layoutParams1.height = ImmersionBar.getStatusBarHeight(this) + DisplayUtil.dip2px(this, 44);

        vp.setAdapter(new FragmentAdapter(this));
        vp.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, vp, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tabLayout.getSelectedTabPosition();
                switch (selectedTabPosition) {
                    case 0:
                        cl_qujiesuan.setVisibility(View.VISIBLE);
                        chart_bottom_dialog_view.showSelectedView(View.VISIBLE);

                        break;
                    case 1:
                    case 2:
                        cl_qujiesuan.setVisibility(View.GONE);
                        chart_bottom_dialog_view.showSelectedView(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tv_dingwei.setVisibility(View.VISIBLE);

        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset==" + verticalOffset + "--scrollRangle==" + scrollRangle);

                if (verticalOffset == 0) {
                    background.setAlpha(0);
                    iv_back.setImageResource(R.drawable.icon_back_bai);
                    tv_search_1.setVisibility(View.GONE);
                    iv_search.setVisibility(View.VISIBLE);
                    iv_shoucang.setImageResource(R.drawable.icon_shoucang);
                    iv_more.setImageResource(R.drawable.icon_more);
                } else if (Math.abs(verticalOffset) >= scrollRangle) {
                    background.setAlpha(1);
                    tv_search_1.setVisibility(View.VISIBLE);
                    iv_back.setImageResource(R.drawable.icon_back_anse);
                    iv_search.setVisibility(View.GONE);
                    iv_shoucang.setImageResource(R.drawable.icon_shoucang_1);
                    iv_more.setImageResource(R.drawable.icon_more_shen);
                } else {
                    //保留一位小数
                    float alpha = (Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    background.setAlpha(alpha);
                }


            }
        });

        getDatas();
    }

    private void getDatas() {


        Api.getClient(HttpRequest.baseUrl_shop).shop_getDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<StoreDetailBean>() {
                    @Override
                    public void onSuccess(StoreDetailBean storeDetailBean) {
                        StoreActivity.this.storeDetailBean = storeDetailBean;
                        setViews(storeDetailBean);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
        //TODO
        List<String> integers = new ArrayList<>();
        integers.add(id);
        //TODO
        Api.getClient(HttpRequest.baseUrl_member).getCart(0, integers)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<ChartBean>>() {
                    @Override
                    public void onSuccess(List<ChartBean> beans) {
                        chart_bottom_dialog_view.reflushAdapter(beans);
                        for (ChartBean bean : beans) {
                            if (bean.getQuantity() > 0) {
                                AddChartBean addCartBean = new AddChartBean(bean.getQuantity(), bean.getProductSkuId(), bean.getStoreId(), BigDecimal.valueOf(bean.getPrice()), bean.getId());
                                hashMap.put(bean.getProductSkuId() + "", addCartBean);
                            } else {
                                hashMap.remove(bean.getProductSkuId() + "");
                            }
                        }
                        resetView();
                        getfragment1Datas();
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }


    private void getfragment1Datas() {
        Api.getClient(HttpRequest.baseUrl_shop)
                .shop_queryProductList(id + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<StoreFoodItem1Bean>>() {
                    @Override
                    public void onSuccess(List<StoreFoodItem1Bean> storeFoodItemBeans) {
                        if (null != storeFoodItemBeans && storeFoodItemBeans.size() > 0) {

                            List<StoreFoodItem2Bean> mDatas = new ArrayList();
                            ArrayList<Observable<BaseResult<List<StoreFoodItem2Bean>>>> observables = new ArrayList<>();

                            for (StoreFoodItem1Bean bean : storeFoodItemBeans) {
                                Map<String, String> map = new HashMap<>();
                                map.put("shopProductCategoryId", bean.getId() + "");
                                map.put("storeId", bean.getStoreId() + "");

                                observables.add(Api.getClient(HttpRequest.baseUrl_goods).findShopProducts(Api.getRequestBody(map)).map(new Function<BaseResult<List<StoreFoodItem2Bean>>, BaseResult<List<StoreFoodItem2Bean>>>() {
                                    @Override
                                    public BaseResult<List<StoreFoodItem2Bean>> apply(BaseResult<List<StoreFoodItem2Bean>> listBaseResult) throws Throwable {
                                        List<StoreFoodItem2Bean> data = listBaseResult.getData();
                                        for(StoreFoodItem2Bean bean1 :data){
                                            bean1.name = bean.getName();
                                        }
                                        return listBaseResult;
                                    }
                                }));

                            }
                            Observable.merge(observables)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseSubscriber<List<StoreFoodItem2Bean>>() {

                                        @Override
                                        public void onComplete() {
                                            super.onComplete();
                                            StoreFragment fragment = (StoreFragment) mFragments[0];
                                            fragment.setFoodDatas(mDatas);
                                        }

                                        @Override
                                        public void onSuccess(List<StoreFoodItem2Bean> storeFoodItem2Beans) {
                                            mDatas.addAll(storeFoodItem2Beans);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    private void setViews(StoreDetailBean storeDetailBean) {

        Picasso.with(this)
                .load(storeDetailBean.getLogoUrl())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(this, 5), 0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_icon);

        Picasso.with(this)
                .load(storeDetailBean.getAppBackgroundUrl())
                .fit()
                .error(R.drawable.bg_chengse)
                .into(tv_store_bg);
        CommonUtil.initTAGViews(ll_mark);

        tv_title.setText(storeDetailBean.getName());
        tv_distance.setText(storeDetailBean.getDistance() + "公里");
        tv_time.setText("大约" + storeDetailBean.getDuration() + "分钟");
        tv_sells.setText("月销" + storeDetailBean.getMonthSalesCount());
    }


    private void showChooseTypeDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_store_bottom_choose);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        FoodChooseTypeAdapter foodChooseTypeAdapter = new FoodChooseTypeAdapter();
        recyclerview.setAdapter(foodChooseTypeAdapter);

        List<String> dataSource = new ArrayList<>();
        dataSource.add("热");
        dataSource.add("温热");
        dataSource.add("多冰");
        dataSource.add("少冰");
        dataSource.add("去冰");
        FoodTypeBean foodTypeBean = new FoodTypeBean();
        foodTypeBean.title = "温度";
        foodTypeBean.lists = dataSource;

        List<FoodTypeBean> datas = new ArrayList<>();
        datas.add(foodTypeBean);
        datas.add(foodTypeBean);

        foodChooseTypeAdapter.setNewInstance(datas);


        bottomSheetDialog.findViewById(R.id.iv_goods_add).setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.iv_goods_comment).setVisibility(View.GONE);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

        }

        bottomSheetDialog.show();
    }

    private Fragment createFragments(Integer index) {
        if (mFragments[index] != null) {
            return mFragments[index];
        }
        Fragment fragment = null;
        switch (index) {
            case 0:

                fragment = StoreFragment.newInstance(DisplayUtil.getWindowHeight(this) - app_barbar.getMeasuredHeight(), id);
                break;
            case 1:
                fragment = new CommentFragment();
                break;
            case 2:
                fragment = new StoreDesFragment();
                break;
        }
        mFragments[index] = fragment;
        return mFragments[index];
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStoreEvent(StoreEvent event) {
        if (event.type == StoreEvent.TYPE_START_ACTIVITY) {
            event.intent.putExtra("data", storeDetailBean);
            startActivity(event.intent);
        } else {
            showChooseTypeDialog();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        if(event.type == CloseEvent.TYPE_PAY){
            finish();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddChartEvent(AddChartEvent event) {
        if(1 == event.type){

            AddChartBean addChartBean = event.addChartBean;
            Map<String, String> map = new HashMap<>();
            map.put("num", "1");
            map.put("skuId", addChartBean.skuId + "");
            map.put("storeId", addChartBean.storeId + "");
//        map.put("skuId", "1032");//"16     1030,1032"  "1077 1078  1079    9"
//        map.put("storeId", "16");
            Api.getClient(HttpRequest.baseUrl_member).addToCart(Api.getRequestBody(map)).
                    subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<AddCartBean>() {
                        @Override
                        public void onSuccess(AddCartBean userBean) {
                            isChanged = true;
                            //添加购物车id
                            addChartBean.cartItemId = userBean.getId();
                            if (addChartBean.num > 0) {
                                hashMap.put(addChartBean.skuId + "", addChartBean);
                            } else {
                                hashMap.remove(addChartBean.skuId + "");
                            }
                            resetView();
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                        }
                    });
        }else{
            AddChartBean addChartBean = event.addChartBean;
            Api.getClient(HttpRequest.baseUrl_member).updateQuantity(addChartBean.cartItemId+"",addChartBean.num+"").
                    subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<String>() {
                        @Override
                        public void onSuccess(String s) {
                            if (addChartBean.num > 0) {
                                hashMap.put(addChartBean.skuId + "", addChartBean);
                            } else {
                                hashMap.remove(addChartBean.skuId + "");
                            }
                            resetView();
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                        }
                    });
        }

    }

    private void resetView() {
        Set<Map.Entry<String, AddChartBean>> entries = hashMap.entrySet();

        int total = 0;
        float totalMoney = 0;
        for (Map.Entry<String, AddChartBean> entry : entries) {
            total += entry.getValue().num;
            totalMoney += entry.getValue().money.multiply(new BigDecimal(entry.getValue().num)).floatValue();
        }

        if (total > 0) {
            tv_xuangou_tag.setText(total + "");
            tv_heji.setText("合计");
            tv_heji_money_pre.setVisibility(View.VISIBLE);
            tv_heji_money.setVisibility(View.VISIBLE);
            tv_heji_money.setText(totalMoney + "");
//            tv_des.setText("");
            tv_confirm.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
            tv_confirm.setEnabled(true);
            cl_qujiesuan.setEnabled(true);
        } else {
            tv_xuangou_tag.setText(total + "");
            tv_heji.setText("未选购商品");
            tv_heji_money_pre.setVisibility(View.INVISIBLE);
            tv_heji_money.setVisibility(View.INVISIBLE);
            tv_heji_money.setText("");
//            tv_des.setText("");
            tv_confirm.setBackgroundResource(R.drawable.btn_gradient_grey_round);
            tv_confirm.setEnabled(false);
            cl_qujiesuan.setEnabled(false);
        }
    }


    final class FragmentAdapter extends FragmentStateAdapter {


        public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return createFragments(position);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }


    public void setAddChartView(AddRemoveView iv_goods_add) {
        iv_goods_add.initAnimalView(fl_content,image_xuangou);
    }
}
