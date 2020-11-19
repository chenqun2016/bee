package com.bee.user.ui.order;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.bean.OrderGridviewItemBean;
import com.bee.user.ui.adapter.OrderGridviewItemAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.MyGridView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/26  16：01
 * 描述：
 */
public class OrderingMapActivity extends BaseActivity implements AMap.OnMapLoadedListener, AMap.OnInfoWindowClickListener {

    @BindView(R.id.iv_back)
    ImageButton iv_back;

    @BindView(R.id.view1)
    LinearLayout view1;

    @BindView(R.id.vie2)
    RelativeLayout vie2;


    @BindView(R.id.gridview_3)
    MyGridView gridview_3;
    @BindView(R.id.gridview_beihuo)
    MyGridView gridview_beihuo;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ordering_map;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        switch (type) {
            case Constants.TYPE_PAY_WAITE://等待支付
                vie2.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);
                setWaitePayGridview();
                break;
            case Constants.TYPE_READY://商家正在备货
            case Constants.TYPE_PEISONG://商品配送中
                vie2.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                setBeihuoGridview();
                break;
            case Constants.TYPE_COMPLETE://订单已送达
                break;
            case Constants.TYPE_CANCELED://订单已取消
                break;
            case Constants.TYPE_TUIKUAN://退款中
                break;
            default:
                break;
        }


        int statusBarHeight = ImmersionBar.getStatusBarHeight(this);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) iv_back.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        iv_back.setLayoutParams(layoutParams);



    }




    private void setWaitePayGridview() {

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order,R.drawable.icon_quxiaodingdan,"取消订单",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop,R.drawable.icon_lianxishangjia,"联系商家",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_pay_now,R.drawable.icon_lijizhifu,"立即支付",R.color.color_FF6200));

        setGridviewAdapter(gridview_3,beans);


    }
    private void setBeihuoGridview() {


        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order_beihuo,R.drawable.icon_quxiaodingdan,"取消订单",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cuidan,R.drawable.icon_cuidan,"催单",R.color.color_ccc));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_change_order,R.drawable.icon_gaidingdanxinxi,"改订单信息",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider,R.drawable.icon_lianxiqishou,"联系骑手",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop,R.drawable.icon_lianxishangjia,"联系商家",R.color.color_3B3838));

        setGridviewAdapter(gridview_beihuo,beans);


    }
    private void setGridviewAdapter(MyGridView gridview_tuikuan, ArrayList<OrderGridviewItemBean> beans) {
        OrderGridviewItemAdapter adapter = new OrderGridviewItemAdapter(this,beans);
        gridview_tuikuan.setAdapter(adapter);
        gridview_tuikuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommonUtil.performOrderGridviewClick(OrderingMapActivity.this,adapter,i);
            }
        });
    }


    //    地图
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


        addMaker();

    }

    private void addMaker() {
        aMap.clear();

        AMapLocation location = SPUtils.geTinstance().getLocation();

//        LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度

        LatLng locationBean1 = new LatLng(location.getLatitude()+0.005d, location.getLongitude()-0.005d);
        MarkerOptions markerOption1 = new MarkerOptions();
        markerOption1.position(locationBean1);
        markerOption1.title(location.getAoiName()).snippet("");
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
        markerOption.title(location.getAoiName()).snippet("");

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
}
