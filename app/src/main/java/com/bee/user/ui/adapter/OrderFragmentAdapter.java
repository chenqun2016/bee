package com.bee.user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.OrderBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.YouhuiquanBean;
import com.bee.user.ui.order.OrderCommentActivity;
import com.bee.user.ui.order.OrderDetailMapActivity;
import com.bee.user.ui.order.OrderingMapActivity;
import com.bee.user.ui.order.TuiKuanActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.ui.xiadan.PayActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/19  21：32
 * 描述：
 */
public class OrderFragmentAdapter extends BaseMultiItemQuickAdapter<OrderBean, BaseViewHolder> implements LoadMoreModule {

    private List<TextureMapView> mapViews = new ArrayList();

    public OrderFragmentAdapter() {
        super();
        addItemType(OrderBean.type1, R.layout.item_order);
        addItemType(OrderBean.type2, R.layout.item_order2);
    }



    @Override
    protected void convert(@NotNull BaseViewHolder helper, OrderBean bean) {
        Context mContext = getRecyclerView().getContext();


        RecyclerView recyclerview = helper.findView(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext){
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        OrderFragmentAdapter.OrderItemAdapter orderItemAdapter = new OrderFragmentAdapter.OrderItemAdapter();
        recyclerview.setAdapter(orderItemAdapter);

        List<OrderBean.OrderItemBean> orderItemBeans = new ArrayList<OrderBean.OrderItemBean>();
        orderItemBeans.add(new OrderBean.OrderItemBean());
        orderItemBeans.add(new OrderBean.OrderItemBean());
        orderItemAdapter.setNewInstance(orderItemBeans);


        TextView name = helper.findView(R.id.name);
        TextView type = helper.findView(R.id.type);

        TextView tv_money = helper.findView(R.id.tv_money);
        TextView tv_total = helper.findView(R.id.tv_total);
        TextView tv_zailaiyidan = helper.findView(R.id.tv_zailaiyidan);
        TextView tv_pinglun = helper.findView(R.id.tv_pinglun);


        switch (bean.type) {

            case 1:
                tv_zailaiyidan.setText("去支付");
                tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(PayActivity.newIntent(mContext, new ArrayList<StoreBean>()));
                    }
                });
                type.setText("等待支付");
                type.setTextColor(type.getResources().getColor(R.color.FF6200));
                tv_pinglun.setVisibility(View.GONE);
                break;
            case 0:
            case 2:
                tv_zailaiyidan.setText("再来一单");
                tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(OrderingActivity.newIntent(mContext, new ArrayList<StoreBean>()));
                    }
                });

                type.setText("已送达");
                type.setTextColor(type.getResources().getColor(R.color.color_7C7877));

                tv_pinglun.setText("评论得12积分");
                tv_pinglun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, OrderCommentActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 3:
                tv_zailaiyidan.setText("再来一单");
                tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, OrderingActivity.class);
                        mContext.startActivity(intent);
                    }
                });

                type.setText("已送达");
                type.setTextColor(type.getResources().getColor(R.color.color_7C7877));

                tv_pinglun.setText("评论得12积分");
                tv_pinglun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, OrderCommentActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 4:
                tv_zailaiyidan.setText("退款进度");
                tv_zailaiyidan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, TuiKuanActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                type.setText("退款成功");
                type.setTextColor(type.getResources().getColor(R.color.color_7C7877));
                tv_pinglun.setText("再来一单");

                tv_pinglun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext, OrderingActivity.class));
                    }
                });
                break;

        }


        switch (helper.getItemViewType()) {
            case OrderBean.type1:


                break;
            case OrderBean.type2:
                TextureMapView map = helper.findView(R.id.map);
                map.onCreate(null);
                mapViews.add(map);

                AMap aMap = map.getMap();
                UiSettings mUiSettings = aMap.getUiSettings();
                mUiSettings.setAllGesturesEnabled(false);
                mUiSettings.setZoomControlsEnabled(false);//设置地图默认的缩放按钮是否显示
                mUiSettings.setMyLocationButtonEnabled(false);// 设置地图默认的定位按钮是否显示
                aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(mContext, OrderDetailMapActivity.class);
                        intent.putExtra("type", bean.type);
                        mContext.startActivity(intent);
                    }
                });
                aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(mContext, OrderDetailMapActivity.class);
                        intent.putExtra("type", bean.type);
                        mContext.startActivity(intent);
                    }
                });
                aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent intent = new Intent(mContext, OrderDetailMapActivity.class);
                        intent.putExtra("type", bean.type);
                        mContext.startActivity(intent);
                        return true;
                    }
                });

                try {
                    /**
                     * 因为listview 中的mapview会复用，所以我们每次清空上一次的element
                     */
                    aMap.clear();

                    AMapLocation location = SPUtils.geTinstance().getLocation();


                    LatLng locationBean = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.position(locationBean);
                    markerOption.title(location.getAoiName()).snippet("");

                    markerOption.draggable(false);
                    markerOption.icon(
                            // BitmapDescriptorFactory
                            // .fromResource(R.drawable.location_marker)
                            BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                    .decodeResource(mContext.getResources(),
                                            R.drawable.touxiang_beijing)));
                    // 将Marker设置为贴地显示，可以双指下拉看效果
                    markerOption.setFlat(true);

                    Marker marker = aMap.addMarker(markerOption);

                    aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            View infoWindow = View.inflate(mContext, R.layout.item_order_map_custom_bg, null);


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


//CameraPosition的参数意思：1目标可视区域的缩放级别。2目标可视区域的倾斜度，以角度为单位。3可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度。
//        // 设置所有maker显示在当前可视区域地图中
                    aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            locationBean, 15, 0, 0)));



                    LatLng locationBean1 = new LatLng(location.getLatitude()+0.005d, location.getLongitude());

                    LatLngBounds bounds = new LatLngBounds.Builder()
                            .include(locationBean).include(locationBean1).build();
                    aMap.moveCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds,0,0,DisplayUtil.dip2px(mContext,80),DisplayUtil.dip2px(mContext,5)));


                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            default:
                break;
        }


    }

    public void onDestroy() {
        for (TextureMapView mapView : mapViews) {
            Log.i("SHIXIN", "onDestroy : 销毁地图");
            mapView.onDestroy();
        }
    }


    public static class OrderItemAdapter extends BaseQuickAdapter<OrderBean.OrderItemBean, BaseViewHolder> {
        public OrderItemAdapter() {
            super(R.layout.item_order_item);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderBean.OrderItemBean o) {
            ImageView iv_image = baseViewHolder.findView(R.id.iv_image);
            Picasso.with(iv_image.getContext())
                    .load(R.drawable.food2)
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_image.getContext(), 10), 0, PicassoRoundTransform.CornerType.ALL))
                    .into(iv_image);
        }

    }
}