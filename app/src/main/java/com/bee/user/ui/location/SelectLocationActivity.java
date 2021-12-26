package com.bee.user.ui.location;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.event.MainEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.search.SearchCityActivity;
import com.bee.user.ui.search.SearchLocationActivity;
import com.bee.user.ui.xiadan.ChooseAddressActivity;
import com.bee.user.ui.xiadan.NewAddressActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.event.MainEvent.TYPE_reset_Location;
import static com.bee.user.ui.search.SearchCityActivity.REQUEST_CODE;
import static com.bee.user.ui.search.SearchCityActivity.RESULT_CODE;
import static com.bee.user.ui.xiadan.ChooseAddressActivity.REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_Secectlocation;
import static com.bee.user.ui.xiadan.ChooseAddressActivity.RESULT_CODE_CHANGED;
import static com.bee.user.ui.xiadan.NewAddressActivity.REQUEST_CODE_NEW;
import static com.bee.user.ui.xiadan.NewAddressActivity.RESULT_CODE_NEWADDRESS;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/07  20：19
 * 描述：
 */
public class SelectLocationActivity extends BaseActivity {
    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;

    @BindView(R.id.recyclerview2)
    RecyclerView recyclerview2;

    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.tv_location_area)
    TextView tv_location_area;

    @BindView(R.id.ll_kepeisong)
    LinearLayout ll_kepeisong;

    @OnClick({R.id.tv_right, R.id.tv_select, R.id.tv_location,
            R.id.tv_reLocation, R.id.tv_more_locatino, R.id.tv_more,R.id.tv_location_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location_area://当前定位
                EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reset_Location_from_SelectLocationActivity));
                finish();
                break;
            case R.id.tv_right://管理
                startActivityForResult(new Intent(this, ChooseAddressActivity.class),REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_Secectlocation);
                break;

            case R.id.tv_location://地址
                startActivityForResult(new Intent(this, SearchCityActivity.class),REQUEST_CODE);
//                showCityPicker();
                break;
            case R.id.tv_reLocation://重新定位
                mLocationClient.startLocation();
                tv_location_area.setText("定位中.");
                break;

            case R.id.tv_more_locatino://新增地址
                Intent intent = new Intent(this, NewAddressActivity.class);
                startActivityForResult(intent,REQUEST_CODE_NEW);
                break;

            case R.id.tv_select://选择
            case R.id.tv_more://新增地址

                startActivity(new Intent(this, SearchLocationActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            String city =  data.getStringExtra("city");
            if(null != city){
                tv_location.setText(city+"");
            }
        }else if(requestCode == REQUEST_CODE_NEW && resultCode == RESULT_CODE_NEWADDRESS){
            getAddress();
        }else if(requestCode == REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_Secectlocation && resultCode == RESULT_CODE_CHANGED){
            getAddress();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_location;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    ChooseAddressAdapter2 chooseAddressAdapter;
    DingWeiAdapter2 dingWeiAdapter;

    @Override
    public void initViews() {

        AMapLocation location = SPUtils.geTinstance().getLocation();
        if(null != location){
            tv_location.setText(location.getCity());
            tv_location_area.setText(location.getPoiName());

            ll_kepeisong.setVisibility(View.VISIBLE);
            recyclerview2.setVisibility(View.VISIBLE);
            getNearByBuilding(location);
        }else{
            ll_kepeisong.setVisibility(View.GONE);
            recyclerview2.setVisibility(View.GONE);
        }

        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        chooseAddressAdapter = new ChooseAddressAdapter2();
        chooseAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                AddressBean addressBean = chooseAddressAdapter.getData().get(position);
                MainEvent mainEvent = new MainEvent(TYPE_reset_Location);
                mainEvent.locationInfo = new MainEvent.LocationInfo(addressBean.detailAddress,addressBean.latitude,addressBean.longitude);
                EventBus.getDefault().post(mainEvent);
                finish();
            }
        });
        recyclerview1.setAdapter(chooseAddressAdapter);

        recyclerview2.setLayoutManager(new LinearLayoutManager(this));
        dingWeiAdapter = new DingWeiAdapter2();
        dingWeiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                if(dingWeiAdapter.current != position){
                    int pre = dingWeiAdapter.current;
                    dingWeiAdapter.notifyItemChanged(pre);
                }
                dingWeiAdapter.current = position;
                dingWeiAdapter.notifyItemChanged(position);
                DingWeiBean bean = dingWeiAdapter.getData().get(position);
                MainEvent mainEvent = new MainEvent(TYPE_reset_Location);
                mainEvent.locationInfo = new MainEvent.LocationInfo(bean.name,bean.latitude,bean.longitude);
                EventBus.getDefault().post(mainEvent);
                finish();
            }
        });
        recyclerview2.setAdapter(dingWeiAdapter);


        initLocations();
        getAddress();

    }


    private void getNearByBuilding(AMapLocation location) {
        Map<String,String> map = new HashMap();

//        map.put("longitude", location.getLongitude()+"");
//        map.put("latitude", location.getLatitude()+"");

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

    private void getAddress() {
        Api.getClient(HttpRequest.baseUrl_member).listAddress().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<AddressBean>>() {
                    @Override
                    public void onSuccess(List<AddressBean> addressBean2) {
                        if(null != addressBean2 && addressBean2.size()>0){
                            chooseAddressAdapter.setNewInstance(addressBean2);
                        }
                    }
                });
    }


    private AMapLocationClient mLocationClient;
    //声明定位回调监听器
    //异步获取定位结果
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {

            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    LogUtil.d("location==" + amapLocation.toStr());
                    SPUtils.geTinstance().setLocation(amapLocation);

                    tv_location_area.setText( amapLocation.getPoiName() );
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
            closeLoadingDialog();
        }
    };


    private void initLocations() {

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
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
//        mLocationClient.startLocation();

    }

    public static class ChooseAddressAdapter2 extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
        public ChooseAddressAdapter2() {
            super(R.layout.item_chooose_address);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, AddressBean addressBean) {
            TextView tv_address = baseViewHolder.findView(R.id.tv_address);
            TextView tv_tag = baseViewHolder.findView(R.id.tv_tag);
            TextView tv_address2 = baseViewHolder.findView(R.id.tv_address2);
            TextView tv_name = baseViewHolder.findView(R.id.tv_name);

            tv_address.setText(addressBean.detailAddress+"");
            tv_tag.setText(CommonUtil.getLocationTag(addressBean.tag));
            tv_address2.setText(addressBean.houseNumber+"");
            tv_name.setText(addressBean.name+""+addressBean.phoneNumber);

            baseViewHolder.findView(R.id.imageview).setVisibility(View.INVISIBLE);
        }
    }

    public static class DingWeiAdapter2 extends BaseQuickAdapter<DingWeiBean, BaseViewHolder> {
        public int current = -1;

        public DingWeiAdapter2() {
            super(R.layout.item_dingwei2);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, DingWeiBean dingWeiBean) {
            ImageView image = holder.getView(R.id.image);

            if(current == holder.getAbsoluteAdapterPosition()){
                image.setImageResource(R.drawable.icon_dingwei_cheng);
            }else{
                image.setImageResource(R.drawable.icon_circle_grey);
            }
            TextView tv_name = holder.getView(R.id.tv_name);
            TextView tv_dizhi = holder.getView(R.id.tv_dizhi);

            tv_name.setText(dingWeiBean.name);
            tv_dizhi.setText(dingWeiBean.address);
        }
    }


}
