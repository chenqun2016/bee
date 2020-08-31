package com.bee.user.ui.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.cindicatorview.CIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MiaoshaFragment.newInstance(0));
        fragments.add(MiaoshaFragment.newInstance(1));
        fragments.add(MiaoshaFragment.newInstance(2));
        fragments.add(MiaoshaFragment.newInstance(3));
        cindicator.initDatas(fragments, str, getSupportFragmentManager());
        cindicator.getIndicator().setItemUnderHeight(0);
        cindicator.getIndicator().setTabUnderHeight(0);
    }
}
