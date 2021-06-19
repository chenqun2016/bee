package com.bee.user.ui.search;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.bee.user.R;
import com.bee.user.bean.CityBean;
import com.bee.user.event.LocationChangedEvent;
import com.bee.user.event.MainEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2021/6/19
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class SearchCityActivity extends BaseActivity {


    @BindView(R.id.tv_location_area)
    TextView tv_location_area;

    @BindView(R.id.rv_city)
    RecyclerView rv_city;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_address_simple;
    }

    @OnClick({R.id.iv_back, R.id.tv_reLocation})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_reLocation://重新定位
                EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_reLocation));

                tv_location_area.setText("定位中.");
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);

        GridLayoutManager layoutManage = new GridLayoutManager(this, 4);
        rv_city.setLayoutManager(layoutManage);
        FeedbackGridAdapter feedbackGridAdapter = new FeedbackGridAdapter();
        rv_city.setAdapter(feedbackGridAdapter );

        Api.getClient(HttpRequest.baseUrl_sys).openCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CityBean>>() {
                    @Override
                    public void onSuccess(List<CityBean> cityBean) {

                    }
                });
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationChangedEvent(LocationChangedEvent event) {
        AMapLocation amapLocation = SPUtils.geTinstance().getLocation();
        if (null != amapLocation) {
            if (amapLocation.getErrorCode() == 0) {
//                tv_location_area.setText( amapLocation.getStreet() + amapLocation.getStreetNum());
                tv_location_area.setText( amapLocation.getAddress() );
            }
        }
    }

    public static class FeedbackGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public int current = -1;

        public FeedbackGridAdapter() {
            super(R.layout.item_choose_city);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, String bean) {

        }
    }

}
