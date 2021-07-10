package com.bee.user.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bee.user.R;
import com.bee.user.utils.sputils.SPUtils;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/29  19：57
 * 描述：
 */
public class DingweiDialog extends Dialog implements AMap.OnMapLoadedListener {
    TextureMapView mMapView;
    AMap aMap;



    public OnDingWei mOnDingWei ;

    public DingweiDialog(@NonNull Context context,OnDingWei mOnDingWei) {
        super(context, R.style.loadingDialogTheme);
        this.mOnDingWei = mOnDingWei;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_home_ditu);

        initView();
    }

    private void initView() {

        findViewById(R.id.tv_guangguang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != DingweiDialog.this) {
                    DingweiDialog.this.dismiss();
                }

            }
        });
        findViewById(R.id.tv_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnDingWei){
                    mOnDingWei.onDingWei();
                }
            }
        });

        mMapView = findViewById(R.id.map);
        mMapView.onCreate(null);

        aMap = mMapView.getMap();

        UiSettings mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);//设置地图默认的缩放按钮是否显示
        mUiSettings.setMyLocationButtonEnabled(false);// 设置地图默认的定位按钮是否显示


        aMap.addOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）

            }
        });


        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器

    }



    @Override
    public void onMapLoaded() {
        AMapLocation location = SPUtils.geTinstance().getLocation();
        if (null == location) {
            //        //        定位到自己的位置
            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.showMyLocation(true);
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                    fromResource(R.drawable.icon_dingwei_cheng));
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        } else {
            addMaker();

            aMap.setMyLocationEnabled(false);

            LatLng locationBean = new LatLng(location.getLatitude(), location.getLongitude());
            // 设置所有maker显示在当前可视区域地图中
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    locationBean, 15, 0, 0)));
        }

    }


    private void addMaker() {
        AMapLocation location = SPUtils.geTinstance().getLocation();

        if(null == location){
            return;
        }
//        LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度

        LatLng locationBean = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(locationBean);
        markerOption.title(location.getPoiName()).snippet("");

        markerOption.draggable(false);
        markerOption.icon(
                // BitmapDescriptorFactory
                // .fromResource(R.drawable.location_marker)
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getContext().getResources(),
                                R.drawable.icon_dingwei_cheng)));
        // 将Marker设置为贴地显示，可以双指下拉看效果
        markerOption.setFlat(true);

        aMap.clear();
        Marker marker = aMap.addMarker(markerOption);

//        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                View infoWindow = getLayoutInflater().inflate(
//                        R.layout.item_map_custom_bg, null);
//
//                TextView tv_text = infoWindow.findViewById(R.id.tv_text);
//                tv_text.setText(marker.getTitle());
//
//                return infoWindow;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                return null;
//            }
//        });
//
//        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    public void setmOnDingWei(OnDingWei mOnDingWei) {
        this.mOnDingWei = mOnDingWei;
    }

    public interface OnDingWei {
        void onDingWei();
    }

}
