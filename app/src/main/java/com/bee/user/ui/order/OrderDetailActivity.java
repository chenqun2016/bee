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
 * ????????????????????????
 * ???????????????2020/09/23  17???08
 * ?????????
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
//                    case Constants.TYPE_PAY_WAITE://????????????
//                        break;
//                    case Constants.TYPE_READY://??????????????????
//                        break;
//                    case Constants.TYPE_PEISONG://???????????????
//                        break;
//                    case Constants.TYPE_COMPLETE://???????????????
//                        break;
//                    case Constants.TYPE_CANCELED://???????????????
//                        break;
//                    case Constants.TYPE_TUIKUAN://?????????
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
                subscribeOn(Schedulers.io())//???????????? ???????????????io??????
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

                        if(orderDetailBean.comment){
                            View  headComment = View.inflate(OrderDetailActivity.this, R.layout.item_order_my_comment, null);
                            headComment.findViewById(R.id.tv_my_comment).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    OrderCommentActivity.newInstance(OrderDetailActivity.this,id,orderDetailBean.storeId,orderDetailBean,OrderCommentActivity.TYPE_RE_COMMENT);
                                }
                            });
                            orderingAdapter.addHeaderView(headComment);
                        }
                    }
                });
    }

    private void initDatas(OrderDetailBean orderDetailBean) {

         tv_people_des.setText("");
         tv_time_des.setText(orderDetailBean.deliveryTime+"");
         tv_address_des.setText(orderDetailBean.receiverDetailAddress+"\n"+orderDetailBean.createName+orderDetailBean.receiverPhone);
         tv_type_des.setText(orderDetailBean.billType+"");
         tv_ordernum_des.setText(orderDetailBean.orderSn+"");
         tv_pay_type_des.setText(orderDetailBean.payType==1?"????????????":"");
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

                if (verticalOffset == 0) {//??????
                    background.setAlpha(0);
                    tv_title.setAlpha(0f);
                } else if (Math.abs(verticalOffset) >= scrollRangle) {
                    background.setAlpha(1);
                    tv_title.setAlpha(1f);
                }else {
                    //??????????????????
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
            case Constants.TYPE_ORDER_DPS://???????????????
                tv_title.setText("???????????????");
                head = View.inflate(this, R.layout.head_orderdetail_complete, null);
                initHeadView(head);
                break;

            case Constants.TYPE_ORDER_WP://????????????
                tv_title.setText("?????????????????????10");
                head = View.inflate(this, R.layout.head_orderdetail_waite, null);
                initHeadViewWaite(head);

                countDown();
                break;
            case Constants.TYPE_ORDER_OMJ://??????????????????
                tv_title.setText("??????????????????");
                head = View.inflate(this, R.layout.head_orderdetail_beihuo, null);
                initHeadViewbeihuo(head);

                break;
            case Constants.TYPE_ORDER_SPS://???????????????
                tv_title.setText("???????????????");
                head = View.inflate(this, R.layout.head_orderdetail_beihuo, null);
                initHeadViewbeihuo(head);

                break;

            case Constants.TYPE_ORDER_OC://???????????????
                tv_title.setText("???????????????");
                head = View.inflate(this, R.layout.head_orderdetail_quxiao, null);
                initHeadViewQuxiao(head);

                line4.setVisibility(View.GONE);
                tv_people.setVisibility(View.GONE);
                tv_people_des.setVisibility(View.GONE);
                ll_bottom.setVisibility(View.VISIBLE);
                break;
            case Constants.TYPE_ORDER_PJ://??????
                head = View.inflate(this, R.layout.head_orderdetail_tuikuan, null);
                initHeadViewtuikuan(head);
                tv_title.setText("???????????????");
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
        tv_youhuiquan_value.setText("??1");
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

    //?????????
    private void initHeadViewtuikuan(View head) {
        MyGridView gridview_tuikuan = head.findViewById(R.id.gridview_tuikuan);

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order_beihuo, R.drawable.icon_quxiaodingdan, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cuidan, R.drawable.icon_cuidan, "??????", R.color.color_ccc));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_change_order, R.drawable.icon_gaidingdanxinxi, "???????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider, R.drawable.icon_lianxiqishou, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "????????????", R.color.color_3B3838));


        setGridviewAdapter(gridview_tuikuan, beans);

    }

    private void setGridviewAdapter(MyGridView gridview_tuikuan, ArrayList<OrderGridviewItemBean> beans) {
        OrderGridviewItemAdapter adapter = new OrderGridviewItemAdapter(this, beans);
        gridview_tuikuan.setAdapter(adapter);
        gridview_tuikuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommonUtil.performOrderGridviewClick(OrderDetailActivity.this, adapter, i,id,orderDetailBean);
            }
        });
    }

    //??????
    private void initHeadViewbeihuo(View head) {

        MyGridView gridview_beihuo = head.findViewById(R.id.gridview_beihuo);

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order_beihuo, R.drawable.icon_quxiaodingdan, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cuidan, R.drawable.icon_cuidan, "??????", R.color.color_ccc));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_change_order, R.drawable.icon_gaidingdanxinxi, "???????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider, R.drawable.icon_lianxiqishou, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "????????????", R.color.color_3B3838));

        setGridviewAdapter(gridview_beihuo, beans);


        TextView tv_jingkuai = head.findViewById(R.id.tv_jingkuai);

        String str1 = time + "";
        SpannableString msp = new SpannableString(str1 + "??????");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_jingkuai.setText(msp);
    }


    //????????????
    private void initHeadViewWaite(View head) {
        MyGridView gridview_3 = head.findViewById(R.id.gridview_3);

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order, R.drawable.icon_quxiaodingdan, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_pay_now, R.drawable.icon_lijizhifu, "????????????", R.color.color_FF6200));

        setGridviewAdapter(gridview_3, beans);


        TextView tv_des = head.findViewById(R.id.tv_des);

        String str1 = time + "??????";
        SpannableString msp = new SpannableString(str1 + "????????????????????????????????????");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_des.setText(msp);
    }

    //????????????
    private void initHeadViewQuxiao(View head) {

    }


    //    ??????
    private void initHeadView(View head) {
        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_reOrder, R.drawable.icon_zailaiyidan, "????????????", R.color.color_FF6200));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_comment, R.drawable.icon_pinglundefen, "???????????????", R.color.color_FF6200));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_shouhou, R.drawable.icon_shouhou, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop, R.drawable.icon_lianxishangjia, "????????????", R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider, R.drawable.icon_lianxiqishou, "????????????", R.color.color_3B3838));

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


    String str = "?????????????????????";
    private void countDown() {
        subscription = Observable.interval(0, 1, TimeUnit.SECONDS).
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

    //??????????????????
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
            e.printStackTrace();
        }

        bottomSheetDialog.show();
    }








    //    ??????????????????
    MapView mMapView;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// ?????????????????????

        if (aMap == null) {
            aMap = mMapView.getMap();
            UiSettings mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);//?????????????????????????????????????????????
            mUiSettings.setMyLocationButtonEnabled(false);// ?????????????????????????????????????????????
        }

//        ????????????????????????
//        MyLocationStyle myLocationStyle;
//        myLocationStyle = new MyLocationStyle();//??????????????????????????????myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1???1???????????????????????????myLocationType????????????????????????????????????
//        myLocationStyle.showMyLocation(true);
////        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
////                fromResource(R.drawable.icon_dengdaichuli));
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//???????????????????????????????????????????????????
//        myLocationStyle.interval(2000); //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//        aMap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
//        aMap.setMyLocationEnabled(true);// ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???

        aMap.addOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //???location????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

            }
        });
        aMap.setOnMapLoadedListener(this);// ??????amap???????????????????????????
        aMap.setOnInfoWindowClickListener(this);// ????????????infoWindow???????????????
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

//        LatLng CHENGDU = new LatLng(30.679879, 104.064855);// ??????????????????

        if(null == location) return;
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
        // ???Marker???????????????????????????????????????????????????
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
        // ???Marker???????????????????????????????????????????????????
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

        marker.showInfoWindow();// ????????????????????????infowinfow
    }

    /**
     * ??????amap??????????????????????????????
     */
    @Override
    public void onMapLoaded() {
        AMapLocation location = SPUtils.geTinstance().getLocation();
        LatLng locationBean = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng locationBean1 = new LatLng(location.getLatitude()+0.005d, location.getLongitude()-0.005d);

//CameraPosition??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????0 ??????360 ??????
//        // ????????????maker????????????????????????????????????
//        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                locationBean, 15, 0, 0)));


        int windowWidth = DisplayUtil.getWindowWidth(this);
        int windowHeight = DisplayUtil.getWindowHeight(this);
        // ????????????maker????????????????????????????????????
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
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        mMapView.onDestroy();

        if (null != subscription && !subscription.isDisposed()) {
            subscription.dispose();
            subscription = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        mMapView.onPause();
    }

    /**
     * ??????????????????
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


}
