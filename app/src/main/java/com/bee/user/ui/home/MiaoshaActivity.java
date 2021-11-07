package com.bee.user.ui.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bee.user.R;
import com.bee.user.bean.PointDetailBen;
import com.bee.user.bean.TimeSectionBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.blankj.utilcode.util.ObjectUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.cindicatorview.CIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/26  16：58
 * 描述：
 */
public class MiaoshaActivity extends BaseActivity {
    @BindView(R.id.statusheight)
    View statusheight;

    @BindView(R.id.cindicator)
    CIndicatorView cindicator;
    private  final  String[] str = {"08:00/已开抢","10:00/抢购中","12:00/即将开始","14:00/即将开始"};

    private List<String> timeList =  new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();



    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_miaosha;
    }

    @Override
    public void initViews() {
        initGoodsTimeSection();
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);


        /*fragments.add(MiaoshaFragment.newInstance(0));
        fragments.add(MiaoshaFragment.newInstance(1));
        fragments.add(MiaoshaFragment.newInstance(2));
        fragments.add(MiaoshaFragment.newInstance(3));
        cindicator.initDatas(fragments, str, getSupportFragmentManager());
        cindicator.getIndicator().setItemUnderHeight(0);
        cindicator.getIndicator().setTabUnderHeight(0);*/
       /* cindicator.getIndicator().setItemUnderHeight(0);
        cindicator.getIndicator().setTabUnderHeight(0);*/
    }

    /**
     * 今日秒杀时间段
     */
    private void initGoodsTimeSection() {
        Api.getClient(HttpRequest.baseUrl_activity).goodsTimeSection().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<TimeSectionBean>>() {
                    @Override
                    public void onSuccess(List<TimeSectionBean> data) {
                        if(!ObjectUtils.isEmpty(data)) {
                            for (TimeSectionBean bean : data){
                                int status = bean.getStatus();//0:进行中 1:未开始 2:已结束
                                fragments.add(MiaoshaFragment.newInstance(bean.getId()));
                                switch (status) {
                                    case 0:
                                        timeList.add(bean.getName()+"/抢购中");
                                        break;
                                    case 1:
                                        timeList.add(bean.getName()+"/即将开始");
                                        break;
                                    case 2:
                                        timeList.add(bean.getName()+"/已结束");
                                        break;
                                }
                            }
                            cindicator.initDatas(fragments, timeList, getSupportFragmentManager());
                            cindicator.getIndicator().setItemUnderHeight(0);
                            cindicator.getIndicator().setTabUnderHeight(0);
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }
}
