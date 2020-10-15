package com.bee.user.ui.trade;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  17：15
 * 描述：
 */
public class MiLiActivity extends BaseActivity {
    @BindView(R.id.statusheight)
    View statusheight;




    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mili;
    }

    @Override
    public void initViews() {
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);
    }
}
