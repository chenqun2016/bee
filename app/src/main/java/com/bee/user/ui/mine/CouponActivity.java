package com.bee.user.ui.mine;

import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/27  20：30
 * 描述：
 */
public class CouponActivity extends BaseActivity {



    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    String[] titles = new String[]{"在线充值", "充值卡/代金券"};


    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initViews() {

    }
}
