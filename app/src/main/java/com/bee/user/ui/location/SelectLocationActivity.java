package com.bee.user.ui.location;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.event.LocationChangedEvent;
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
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            R.id.tv_reLocation, R.id.tv_more_locatino, R.id.tv_more})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_right://管理
                startActivityForResult(new Intent(this, ChooseAddressActivity.class),REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_Secectlocation);
                break;

            case R.id.tv_location://地址
                startActivityForResult(new Intent(this, SearchCityActivity.class),REQUEST_CODE);
//                showCityPicker();
                break;
            case R.id.tv_reLocation://重新定位
                EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reLocation));

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
        EventBus.getDefault().unregister(this);
    }
    ChooseAddressAdapter2 chooseAddressAdapter;
    DingWeiAdapter2 dingWeiAdapter;

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);

        AMapLocation location = SPUtils.geTinstance().getLocation();
        if(null != location){
            tv_location.setText(location.getCity());
            tv_location_area.setText(location.getPoiName());
        }

        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        chooseAddressAdapter = new ChooseAddressAdapter2();
        chooseAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                AddressBean addressBean = chooseAddressAdapter.getData().get(position);
                MainEvent mainEvent = new MainEvent(TYPE_reset_Location);
                mainEvent.addressBean = addressBean;
                EventBus.getDefault().post(mainEvent);
                finish();
            }
        });
        recyclerview1.setAdapter(chooseAddressAdapter);

        recyclerview2.setLayoutManager(new LinearLayoutManager(this));
        dingWeiAdapter = new DingWeiAdapter2();
        recyclerview2.setAdapter(dingWeiAdapter);
        ll_kepeisong.setVisibility(View.GONE);
        recyclerview2.setVisibility(View.GONE);
        getAddress();
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

//    public void showCityPicker() {
//        List<HotCity> hotCities = new ArrayList<>();
//        hotCities.add(new HotCity("北京", "北京", "101010100"));
//        hotCities.add(new HotCity("上海", "上海", "101020100"));
//        hotCities.add(new HotCity("广州", "广东", "101280101"));
//        hotCities.add(new HotCity("深圳", "广东", "101280601"));
//        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
//
//
//        CityPicker.from(this)
//                .enableAnimation(true)
//                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
//                .setLocatedCity(null)
//                .setHotCities(hotCities)
//                .setOnPickListener(new OnPickListener() {
//                    @Override
//                    public void onPick(int position, City data) {
//                        tv_location.setText(data.getName());
//                        Toast.makeText(
//                                SelectLocationActivity.this,
//                                String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
//                                Toast.LENGTH_SHORT)
//                                .show();
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast.makeText(SelectLocationActivity.this, "取消选择", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onLocate() {
//                        LogUtil.d("onLocate");
//
//                        EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reLocation));
//
//                    }
//                })
//                .show();
//    }


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
            tv_name.setText(addressBean.name+"");

            baseViewHolder.findView(R.id.imageview).setVisibility(View.INVISIBLE);
        }
    }

    public static class DingWeiAdapter2 extends BaseQuickAdapter<DingWeiBean, BaseViewHolder> {
        public DingWeiAdapter2() {
            super(R.layout.item_dingwei2);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, DingWeiBean dingWeiBean) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationChangedEvent(LocationChangedEvent event) {
        AMapLocation amapLocation = SPUtils.geTinstance().getLocation();
        if (null != amapLocation) {
            if (amapLocation.getErrorCode() == 0) {
//                tv_location_area.setText( amapLocation.getStreet() + amapLocation.getStreetNum());
                tv_location_area.setText( amapLocation.getPoiName() );
                CityPicker.from(SelectLocationActivity.this).locateComplete(new LocatedCity(amapLocation.getCity(), amapLocation.getProvince(), amapLocation.getAdCode()), LocateState.SUCCESS);
            }
        }
    }
}
