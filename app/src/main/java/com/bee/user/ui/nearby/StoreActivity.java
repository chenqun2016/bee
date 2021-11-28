package com.bee.user.ui.nearby;

import android.content.Intent;
import android.text.TextUtils;
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
import java.util.Arrays;
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

                showSelectedDialog(null, null);
                break;

            case R.id.tv_dingwei:

//                startActivity(new Intent(this, DingWeiActivity.class));
                break;

            case R.id.iv_search:
            case R.id.tv_search_1:
                startActivity(new Intent(this, SearchFoodActivity.class));
                break;
        }

    }

    private void showSelectedDialog(AddChartBean addChartBean, OnAddChartListener listener) {
        List<ChartBean> list = new ArrayList<>();
        for (AddChartBean bean : hashMap.values()) {
            list.add(bean.data);
        }
        chart_bottom_dialog_view.reflushAdapter(list);
        chart_bottom_dialog_view.showSelectedDialog();

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
        chart_bottom_dialog_view.setChartBottomDialogListener(new ChartBottomDialogView.ChartBottomDialogListener() {
            @Override
            public void onClear() {
                List<String> ints = new ArrayList<String>();
                ints.add(id + "");
                Api.getClient(HttpRequest.baseUrl_member).clearCartInfo(ints).
                        subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>() {
                            @Override
                            public void onSuccess(String s) {
                                StoreFragment fragment = (StoreFragment) mFragments[0];
                                for(AddChartBean bean :hashMap.values()){
                                    fragment.notifyReflush(bean.indexForList,0);
                                }
                                hashMap.clear();
                                resetView();
                                getChartDatas();
                            }

                            @Override
                            public void onFail(String fail) {
                                super.onFail(fail);
                            }
                        });
            }

            @Override
            public void onAdd(int num, AddRemoveView iv_goods_add,ChartBean foodBean) {
                AddChartBean addChartBean = hashMap.get(foodBean.getProductSkuId() + "");
                addChartBean.isTagStyle = false;
                addChartBean.num = num;
                AddChartEvent  addChartEvent = new AddChartEvent(addChartBean, 1);
                doAddChartEvent(addChartEvent, new OnAddChartListener() {
                    @Override
                    public void onSuccess() {
                        iv_goods_add.setNum(iv_goods_add.getNum() + 1);
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }

            @Override
            public void onRemove(int num, AddRemoveView iv_goods_add,ChartBean foodBean) {
                AddChartBean addChartBean = hashMap.get(foodBean.getProductSkuId() + "");
                addChartBean.isTagStyle = false;
                addChartBean.num = num;
                AddChartEvent  addChartEvent = new AddChartEvent(addChartBean, 0);
                doAddChartEvent(addChartEvent, new OnAddChartListener() {
                    @Override
                    public void onSuccess() {
                        iv_goods_add.setNum(iv_goods_add.getNum() -1);
                        if (iv_goods_add.getNum() <= 0 && null != foodBean) {
                            int indexOf = chart_bottom_dialog_view.selectedFoodAdapter.getData().indexOf(foodBean);
                            chart_bottom_dialog_view.selectedFoodAdapter.getData().remove(foodBean);
                            chart_bottom_dialog_view.selectedFoodAdapter.notifyItemRemoved(indexOf);
                            hashMap.remove(foodBean.getProductSkuId());
                            if(chart_bottom_dialog_view.selectedFoodAdapter.getData() == null || chart_bottom_dialog_view.selectedFoodAdapter.getData().size()<=0){
                                chart_bottom_dialog_view.close();
                            }
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
        });

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
        getChartDatas();
    }

    private void getChartDatas() {
        //TODO
        List<String> integers = new ArrayList<>();
        integers.add(id);
        //TODO
        Api.getClient(HttpRequest.baseUrl_member).getCart(0L, integers)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<ChartBean>>() {
                    @Override
                    public void onSuccess(List<ChartBean> beans) {

                        for (ChartBean bean : beans) {
                            if (bean.getQuantity() > 0) {
                                AddChartBean ChartBean = new AddChartBean(false, bean.getQuantity(), bean.getProductSkuId(), bean.getStoreId(), bean.getPrice(), bean.getId(), bean, null);
                                hashMap.put(bean.getProductSkuId() + "", ChartBean);
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
                                        if (null != data) {
                                            for (StoreFoodItem2Bean bean1 : data) {
                                                bean1.name = bean.getName();
                                            }
                                        }
                                        return listBaseResult;
                                    }
                                }));

                            }
                            Observable.concat(observables)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseSubscriber<List<StoreFoodItem2Bean>>() {

                                        @Override
                                        public void onComplete() {
                                            super.onComplete();
                                            StoreFragment fragment = (StoreFragment) mFragments[0];
                                            fragment.setFoodDatas(mDatas,hashMap);
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

    private void setViews(StoreDetailBean data) {

//        Picasso.with(this)
//                .load(storeDetailBean.getLogoUrl())
//                .fit()
//                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(this, 5), 0, PicassoRoundTransform.CornerType.ALL))
//                .into(iv_icon);
//
//        tv_title.setText(storeDetailBean.getName());
//        if (!TextUtils.isEmpty(storeDetailBean.getDistance())) {
//            tv_distance.setText(storeDetailBean.getDistance() + "公里");
//        }
//        if (!TextUtils.isEmpty(storeDetailBean.getDuration())) {
//            tv_time.setText("大约" + storeDetailBean.getDuration() + "分钟");
//        }
//        if (!TextUtils.isEmpty(storeDetailBean.getMonthSalesCount())) {
//            tv_sells.setText("月销" + storeDetailBean.getMonthSalesCount());
//        }

        Picasso.with(this)
                .load(data.appBackgroudUrl)
                .fit()
                .error(R.drawable.bg_chengse)
                .into(tv_store_bg);
        CommonUtil.initTAGViews(ll_mark);

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
        Picasso.with(this)
                .load(data.logoUrl)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(this, 5), 0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_icon);
    }


    private void showChooseTypeDialog(AddChartEvent event, OnAddChartListener listener) {
        AddChartBean bean = event.addChartBean;
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
        iv_goods_name.setText(bean.data2.subTitle);
        TextView iv_goods_detail = bottomSheetDialog.findViewById(R.id.iv_goods_detail);
//        iv_goods_detail.setText(bean.data2.description);

//        TextView iv_goods_comment = bottomSheetDialog.findViewById(R.id.iv_goods_comment);
//        iv_goods_comment.setText("剩余" + bean.data2.stock + "份  月售" + bean.data2.sale);
        TextView iv_goods_price = bottomSheetDialog.findViewById(R.id.iv_goods_price);
        iv_goods_price.setText("¥" + bean.data2.skuList.get(0).price);
        TextView iv_goods_price_past = bottomSheetDialog.findViewById(R.id.iv_goods_price_past);
        if ( bean.data2.skuList.get(0).orginPrice >0) {
            iv_goods_price_past.setVisibility(View.VISIBLE);
        } else {
            iv_goods_price_past.setVisibility(View.GONE);
        }
        iv_goods_price_past.setText("¥" + bean.data2.skuList.get(0).orginPrice);

        ImageView iv_goods_img = bottomSheetDialog.findViewById(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(bean.data2.pic)
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
        if (bean.data2.skuList.size() > 1) {
            List<String> dataSource = new ArrayList<>();
            for (StoreFoodItem2Bean.SkuListBean name : bean.data2.skuList) {
                dataSource.add(name.skuName);
            }
            FoodTypeBean foodTypeBean = new FoodTypeBean();
            foodTypeBean.title = title1;
            foodTypeBean.lists = dataSource;
            datas.add(foodTypeBean);
        }

        //添加标签
        for (StoreFoodItem2Bean.AttributeListBean bean1 : bean.data2.attributeList) {
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
            public void onItemChecked(int index, String content) {
                setSelectedParams(foodChooseTypeAdapter.getData(),iv_goods_detail);
                iv_goods_price.setText("¥" + bean.data2.skuList.get(index).price);
                if (bean.data2.skuList.get(index).orginPrice>0) {
                    iv_goods_price_past.setVisibility(View.VISIBLE);
                } else {
                    iv_goods_price_past.setVisibility(View.GONE);
                }
                iv_goods_price_past.setText("¥" + bean.data2.skuList.get(index).orginPrice);
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
                if (title1.equals(data.get(0).title)) {
                    event.addChartBean.skuId = event.addChartBean.data2.skuList.get(data.get(0).selected).skuId;

                }
                event.type = 1;
//                onAddChartEvent(event);
                StringBuilder tags = new StringBuilder();
                for (FoodTypeBean bean : data) {
                    tags.append(bean.lists.get(bean.selected));
                    tags.append(",");
                }
                event.addChartBean.tags = tags.toString();
                doAddChartEvent(event, listener);
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
                fragment = new CommentFragment(id, 0);
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

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        if (event.type == CloseEvent.TYPE_PAY) {
            finish();
        }
    }


    public interface OnAddChartListener {
        void onSuccess();

        void onFail();
    }

    public void doAddChartEvent(AddChartEvent event, OnAddChartListener listener) {
        if (1 == event.type) {

            AddChartBean addChartBean = event.addChartBean;
            Map<String, String> map = new HashMap<>();
            map.put("num", "1");
            map.put("skuId", addChartBean.skuId + "");
            map.put("storeId", addChartBean.storeId + "");
            if (!TextUtils.isEmpty(event.addChartBean.tags)) {
                map.put("attributes", event.addChartBean.tags);
            }
            Api.getClient(HttpRequest.baseUrl_member).addToCart(Api.getRequestBody(map)).
                    subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ChartBean>() {
                        @Override
                        public void onSuccess(ChartBean userBean) {
                            addChartBean.data = userBean;
                            isChanged = true;
                            //添加购物车id
                            addChartBean.cartItemId = userBean.getId();
                            hashMap.put(addChartBean.skuId + "", addChartBean);
                            resetView();
                            if (null != listener) {
                                listener.onSuccess();
                            }
                            StoreFragment fragment = (StoreFragment) mFragments[0];
                            fragment.notifyReflush(addChartBean.indexForList,1);
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                            if (null != listener) {
                                listener.onFail();
                            }
                        }
                    });
        } else if (0 == event.type) {
            AddChartBean addChartBean = event.addChartBean;
            if (addChartBean.isTagStyle) {
                showSelectedDialog(addChartBean, listener);
            } else {
                doRemoveChart(addChartBean, listener);
            }


        } else if (2 == event.type) {
            showChooseTypeDialog(event, listener);
        }
    }

    private void doRemoveChart(AddChartBean addChartBean, OnAddChartListener listener) {
        if (addChartBean.num <= 0) {
            ArrayList<Integer> ids = new ArrayList<>();
            ids.add(addChartBean.cartItemId);
            Api.getClient(HttpRequest.baseUrl_member).deleteCartItem(ids).
                    subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<String>() {
                        @Override
                        public void onSuccess(String s) {
                            hashMap.remove(addChartBean.skuId + "");
                            resetView();
                            if (null != listener) {
                                listener.onSuccess();
                            }
                            StoreFragment fragment = (StoreFragment) mFragments[0];
                            fragment.notifyReflush(addChartBean.indexForList,0);
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                            if (null != listener) {
                                listener.onFail();
                            }
                        }
                    });
        } else {
            Api.getClient(HttpRequest.baseUrl_member).updateQuantity(addChartBean.cartItemId + "", addChartBean.num + "").
                    subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<String>() {
                        @Override
                        public void onSuccess(String s) {
                            if (addChartBean.num > 0) {
                                AddChartBean old = hashMap.get(addChartBean.skuId + "");
                                old.num = addChartBean.num;
                                old.skuId = addChartBean.skuId;
                                old.storeId = addChartBean.storeId;
                                old.cartItemId = addChartBean.cartItemId;
                                old.money = addChartBean.money;
                                old.data.setQuantity(old.num);
                                old.data2 = addChartBean.data2;
                                hashMap.put(addChartBean.skuId + "", old);
                            } else {
                                hashMap.remove(addChartBean.skuId + "");
                            }
                            resetView();
                            if (null != listener) {
                                listener.onSuccess();
                            }
                            StoreFragment fragment = (StoreFragment) mFragments[0];
                            fragment.notifyReflush(addChartBean.indexForList,-1);
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                            if (null != listener) {
                                listener.onFail();
                            }
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
            tv_xuangou_tag.setVisibility(View.VISIBLE);
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
            tv_xuangou_tag.setVisibility(View.GONE);
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
        iv_goods_add.initAnimalView(fl_content, image_xuangou);
    }
}
