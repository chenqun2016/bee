package com.bee.user.ui.order;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/26  16：01
 * 描述：
 */
public class OrderingMapActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton iv_back;

    @BindView(R.id.view1)
    LinearLayout view1;

    @BindView(R.id.vie2)
    RelativeLayout vie2;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ordering_map;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        switch (type) {
            case Constants.TYPE_PAY_WAITE://等待支付
                vie2.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);
                break;
            case Constants.TYPE_READY://商家正在备货
            case Constants.TYPE_PEISONG://商品配送中
                vie2.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                break;
            case Constants.TYPE_COMPLETE://订单已送达
                break;
            case Constants.TYPE_CANCELED://订单已取消
                break;
            case Constants.TYPE_TUIKUAN://退款中
                break;
            default:
                break;
        }


        int statusBarHeight = ImmersionBar.getStatusBarHeight(this);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) iv_back.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        iv_back.setLayoutParams(layoutParams);
    }
}
