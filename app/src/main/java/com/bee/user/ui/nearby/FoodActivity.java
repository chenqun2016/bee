package com.bee.user.ui.nearby;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.Constants;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.CommentWrapBean;
import com.bee.user.bean.FoodDetailBean;
import com.bee.user.bean.FoodTypeBean;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.event.CloseEvent;
import com.bee.user.params.AddFavoritesParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.StringBannerImageHolder;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.adapter.FoodChooseTypeAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.AddRemoveView;
import com.bee.user.widget.ChartBottomDialogView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.bee.user.Constants.REQUEST_CODE_ORDERING;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  20：48
 * 描述：
 */
public class FoodActivity extends BaseActivity {
//    @BindView(R.id.app_barbar)
//    AppBarLayout app_barbar;

    //    @BindView(R.id.background)
//    View background;

    @BindView(R.id.cl_content)
    FrameLayout cl_content;

    @BindView(R.id.ll_toolbar)
    RelativeLayout ll_toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @BindView(R.id.tv_sure)
    TextView tv_sure;


    @BindView(R.id.scrollview)
    NestedScrollView scrollview;

    @BindView(R.id.fl_title2)
    FrameLayout fl_title2;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


//    @BindView(R.id.ll_head2)
//    ConstraintLayout ll_head2;

    @BindView(R.id.tv_food_title)
    TextView tv_food_title;
    @BindView(R.id.tv_selled)
    TextView tv_selled;

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_money2)
    TextView tv_money2;
    @BindView(R.id.tv_money_past)
    TextView tv_money_past;
    @BindView(R.id.tv_money_past2)
    TextView tv_money_past2;

    @BindView(R.id.tv_hour)
    TextView tv_hour;
    @BindView(R.id.tv_min)
    TextView tv_min;
    @BindView(R.id.tv_sec)
    TextView tv_sec;

    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_point)
    TextView tv_point;
    @BindView(R.id.mrb)
    MaterialRatingBar mrb;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_sells)
    TextView tv_sells;


    @BindView(R.id.tv_food_comment2)
    TextView tv_food_comment2;

    @BindView(R.id.banner2)
    ConvenientBanner banner2;
    @BindView(R.id.tv_index)
    TextView tv_index;

    @BindView(R.id.ll_foot)
    LinearLayout ll_foot;


    @BindView(R.id.iv_des)
    WebView iv_des;

    @BindView(R.id.tv_add_to_chart)
    TextView tv_add_to_chart;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.iv_chart)
    ImageView iv_chart;
    @BindView(R.id.tv_xuangou_tag)
    TextView tv_xuangou_tag;

    @BindView(R.id.cl_miaosha)
    ConstraintLayout cl_miaosha;
    @BindView(R.id.cl_detail)
    ConstraintLayout cl_detail;

    @BindView(R.id.tv_money_pre)
    TextView tv_money_pre;
    //底部dialog
    @BindView(R.id.chart_bottom_dialog_view)
    ChartBottomDialogView chart_bottom_dialog_view;

    //只要有改变就刷新购物车数据
    private boolean isChanged = false;
//    private static final Handler handler  = new Handler(Looper.getMainLooper());

    CommentAdapter mAdapter;

    //banner 计数
    private int totalPage = 0;
    //倒计时
    private long endTimeSecond = 0;
    //购物车
    private HashMap<String, ChartBean> hashMap = new LinkedHashMap<>();


    public static void newInstance(Context context, int shopProductId, int storeId, int skuId) {
        Intent intent = new Intent(context, FoodActivity.class);
        intent.putExtra("shopProductId", shopProductId);
        intent.putExtra("storeId",storeId);
        intent.putExtra("skuId",skuId);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (isChanged) {
            EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_REFLUSH));
        }
        if (null != subscription && !subscription.isDisposed()) {
            subscription.dispose();
            subscription = null;
        }
    }


    String[] titles = new String[]{"商品", "评价", "详情"};
    int storeId;
    int skuId;
    int shopProductId;

    @OnClick({R.id.view1, R.id.view2, R.id.view3, R.id.tv_sure, R.id.tv_add_to_chart, R.id.iv_chart,R.id.iv_share,R.id.tv_to_store})
    public void onClick(View view) {
        scrollview.stopNestedScroll();

        int ll_toolbarHeight = ll_toolbar.getMeasuredHeight();

        int bannerHeight = banner2.getMeasuredHeight() - ll_toolbarHeight;
        LogUtil.d("bannerHeight" + bannerHeight + ",banner2.getMeasuredHeight()==" + banner2.getMeasuredHeight() + ",ll_toolbar.getMeasuredHeight()==" + ll_toolbar.getMeasuredHeight());
        int commentPoint = recyclerview.getTop() - ll_toolbarHeight - DisplayUtil.dip2px(FoodActivity.this, 50);//商品评价的位置
        int detailPoint = ll_foot.getTop() - ll_toolbarHeight;

        switch (view.getId()) {
            case R.id.iv_chart:
                List<ChartBean> list = new ArrayList<>();
                list.addAll(hashMap.values());
                chart_bottom_dialog_view.reflushAdapter(list);
                chart_bottom_dialog_view.showSelectedDialog();
                break;
            case R.id.view1:
                scrollview.smoothScrollTo(0, bannerHeight);
//                if (tabLayout.getSelectedTabPosition() != 0) {
//                    tabLayout.selectTab(tabLayout.getTabAt(0));
//                }
                break;
            case R.id.view2:
                scrollview.smoothScrollTo(0, commentPoint);
//                if (tabLayout.getSelectedTabPosition() != 1) {
//                    tabLayout.selectTab(tabLayout.getTabAt(1));
//                }
                break;
            case R.id.view3:
                scrollview.smoothScrollTo(0, detailPoint);
//                                scrollview.fullScroll(NestedScrollView.FOCUS_DOWN);
//                if (tabLayout.getSelectedTabPosition() != 2) {
//                    tabLayout.selectTab(tabLayout.getTabAt(2));
//                }
                break;
            case R.id.tv_sure:
                if (null != hashMap && hashMap.size()>0) {
                    ArrayList<Integer> intss = new ArrayList<>();
                    ArrayList<Integer> storeIds = new ArrayList<>();

                    for (ChartBean bean : hashMap.values()) {
                        intss.add(bean.getId());
                        if (!storeIds.contains(bean.getStoreId())) {
                            storeIds.add(bean.getStoreId());
                        }
                    }
                    startActivityForResult(OrderingActivity.newIntent(this, 2, intss, storeIds),REQUEST_CODE_ORDERING);
                }

                break;
            case R.id.tv_add_to_chart:
                if (isTagStyle()) {
                    showChooseTypeDialog();
                } else {
                    doAddToChart(mBeans.skuId + "", "",true,null,mBeans.flashSaleGoodsId+"");
                }

                break;
            case R.id.iv_share:
                toShoucang();
                break;
            case R.id.tv_to_store:
                Intent intent = new Intent(this, StoreActivity.class);
                intent.putExtra("id",mBeans.storeId+"");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ORDERING && resultCode == Constants.RESULT_CODE_ORDERING){
            hashMap.clear();
            setCartQuantity();
            chart_bottom_dialog_view.close();
        }
    }

    private void toShoucang() {
        AddFavoritesParams params = new AddFavoritesParams();
        params.setFavoritesType("GOOD");
        params.setBizId(shopProductId);
        Api.getClient(HttpRequest.baseUrl_member).addFavorites(Api.getRequestBody(params)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object o) {

                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);

                    }
                });
    }

    private void doAddToChart(String skuId, String tags,boolean animal,AddRemoveView iv_goods_add,String flashSaleGoodsId) {

        Map<String, String> map = new HashMap<>();
        map.put("num", "1");
        map.put("skuId", skuId);
        map.put("storeId", mBeans.storeId + "");
        map.put("attributes", tags);
        if(!TextUtils.isEmpty(flashSaleGoodsId)){
            map.put("flashSaleGoodsId", flashSaleGoodsId);
        }
        Api.getClient(HttpRequest.baseUrl_member).addToCart(Api.getRequestBody(map)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ChartBean>() {
                    @Override
                    public void onSuccess(ChartBean cartBean) {
                        if(null != iv_goods_add){
                            iv_goods_add.setNum(iv_goods_add.getNum() + 1);
                        }
                        if(animal){
                            AddRemoveView.doChartAnimal(FoodActivity.this, iv_add, cl_content, iv_chart);
                        }
                        isChanged = true;
                        //添加购物车id
                        hashMap.put(cartBean.getProductSkuId() + "", cartBean);
                        List<ChartBean> list = new ArrayList<>();
                        list.addAll(hashMap.values());


//                        if(null != chart_bottom_dialog_view && null != chart_bottom_dialog_view.selectedFoodAdapter) {
//                            chart_bottom_dialog_view.selectedFoodAdapter.getData();
//                            if (chart_bottom_dialog_view.selectedFoodAdapter.getData().size() != list.size()) {
//
//                            }
//                        }
                        chart_bottom_dialog_view.reflushAdapter(list);
                        chart_bottom_dialog_view.selectedFoodAdapter.notifyDataSetChanged();

                        setCartQuantity();
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }


    private void getChartDatas() {
        List<String> integers = new ArrayList<>();
        integers.add(mBeans.storeId + "");
        Api.getClient(HttpRequest.baseUrl_member).getCart(0L, integers)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<ChartBean>>() {
                    @Override
                    public void onSuccess(List<ChartBean> beans) {

                        for (ChartBean bean : beans) {
                            if (bean.getQuantity() > 0) {
                                hashMap.put(bean.getProductSkuId() + "", bean);
                            } else {
                                hashMap.remove(bean.getProductSkuId() + "");
                            }
                        }
                        setCartQuantity();
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_food2;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        skuId = getIntent().getIntExtra("skuId", 0);
        storeId = getIntent().getIntExtra("storeId", 0);
        shopProductId = getIntent().getIntExtra("shopProductId", 0);

        mrb.setIsIndicator(true);
        chart_bottom_dialog_view.initDatas(DisplayUtil.getWindowHeight(this));
        chart_bottom_dialog_view.setChartBottomDialogListener(new ChartBottomDialogView.ChartBottomDialogListener() {
            @Override
            public void onClear() {
                List<String> ints = new ArrayList<String>();
                ints.add(mBeans.storeId + "");
                Api.getClient(HttpRequest.baseUrl_member).clearCartInfo(ints).
                        subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>() {
                            @Override
                            public void onSuccess(String s) {

                                hashMap.clear();
                                setCartQuantity();
                                getChartDatas();
                                chart_bottom_dialog_view.close();
                            }

                            @Override
                            public void onFail(String fail) {
                                super.onFail(fail);
                            }
                        });
            }

            @Override
            public void onAdd(int num, AddRemoveView iv_goods_add, ChartBean foodBean) {
                doAddToChart(foodBean.getProductSkuId() + "", foodBean.attributes,false,iv_goods_add,foodBean.flashSaleGoodsId);
            }

            @Override
            public void onRemove(int num, AddRemoveView iv_goods_add, ChartBean foodBean) {
                if (num <= 0) {
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(foodBean.getId());
                    Api.getClient(HttpRequest.baseUrl_member).deleteCartItem(ids).
                            subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSubscriber<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    hashMap.remove(foodBean.getProductSkuId() + "");

                                    onChartItemRemove(num,iv_goods_add,foodBean);

                                }

                                @Override
                                public void onFail(String fail) {
                                    super.onFail(fail);

                                }
                            });
                } else {
                    Api.getClient(HttpRequest.baseUrl_member).updateQuantity(foodBean.getId() + "", num + "").
                            subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSubscriber<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    ChartBean old = hashMap.get(foodBean.getProductSkuId() + "");
                                    old.setQuantity(num);
                                    hashMap.put(foodBean.getProductSkuId() + "", old);

                                    onChartItemRemove(num,iv_goods_add,foodBean);

                                }

                                @Override
                                public void onFail(String fail) {
                                    super.onFail(fail);

                                }
                            });
                }

            }
        });

        View iv_back2 = findViewById(R.id.iv_back2);
        if (null != iv_back2) {
            iv_back2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        ViewGroup.LayoutParams layoutParams = ll_toolbar.getLayoutParams();
        layoutParams.height += ImmersionBar.getStatusBarHeight(this);
        ll_toolbar.invalidate();


        fl_title2.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0);

        View view1 = View.inflate(this, R.layout.item_food_tab, null);
        TextView tv_text1 = view1.findViewById(R.id.tv_text);
        tv_text1.setText("商品");
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        view1 = View.inflate(this, R.layout.item_food_tab, null);
        tv_text1 = view1.findViewById(R.id.tv_text);
        tv_text1.setText("评价");
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        view1 = View.inflate(this, R.layout.item_food_tab, null);
        tv_text1 = view1.findViewById(R.id.tv_text);
        tv_text1.setText("详情");
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv_text = tab.getCustomView().findViewById(R.id.tv_text);
                tv_text.setTextSize(16);
                tv_text.getPaint().setFakeBoldText(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv_text = tab.getCustomView().findViewById(R.id.tv_text);
                tv_text.setTextSize(15);
                tv_text.getPaint().setFakeBoldText(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//
//                int scrollRangle = appBarLayout.getTotalScrollRange();
//
//                LogUtil.d("verticalOffset=="+verticalOffset+"--scrollRangle=="+scrollRangle);
//
//                if (verticalOffset == 0) {
//                    background.setAlpha(0);
//                } else if(Math.abs(verticalOffset) >= scrollRangle){
//                    background.setAlpha(1);
//                }else{
//                    //保留一位小数
//                    float alpha =( Math.abs(verticalOffset)) * 1.0f / scrollRangle;
//                    background.setAlpha(alpha);
//                }
//
//
//            }
//        });

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);


//        View head = View.inflate(this, R.layout.head_activity_food_comment, null);
//        View head2 = View.inflate(this, R.layout.head_activity_food_comment2, null);
//        initBanner(head);
//        initBanner2(head2);


//        mAdapter.addHeaderView(head,0);
//        mAdapter.addHeaderView(head2,1);

//        View bottom = View.inflate(this, R.layout.bottom_food_list, null);
//        mAdapter.addFooterView(bottom);

        ViewGroup.LayoutParams layoutParams1 = banner2.getLayoutParams();
        layoutParams1.height = layoutParams1.width;
        banner2.setLayoutParams(layoutParams1);

        initScroll();
        getDatas();
        tv_money_past.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    private void onChartItemRemove(int num,AddRemoveView iv_goods_add,ChartBean foodBean) {

        iv_goods_add.setNum(iv_goods_add.getNum() - 1);
        if (num <= 0 && null != foodBean) {
            hashMap.remove(foodBean.getProductSkuId());
            int index = chart_bottom_dialog_view.selectedFoodAdapter.getData().indexOf(foodBean);
            chart_bottom_dialog_view.selectedFoodAdapter.getData().remove(foodBean);
            chart_bottom_dialog_view.selectedFoodAdapter.notifyItemRemoved(index);

            if (chart_bottom_dialog_view.selectedFoodAdapter.getData().size() <= 0) {
                chart_bottom_dialog_view.close();
            }
        }
        setCartQuantity();
    }

    FoodDetailBean mBeans;

    private void getDatas() {
        Api.getClient(HttpRequest.baseUrl_goods).productDetail(shopProductId)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<FoodDetailBean>() {
                    @Override
                    public void onSuccess(FoodDetailBean beans) {
                        mBeans = beans;
                        storeId = beans.storeId;
                        setFoodDetail(beans);

                        initBanner();
                        if (!TextUtils.isEmpty(beans.detailDesc)) {
                            ll_foot.setVisibility(View.VISIBLE);
                            setWebView(beans.detailDesc);
                        } else {
                            ll_foot.setVisibility(View.GONE);
                        }
                        getStoreDetail();

//                        ArrayList<ChartBean> objects = new ArrayList<>();
//                        ChartBean chartBean = new ChartBean();
//                        chartBean.setProductName(beans.skuName);
//                        chartBean.setPrice(BigDecimal.valueOf(beans.price));
//                        chartBean.setQuantity(beans.cartQuantity);
////                        chartBean.setSp1(beans.sp1);
////                        chartBean.setSp2(beans.sp2);
////                        chartBean.setSp3(beans.sp3);
//                        chartBean.setStoreId(beans.storeId);
//                        try {
//                            chartBean.setId(beans.cartItemId == null ? 0 : beans.cartItemId);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        chartBean.setProductId(beans.productId);
//                        chartBean.setProductSkuId(beans.skuId);
//                        chartBean.flashSaleGoodsId = beans.flashSaleGoodsId+"";
//                        objects.add(chartBean);
//                        chart_bottom_dialog_view.reflushAdapter(objects);

                        getChartDatas();

                        getComments();
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    private void reflushProductDetail(){
        Api.getClient(HttpRequest.baseUrl_goods).productDetail(shopProductId)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<FoodDetailBean>() {
                    @Override
                    public void onSuccess(FoodDetailBean beans) {
                        mBeans = beans;
                        setFoodDetail(beans);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }
    private Disposable subscription;

    private void setFoodDetail(FoodDetailBean beans) {
        if(beans.flashSaleStatus == 1){
            showMiaoSha(true);

            if(beans.endTimeSecond>0){
                endTimeSecond = beans.endTimeSecond;
                if(null != subscription && !subscription.isDisposed()){
                    subscription.dispose();
                }
                subscription = Observable.interval(0, 1, TimeUnit.SECONDS).
                        take(endTimeSecond+1).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Throwable {
                        Log.d(TAG, "setFoodDetail: "+aLong);

                        startCutDown(endTimeSecond-aLong);
                    }

                });
            }
        }else{
            showMiaoSha(false);
        }

        tv_money_past.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        tv_money_past.setText("¥" + mBeans.originalPrice + "");
        tv_money_past2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        tv_money_past2.setText("¥" + mBeans.originalPrice + "");

        tv_money.setText(mBeans.price + "");
        tv_money2.setText(mBeans.price + "");
        tv_food_title.setText(mBeans.subTitle);
        tv_selled.setText("已售" + mBeans.sale);

    }

    private void showMiaoSha(boolean b) {
        if(b){
            cl_miaosha.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cl_detail.getLayoutParams();
            layoutParams.topMargin = DisplayUtil.dip2px(this,-40);
            cl_detail.setLayoutParams(layoutParams);
            tv_money_pre.setVisibility(View.GONE);
            tv_money.setVisibility(View.GONE);
            tv_money_past.setVisibility(View.GONE);
        }else{
            cl_miaosha.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cl_detail.getLayoutParams();
            layoutParams.topMargin = DisplayUtil.dip2px(this,0);
            cl_detail.setLayoutParams(layoutParams);
            tv_money_pre.setVisibility(View.VISIBLE);
            tv_money.setVisibility(View.VISIBLE);
            tv_money_past.setVisibility(View.VISIBLE);
        }
    }

    private void startCutDown(long time) {
        int hour = (int) Math.floor(time  / 3600);
        int min = (int) Math.floor((time  % 3600) / 60);
        int sec = (int) ((time  % 3600) % 60);
        String h = hour < 10 ? ("0" + hour) : hour+"";
        String m = min < 10 ? ("0" + min) : min+"";
        String s = sec < 10 ? ("0" + sec) : sec+"";
        tv_hour.setText(h);
        tv_min.setText(m);
        tv_sec.setText(s);
        Log.d(TAG, "setFoodDetail: "+time+"/"+hour+"/"+min+"/"+sec);
//        endTimeSecond--;
        if(time==0){
            reflushProductDetail();
        }
    }
//    private final Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            if (isFinishing() || isDestroyed()){
//                return;
//            }
//            startCutDown();
//        }
//    };


    private void setCartQuantity() {
        if (hashMap.size() > 0) {
            int num = 0;
            for(ChartBean bean :hashMap.values()){
                num += bean.getQuantity();
            }
            if(num > 0){
                tv_xuangou_tag.setText(num + "");
                tv_xuangou_tag.setVisibility(View.VISIBLE);
            }else{
                tv_xuangou_tag.setVisibility(View.GONE);
            }
        } else {
            tv_xuangou_tag.setVisibility(View.GONE);
        }
    }

    private void setWebView(String url) {
        WebSettings settings = iv_des.getSettings();
        if (getIntent().getBooleanExtra("scale", false)) {
            settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        }
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);// 支持js功能
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);//H5使用了在浏览器本地存储功能就必须加这句
        settings.setUseWideViewPort(true);
        iv_des.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
    }

    /**
     * 刷新
     */
    private void getComments() {
        Api.getClient(HttpRequest.baseUrl_eva).queryListBySkuId(shopProductId + "", 1, LoadmoreUtils.PAGE_SIZE)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CommentWrapBean>() {
                    @Override
                    public void onSuccess(CommentWrapBean beans) {
                        mAdapter.setNewInstance(beans.records.size() > 2 ? beans.records.subList(0, 1) : beans.records);
                        if(beans.records==null || beans.records.size()==0){
                            tv_food_comment2.setText("暂无评价");
                        }else{
                            tv_food_comment2.setText(beans.records.size()+"条评价");
                            tv_food_comment2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(FoodActivity.this, CommentActivity.class);
                                    intent.putExtra("storeId", storeId);
                                    intent.putExtra("skuId", skuId);
                                    intent.putExtra("shopProductId", shopProductId);
                                    startActivity(intent);
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

    private void initScroll() {

        recyclerview.post(new Runnable() {
            @Override
            public void run() {
                int ll_toolbarHeight = ll_toolbar.getMeasuredHeight();

                int bannerHeight = banner2.getMeasuredHeight() - ll_toolbarHeight;
                LogUtil.d("bannerHeight" + bannerHeight + ",banner2.getMeasuredHeight()==" + banner2.getMeasuredHeight() + ",ll_toolbar.getMeasuredHeight()==" + ll_toolbar.getMeasuredHeight());
                int commentPoint = recyclerview.getTop() - ll_toolbarHeight - DisplayUtil.dip2px(FoodActivity.this, 50);//商品评价的位置
                int detailPoint = ll_foot.getTop() - ll_toolbarHeight;

                fl_title2.setVisibility(View.GONE);
                ll_toolbar.setVisibility(View.VISIBLE);


                scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int totalScrollY, int oldScrollX, int oldScrollY) {

                        if (totalScrollY < commentPoint) {
                            if (tabLayout.getSelectedTabPosition() != 0) {
                                tabLayout.selectTab(tabLayout.getTabAt(0));
                            }

                        } else if (totalScrollY >= detailPoint) {
                            if (tabLayout.getSelectedTabPosition() != 2) {
                                tabLayout.selectTab(tabLayout.getTabAt(2));
                            }
                        } else if (totalScrollY < detailPoint) {
                            if (tabLayout.getSelectedTabPosition() != 1) {
                                tabLayout.selectTab(tabLayout.getTabAt(1));
                            }
                        }


                        LogUtil.d(",scrollY==" + totalScrollY + ",oldScrollY==" + oldScrollY);

                        if (totalScrollY > oldScrollY) {
                            LogUtil.d("=====", "下滑");
                        }
                        if (totalScrollY < oldScrollY) {
                            LogUtil.d("=====", "上滑");
                        }

                        if (totalScrollY == 0) {
                            LogUtil.d("=====", "滑倒顶部");
                        }

                        if (totalScrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                            LogUtil.d("=====", "滑倒底部");
                        }

                        float lineValue = bannerHeight * 0.7f;

                        if (totalScrollY <= lineValue) {
                            ll_toolbar.setAlpha(1);
                            fl_title2.setAlpha(0);

                            fl_title2.setVisibility(View.GONE);
                            ll_toolbar.setVisibility(View.VISIBLE);
                        } else if (totalScrollY <= bannerHeight) {
                            ll_toolbar.setAlpha(1 - (float) (totalScrollY - lineValue) / (bannerHeight - lineValue));
                            fl_title2.setAlpha((float) (totalScrollY - lineValue) / (bannerHeight - lineValue));

                            fl_title2.setVisibility(View.VISIBLE);
                            ll_toolbar.setVisibility(View.VISIBLE);
                        } else {
                            ll_toolbar.setAlpha(0);
                            fl_title2.setAlpha(1);

                            fl_title2.setVisibility(View.VISIBLE);
                            ll_toolbar.setVisibility(View.GONE);
                        }


                    }
                });


            }
        });

    }


    private void getStoreDetail() {
        Api.getClient(HttpRequest.baseUrl_shop).shop_getDetail(storeId + "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<StoreDetailBean>() {
                    @Override
                    public void onSuccess(StoreDetailBean data) {

                        if (null != data) {
                            mrb.setRating(data.star);
                            tv_point.setText(data.star+"分");
                            tv_title.setText(data.name + "");
                            if (!TextUtils.isEmpty(data.distanceStr)) {
                                tv_distance.setText(data.distanceStr);
                            }
//                            if (!TextUtils.isEmpty(data.getDuration())) {
//                                tv_time.setText("大约" + data.getDuration() + "分钟");
//                            }
                            if (!TextUtils.isEmpty(data.saleStr)) {
                                tv_sells.setText("" + data.saleStr);
                            }

                            Picasso.with(FoodActivity.this)
                                    .load(data.logoUrl)
                                    .fit()
                                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(FoodActivity.this, 5), 0, PicassoRoundTransform.CornerType.ALL))
                                    .into(iv_icon);
                        }

//                        List<StoreBean.StoreTag> list = new ArrayList<>();
//                        list.add(new StoreBean.StoreTag(mBeans.sp1, 0));
//                        list.add(new StoreBean.StoreTag(mBeans.sp2, 0));
//                        list.add(new StoreBean.StoreTag(mBeans.sp3, 2));
//                        list.add(new StoreBean.StoreTag(mBeans.sp4, 2));
//
//                        CommonUtil.initTAGViews(ll_mark, list);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }


    private void initBanner() {
//        ConvenientBanner banner2 = head.findViewById(R.id.banner2);
//        TextView tv_index = head.findViewById(R.id.tv_index);

//        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
//        params2.width = DisplayUtil.getWindowWidth(getActivity());
//        params2.height = (int) ((params2.width - DisplayUtil.dip2px(getContext(), 30)) * Constants.RATE_HOME) + DisplayUtil.dip2px(getContext(), 35);
//        mBanner.setLayoutParams(params2);
        String[] pics = mBeans.bizPicture;
        List<String> adsList = Arrays.asList(pics);

        totalPage = adsList.size();
        tv_index.setText(1 + "/" + totalPage);
        banner2.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new StringBannerImageHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_food_image;
            }


        }, adsList);
        banner2.setPointViewVisible(false);
        banner2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        banner2.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onPageSelected(int index) {
                tv_index.setText((index + 1) + "/" + totalPage);
            }
        });

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
        TextView iv_goods_name = bottomSheetDialog.findViewById(R.id.iv_goods_name);
        iv_goods_name.setText(mBeans.subTitle);
        TextView iv_goods_detail = bottomSheetDialog.findViewById(R.id.iv_goods_detail);
        iv_goods_detail.setText(mBeans.description);
        TextView iv_goods_comment = bottomSheetDialog.findViewById(R.id.iv_goods_comment);
        iv_goods_comment.setText("剩余" + mBeans.stock + "份  月售" + mBeans.sale);
        TextView iv_goods_price = bottomSheetDialog.findViewById(R.id.iv_goods_price);
        iv_goods_price.setText("¥" + mBeans.skuList.get(0).price);
        TextView iv_goods_price_past = bottomSheetDialog.findViewById(R.id.iv_goods_price_past);
        if ( mBeans.skuList.get(0).orginPrice >0) {
            iv_goods_price_past.setVisibility(View.VISIBLE);
        } else {
            iv_goods_price_past.setVisibility(View.GONE);
        }
        iv_goods_price_past.setText("¥" + mBeans.skuList.get(0).orginPrice);


        ImageView iv_goods_img = bottomSheetDialog.findViewById(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(mBeans.pic)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(), 5), 0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_goods_img);
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        FoodChooseTypeAdapter foodChooseTypeAdapter = new FoodChooseTypeAdapter();
        recyclerview.setAdapter(foodChooseTypeAdapter);

        List<FoodTypeBean> datas = new ArrayList<>();

        String title1 = "规格";
        //添加规格
        if (mBeans.skuList.size() > 1) {
            List<String> dataSource = new ArrayList<>();
            for (StoreFoodItem2Bean.SkuListBean name : mBeans.skuList) {
                dataSource.add(name.skuName);
            }
            FoodTypeBean foodTypeBean = new FoodTypeBean();
            foodTypeBean.title = title1;
            foodTypeBean.lists = dataSource;
            datas.add(foodTypeBean);
        }

        //添加标签
        for (StoreFoodItem2Bean.AttributeListBean bean1 : mBeans.attributeList) {
            List<String> tags = new ArrayList<>();
            tags.addAll(Arrays.asList(bean1.inputList.split(",")));
            FoodTypeBean tagsBean = new FoodTypeBean();
            tagsBean.title = bean1.name;
            tagsBean.lists = tags;
            datas.add(tagsBean);
        }

        foodChooseTypeAdapter.setNewInstance(datas);
        foodChooseTypeAdapter.setOnItemCheckedListener(new FoodChooseTypeAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(int position ,int index, String content) {
                setSelectedParams(foodChooseTypeAdapter.getData(), iv_goods_detail);
                if(position == 0) {
                    iv_goods_price.setText("¥" + mBeans.skuList.get(index).price);
                    if (mBeans.skuList.get(index).orginPrice > 0) {
                        iv_goods_price_past.setVisibility(View.VISIBLE);
                    } else {
                        iv_goods_price_past.setVisibility(View.GONE);
                    }
                    iv_goods_price_past.setText("¥" + mBeans.skuList.get(index).orginPrice);
                }
            }
        });
        setSelectedParams(foodChooseTypeAdapter.getData(),iv_goods_detail);

        bottomSheetDialog.findViewById(R.id.iv_goods_add).setVisibility(View.GONE);
//        bottomSheetDialog.findViewById(R.id.iv_goods_comment).setVisibility(View.GONE);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bottomSheetDialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                List<FoodTypeBean> data = foodChooseTypeAdapter.getData();
                //有规格的情况，设置skuid
                int skuId = 0;
                int flashSaleGoodsId = 0;
                if (title1.equals(data.get(0).title)) {
                    skuId = mBeans.skuList.get(data.get(0).selected).skuId;
                    flashSaleGoodsId = mBeans.skuList.get(data.get(0).selected).flashSaleGoodsId;
                }
                StringBuilder tags = new StringBuilder();
                for (FoodTypeBean bean : data) {
                    tags.append(bean.lists.get(bean.selected));
                    tags.append(",");
                }

                doAddToChart(skuId + "", tags.toString(),true,null,flashSaleGoodsId+"");

            }
        });

        bottomSheetDialog.show();
    }
    private void setSelectedParams(List<FoodTypeBean> data, TextView iv_goods_detail) {
        StringBuilder builder = new StringBuilder();
        for(FoodTypeBean foodTypeBean :data){
            int indexOf = data.indexOf(foodTypeBean);
            if(0 == indexOf){
                builder.append(foodTypeBean.lists.get(foodTypeBean.selected));
            }else{
                builder.append("/"+foodTypeBean.lists.get(foodTypeBean.selected));
            }
        }
        iv_goods_detail.setText("已选：+"+builder.toString());
    }
    private boolean isTagStyle() {
        return (mBeans.attributeList != null && mBeans.attributeList.size() > 0)
                || (mBeans.skuList != null && mBeans.skuList.size() > 1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        if (event.type == CloseEvent.TYPE_PAY) {
            finish();
        }
    }
}
