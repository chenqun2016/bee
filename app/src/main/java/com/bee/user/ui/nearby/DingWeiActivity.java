package com.bee.user.ui.nearby;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bee.user.R;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.DingWeiAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  16：47
 * 描述：
 */
public class DingWeiActivity extends BaseActivity implements AMap.OnMapLoadedListener {
    public static final int REQUEST_CODE_LOCATION_ACTIVITY = 11;
    public static final int RESULT_CODE_LOCATION_ACTIVITY = 111;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_other)
    TextView tv_other;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    DingWeiAdapter dingWeiAdapter;
    @OnClick({R.id.tv_location_area,R.id.tv_location})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_location_area:

                break;

            case R.id.tv_location:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dingwei2;
    }

    @Override
    public void initViews() {
        tv_title.setText("确认收货地址");
        tv_other.setText("当前可配送地址");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        dingWeiAdapter = new DingWeiAdapter();
        dingWeiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                DingWeiBean bean = (DingWeiBean) adapter.getData().get(position);
                try{
                    LatLng locationBean = new LatLng(Double.parseDouble(bean.latitude), Double.parseDouble(bean.longitude));
                    addMaker(locationBean,bean.name);

                    // 设置所有maker显示在当前可视区域地图中
                    aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            locationBean, 18, 0, 0)));
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(dingWeiAdapter.current != position){
                    int pre = dingWeiAdapter.current;
                    dingWeiAdapter.notifyItemChanged(pre);
                }
                dingWeiAdapter.current = position;
                dingWeiAdapter.notifyItemChanged(position);

                Intent intent = new Intent();
                intent.putExtra("data",bean);
                setResult(RESULT_CODE_LOCATION_ACTIVITY,intent);
                finish();
            }
        });
        recyclerview.setAdapter(dingWeiAdapter);

        getDatas();
    }

    private void getDatas() {
        Map<String,String> map = new HashMap();

        Intent intent = getIntent();
//        map.put("longitude", intent.getStringExtra("longitude"));
//        map.put("latitude", intent.getStringExtra("latitude"));

        map.put("longitude", "121.518689");
        map.put("latitude", "31.240972");

        Api.getClient(HttpRequest.baseUrl_shop)
                .nearByBuilding(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<DingWeiBean>>() {
                    @Override
                    public void onSuccess(List<DingWeiBean> beans) {
                        dingWeiAdapter.setNewInstance(beans);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
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
            UiSettings   mUiSettings = aMap.getUiSettings();
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

        AMapLocation location = SPUtils.geTinstance().getLocation();
//        LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
        LatLng locationBean = new LatLng(location.getLatitude(), location.getLongitude());
        addMaker(locationBean,location.getPoiName());
    }

    private void addMaker(LatLng locationBean,String title) {

        MarkerOptions  markerOption = new MarkerOptions();
        markerOption.position(locationBean);
        markerOption.title(title).snippet("");

        markerOption.draggable(false);
        markerOption.icon(
                // BitmapDescriptorFactory
                // .fromResource(R.drawable.location_marker)
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.icon_dingwei_cheng)));
        // 将Marker设置为贴地显示，可以双指下拉看效果
        markerOption.setFlat(true);

        aMap.clear();
        Marker marker = aMap.addMarker(markerOption);

        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View infoWindow = getLayoutInflater().inflate(
                        R.layout.item_map_custom_bg, null);

                TextView tv_text = infoWindow.findViewById(R.id.tv_text);
                tv_text.setText(marker.getTitle());

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


        // 设置所有maker显示在当前可视区域地图中
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                locationBean, 18, 0, 0)));
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


}
