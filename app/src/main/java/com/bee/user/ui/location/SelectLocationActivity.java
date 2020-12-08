package com.bee.user.ui.location;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.event.LocationChangedEvent;
import com.bee.user.event.MainEvent;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.search.SearchLocationActivity;
import com.bee.user.ui.xiadan.ChooseAddressActivity;
import com.bee.user.ui.xiadan.NewAddressActivity;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    @OnClick({R.id.tv_right, R.id.tv_select, R.id.tv_location,
            R.id.tv_reLocation, R.id.tv_more_locatino, R.id.tv_more})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_right://管理
                startActivity(new Intent(this, ChooseAddressActivity.class));
                break;

            case R.id.tv_location://地址
                showCityPicker();
                break;
            case R.id.tv_reLocation://重新定位
                EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reLocation));

                tv_location_area.setText("定位中.");
                break;

            case R.id.tv_more_locatino://新增地址
                startActivity(new Intent(this, NewAddressActivity.class));
                break;

            case R.id.tv_select://选择
            case R.id.tv_more://新增地址

                startActivity(new Intent(this, SearchLocationActivity.class));
                break;
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

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);

        recyclerview1.setLayoutManager(new LinearLayoutManager(this));

        ChooseAddressAdapter2 chooseAddressAdapter = new ChooseAddressAdapter2();
        recyclerview1.setAdapter(chooseAddressAdapter);


        ArrayList addressBeans = new ArrayList<>();
        addressBeans.add(new AddressBean(0));
        addressBeans.add(new AddressBean(0));

        chooseAddressAdapter.setNewInstance(addressBeans);


        recyclerview2.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DingWeiBean> dingWeiBeans = new ArrayList<>();
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        DingWeiAdapter2 dingWeiAdapter = new DingWeiAdapter2(dingWeiBeans);
        recyclerview2.setAdapter(dingWeiAdapter);

    }


    public void showCityPicker() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));


        CityPicker.from(this)
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        tv_location.setText(data.getName());
                        Toast.makeText(
                                SelectLocationActivity.this,
                                String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                Toast.LENGTH_SHORT)
                                .show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SelectLocationActivity.this, "取消选择", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLocate() {
                        LogUtil.d("onLocate");

                        EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reLocation));

                    }
                })
                .show();
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


            baseViewHolder.findView(R.id.imageview).setVisibility(View.INVISIBLE);
        }
    }

    public static class DingWeiAdapter2 extends BaseQuickAdapter<DingWeiBean, BaseViewHolder> {
        public DingWeiAdapter2(@Nullable List<DingWeiBean> data) {
            super(R.layout.item_dingwei2, data);
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
                tv_location_area.setText( amapLocation.getStreet() + amapLocation.getStreetNum());
                CityPicker.from(SelectLocationActivity.this).locateComplete(new LocatedCity(amapLocation.getCity(), amapLocation.getProvince(), amapLocation.getAdCode()), LocateState.SUCCESS);
            }
        }
    }
}
