package com.bee.user.ui.order;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.bean.OrderDetailBean;
import com.bee.user.bean.OrderGridviewItemBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.OrderDetailAdapter;
import com.bee.user.ui.adapter.OrderFoodAdapter;
import com.bee.user.ui.adapter.OrderGridviewItemAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.MyGridView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  17：08
 * 描述：
 */
public class OrderDetailActivity extends BaseActivity  implements AMap.OnMapLoadedListener, AMap.OnInfoWindowClickListener, View.OnClickListener {
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    @BindView(R.id.appbar)
    AppBarLayout appbar;


    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;

//    @BindView(R.id.iv_back)
//    ImageButton iv_back;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.background)
    View background;

    @BindView(R.id.tv_text_title)
    TextView tv_title;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    View line4;
    TextView tv_people;
    LinearLayout ll_bottom;

    TextView tv_people_des;
    TextView tv_time_des;
    TextView tv_address_des;
    TextView tv_type_des;
    TextView tv_ordernum_des;
    TextView tv_pay_type_des;
    TextView tv_pay_time_des;
    TextView tv_beizhu_des;

    private OrderDetailAdapter orderingAdapter;

    private long time = 10;
    private Disposable subscription;
    private String type;
    private int id;
    private OrderDetailBean orderDetailBean;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }


    @Override
    @OnClick({R.id.tv_text_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_text_title:
                switch (type) {
//                    case Constants.TYPE_PAY_WAITE://等待支付
//                        break;
//                    case Constants.TYPE_READY://商家正在备货
//                        break;
//                    case Constants.TYPE_PEISONG://商品配送中
//                        break;
//                    case Constants.TYPE_COMPLETE://订单已送达
//                        break;
//                    case Constants.TYPE_CANCELED://订单已取消
//                        break;
//                    case Constants.TYPE_TUIKUAN://退款中
//                        break;
                    default:
                        showTraceDialog();
                        break;
                }

                break;
            case R.id.tv_copy:
                CommonUtil.copyContentToClipboard(tv_ordernum_des.getText().toString(),this);

                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail_map;
    }



    private void getOrderDetail() {
        Api.getClient(HttpRequest.baseUrl_order).orderDetail(id).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderDetailBean>() {
                    @Override
                    public void onSuccess(OrderDetailBean orderDetailBean) {
                        OrderDetailActivity.this.orderDetailBean = orderDetailBean;
                        orderingAdapter.setNewInstance(new ArrayList<>());

                        View head2 = View.inflate(OrderDetailActivity.this, R.layout.item_ordering, null);
                        initHead2View(head2, orderDetailBean);
                        initDatas(orderDetailBean);
                        orderingAdapter.addHeaderView(head2, 1);
                    }
                });
    }

    private void initDatas(OrderDetailBean orderDetailBean) {

         tv_people_des.setText("");
         tv_time_des.setText(orderDetailBean.deliveryTime+"");
         tv_address_des.setText(orderDetailBean.receiverDetailAddress+"\n"+orderDetailBean.createName+orderDetailBean.receiverPhone);
         tv_type_des.setText(orderDetailBean.billType+"");
         tv_ordernum_des.setText(orderDetailBean.orderSn+"");
         tv_pay_type_des.setText(orderDetailBean.payType==1?"米粒支付":"");
        tv_pay_time_des.setText(CommonUtil.getNomalTime(orderDetailBean.createTime));
         tv_beizhu_des.setText(orderDetailBean.note+"");
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        type = intent.getStringExtra("type");
        int statusBarHeight = ImmersionBar.getStatusBarHeight(this);
        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height += statusBarHeight;
        toolbar.setLayoutParams(layoutParams);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset==" + verticalOffset + "--scrollRangle==" + scrollRangle);

                if (verticalOffset == 0) {//展开
                    background.setAlpha(0);
                    tv_title.setAlpha(0f);
                } else if (Math.abs(verticalOffset) >= scrollRangle) {
                    background.setAlpha(1);
                    tv_title.setAlpha(1f);
                }else {
                    //保留一位小数
                    float alpha = (Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    background.setAlpha(alpha);
                    tv_title.setAlpha(alpha);
                }


            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        orderingAdapter = new OrderDetailAdapter();
        recyclerView.setAdapter(orderingAdapter);


        View foot = View.inflate(this, R.layout.foot_orderdetail, null);
        initFootView(foot);

        View head;

        switch (type) {
            case Constants.TYPE_ORDER_DPS://订单已送达
                tv_title.setText("订单已送达");
                head = View.inflate(this, R.layout.head_orderdetail_complete, null);
                initHeadView(head);
                break;

            case Constants.TYPE_ORDER_WP://等待支付
                tv_title.setText("等待支付，剩余10");
                head = View.inflate(this, R.layout.head_orderdetail_waite, null);
                initHeadViewWaite(head);

                countDown();
                break;
            case Constants.TYPE_ORDER_OMJ://商家正在备货
                tv_title.setText("商家正在备货");
                head = View.inflate(this, R.layout.head_orderdetail_beihuo, null);
                initHeadViewbeihuo(head);

                break;
            case Constants.TYPE_ORDER_SPS://商品配送中
                tv_title.setText("商品配送中");
                head = View.inflate(this, R.layout.head_orderdetail_beihuo, null);
                initHeadViewbeihuo(head);

                break;

            case Constants.TYPE_ORDER_OC://订单已取消
                tv_title.setText("订单已取消");
                head = View.inflate(this, R.layout.head_orderdetail_quxiao, null);
                initHeadViewQuxiao(head);

                line4.setVisibility(View.GONE);
                tv_people.setVisibility(View.GONE);
                tv_people_des.setVisibility(View.GONE);
                ll_bottom.setVisibility(View.VISIBLE);
                break;
            case Constants.TYPE_ORDER_PJ://退款
                head = View.inflate(this, R.layout.head_orderdetail_tuikuan, null);
                initHeadViewtuikuan(head);
                tv_title.setText("订单已送达");
                break;
            default:
                head = View.inflate(this, R.layout.head_orderdetail_complete, null);
                initHeadView(head);
                break;
        }

        CommonUtil.initBuyCardView(head);

//        ArrayList<StoreBean> beans = new ArrayList<>();
//        beans.add(new StoreBean());
//        beans.add(new StoreBean());

        orderingAdapter.addHeaderView(head);


        orderingAdapter.addFooterView(foot);


        getOrderDetail();
    }

    private void initHead2View(View head2, OrderDetailBean orderDetailBean) {
        TextView tv_store = head2.findViewById(R.id.tv_store);
        tv_store.setText(orderDetailBean.storeName + "");

        TextView tv_time_value = head2.findViewById(R.id.tv_time_value);
        tv_time_value.setVisibility(View.GONE);

        RecyclerView recyclerView = head2.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        OrderFoodAdapter adapter = new OrderFoodAdapter(orderDetailBean.orderItemList);
        recyclerView.setAdapter(adapter);

        TextView tv_youhuiquan_value = head2.findViewById(R.id.tv_youhuiquan_value);
        tv_youhuiquan_value.setTextColor(tv_youhuiquan_value.getResources().getColor(R.color.color_282525));
        tv_youhuiquan_value.setCompoundDrawables(null, null, null, null);
        tv_youhuiquan_value.setText("¥1");
        tv_youhuiquan_value.setTypeface(Typeface.DEFAULT_BOLD);


        TextView tv_total_money_value = head2.findViewById(R.id.tv_total_money_value);
        tv_total_money_value.setText(orderDetailBean.totalAmount + "");
        TextView tv_baozhuangfei_value = head2.findViewById(R.id.tv_baozhuangfei_value);
        tv_baozhuangfei_value.setText("");
        TextView tv_peisongfei_value = head2.findViewById(R.id.tv_peisongfei_value);
        tv_peisongfei_value.setText(orderDetailBean.freightAmount + "");
        TextView tv_total_value = head2.findViewById(R.id.tv_total_value);
        tv_total_value.setText(orderDetailBean.totalAmount + "");
        TextView tv_total_youhui_value = head2.findViewById(R.id.tv_total_youhui_value);
        tv_total_youhui_value.setText(orderDetailBean.couponAmount + "");
    }

    //退款中
    private void initHeadViewtuikuan(View head) {
        MyGridView gridview_tuikuan = head.findViewById(R.id.gridview_tuikuan);

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order_beihuo, R.drawable.icon_quxiaodingdan, "取消订单", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cuidan, R.drawable.icon_cuidan, "催单", R.color.color_ccc));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_change_order, R.drawable.icon_gaidingdanxinxi, "改订单信息", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider, R.drawable.icon_lianxiqishou, "联系骑手", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "联系商家", R.color.color_3B3838));


        setGridviewAdapter(gridview_tuikuan, beans);

    }

    private void setGridviewAdapter(MyGridView gridview_tuikuan, ArrayList<OrderGridviewItemBean> beans) {
        OrderGridviewItemAdapter adapter = new OrderGridviewItemAdapter(this, beans);
        gridview_tuikuan.setAdapter(adapter);
        gridview_tuikuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommonUtil.performOrderGridviewClick(OrderDetailActivity.this, adapter, i,id);
            }
        });
    }

    //备货
    private void initHeadViewbeihuo(View head) {

        MyGridView gridview_beihuo = head.findViewById(R.id.gridview_beihuo);

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order_beihuo, R.drawable.icon_quxiaodingdan, "取消订单", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cuidan, R.drawable.icon_cuidan, "催单", R.color.color_ccc));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_change_order, R.drawable.icon_gaidingdanxinxi, "改订单信息", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider, R.drawable.icon_lianxiqishou, "联系骑手", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "联系商家", R.color.color_3B3838));

        setGridviewAdapter(gridview_beihuo, beans);


        TextView tv_jingkuai = head.findViewById(R.id.tv_jingkuai);

        String str1 = time + "";
        SpannableString msp = new SpannableString(str1 + "送达");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_jingkuai.setText(msp);
    }


    //等待支付
    private void initHeadViewWaite(View head) {
        MyGridView gridview_3 = head.findViewById(R.id.gridview_3);

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order, R.drawable.icon_quxiaodingdan, "取消订单", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "联系商家", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_pay_now, R.drawable.icon_lijizhifu, "立即支付", R.color.color_FF6200));

        setGridviewAdapter(gridview_3, beans);


        TextView tv_des = head.findViewById(R.id.tv_des);

        String str1 = time + "分钟";
        SpannableString msp = new SpannableString(str1 + "内未支付，订单将自动取消");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_des.setText(msp);
    }

    //订单取消
    private void initHeadViewQuxiao(View head) {

    }


    //    默认
    private void initHeadView(View head) {
        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_reOrder, R.drawable.icon_zailaiyidan, "再来一单", R.color.color_FF6200));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_comment, R.drawable.icon_pinglundefen, "评价得积分", R.color.color_FF6200));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_shouhou, R.drawable.icon_shouhou, "申请售后", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "联系商家", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider, R.drawable.icon_lianxiqishou, "联系骑手", R.color.color_3B3838));

        MyGridView gridview_complete = head.findViewById(R.id.gridview_complete);
        setGridviewAdapter(gridview_complete, beans);
    }



    private void initFootView(View foot) {
        line4 = foot.findViewById(R.id.line4);
        tv_people = foot.findViewById(R.id.tv_people);
        tv_people_des = foot.findViewById(R.id.tv_people_des);
        ll_bottom = foot.findViewById(R.id.ll_bottom);

        tv_time_des = foot.findViewById(R.id.tv_time_des);
        tv_address_des = foot.findViewById(R.id.tv_address_des);
        tv_type_des = foot.findViewById(R.id.tv_type_des);
        tv_ordernum_des = foot.findViewById(R.id.tv_ordernum_des);
        tv_pay_type_des = foot.findViewById(R.id.tv_pay_type_des);
        tv_pay_time_des = foot.findViewById(R.id.tv_pay_time_des);
        tv_beizhu_des = foot.findViewById(R.id.tv_beizhu_des);

        TextView tv_copy = foot.findViewById(R.id.tv_copy);
        tv_copy.setOnClickListener(this);
    }


    String str = "等待支付，剩余";

    private void countDown() {
        Disposable subscription = Observable.interval(0, 1, TimeUnit.SECONDS).
                take(time + 1).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {

                SpannableString msp = new SpannableString(str + (time - aLong));
                msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), str.length(), msp.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

                tv_title.setText(msp);
            }

        });
    }

    //订单追踪弹窗
    private void showTraceDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_order_detail_trace);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);

        CommonUtil.initTraceAdapter(recyclerview);


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

        }

        bottomSheetDialog.show();
    }








    //    地图相关代码
    MapView mMapView;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写

        if (aMap == null) {
            aMap = mMapView.getMap();
            UiSettings mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);//设置地图默认的缩放按钮是否显示
            mUiSettings.setMyLocationButtonEnabled(false);// 设置地图默认的定位按钮是否显示
        }

//        定位到自己的位置
//        MyLocationStyle myLocationStyle;
//        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.showMyLocation(true);
////        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
////                fromResource(R.drawable.icon_dengdaichuli));
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        aMap.addOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）

            }
        });
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return true;
            }
        });

        addMaker();

    }

    private void addMaker() {
        aMap.clear();

        AMapLocation location = SPUtils.geTinstance().getLocation();

//        LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度

        LatLng locationBean1 = new LatLng(location.getLatitude()+0.005d, location.getLongitude()-0.005d);
        MarkerOptions markerOption1 = new MarkerOptions();
        markerOption1.position(locationBean1);
        markerOption1.title(location.getPoiName()).snippet("");
        markerOption1.draggable(false);
        markerOption1.icon(
                // BitmapDescriptorFactory
                // .fromResource(R.drawable.location_marker)
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.touxiang_beijing)));
        // 将Marker设置为贴地显示，可以双指下拉看效果
        markerOption1.setFlat(true);
        aMap.addMarker(markerOption1);


        LatLng locationBean = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(locationBean);
        markerOption.title(location.getPoiName()).snippet("");

        markerOption.draggable(false);
        markerOption.icon(
                // BitmapDescriptorFactory
                // .fromResource(R.drawable.location_marker)
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.touxiang_beijing)));
        // 将Marker设置为贴地显示，可以双指下拉看效果
        markerOption.setFlat(true);

        Marker marker = aMap.addMarker(markerOption);

        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View infoWindow = getLayoutInflater().inflate(
                        R.layout.item_order_map_custom_bg, null);

                TextView tv_text = infoWindow.findViewById(R.id.tv_text);
//                tv_text.setText(marker.getTitle());

                return infoWindow;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        AMapLocation location = SPUtils.geTinstance().getLocation();
        LatLng locationBean = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng locationBean1 = new LatLng(location.getLatitude()+0.005d, location.getLongitude()-0.005d);

//CameraPosition的参数意思：目标可视区域的缩放级别。目标可视区域的倾斜度，以角度为单位。可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度。
//        // 设置所有maker显示在当前可视区域地图中
//        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                locationBean, 15, 0, 0)));


        int windowWidth = DisplayUtil.getWindowWidth(this);
        int windowHeight = DisplayUtil.getWindowHeight(this);
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(locationBean).include(locationBean1).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds,windowWidth/4,windowWidth/4, windowHeight/4,windowHeight/2));
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        showTraceDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();

        if (null != subscription && !subscription.isDisposed()) {
            subscription.dispose();
            subscription = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


}
