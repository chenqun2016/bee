package com.bee.user.ui.mine.coupon;

import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/23  15：24
 * 描述：
 */
public class UnuseCouponActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FrameLayout fl_content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_unuse_coupon;
    }

    @Override
    public void initViews() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content,CouponFragment.newInstance(0,2))
                .commitAllowingStateLoss();
    }
}
