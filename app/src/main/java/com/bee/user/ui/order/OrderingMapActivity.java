package com.bee.user.ui.order;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.bean.OrderGridviewItemBean;
import com.bee.user.ui.adapter.OrderGridviewItemAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.widget.MyGridView;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

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


    @BindView(R.id.gridview_3)
    MyGridView gridview_3;
    @BindView(R.id.gridview_beihuo)
    MyGridView gridview_beihuo;

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
                setWaitePayGridview();
                break;
            case Constants.TYPE_READY://商家正在备货
            case Constants.TYPE_PEISONG://商品配送中
                vie2.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                setBeihuoGridview();
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

    private void setWaitePayGridview() {

        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order,R.drawable.icon_quxiaodingdan,"取消订单",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop,R.drawable.icon_lianxishangjia,"联系商家",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_pay_now,R.drawable.icon_lijizhifu,"立即支付",R.color.color_FF6200));

        setGridviewAdapter(gridview_3,beans);


    }
    private void setBeihuoGridview() {


        ArrayList<OrderGridviewItemBean> beans = new ArrayList<>();
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cancel_Order_beihuo,R.drawable.icon_quxiaodingdan,"取消订单",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_cuidan,R.drawable.icon_cuidan,"催单",R.color.color_ccc));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_change_order,R.drawable.icon_gaidingdanxinxi,"改订单信息",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_rider,R.drawable.icon_lianxiqishou,"联系骑手",R.color.color_3B3838));
        beans.add(new OrderGridviewItemBean(OrderGridviewItemBean.TYPE_contact_shop,R.drawable.icon_lianxishangjia,"联系商家",R.color.color_3B3838));

        setGridviewAdapter(gridview_beihuo,beans);


    }
    private void setGridviewAdapter(MyGridView gridview_tuikuan, ArrayList<OrderGridviewItemBean> beans) {
        OrderGridviewItemAdapter adapter = new OrderGridviewItemAdapter(this,beans);
        gridview_tuikuan.setAdapter(adapter);
        gridview_tuikuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommonUtil.performOrderGridviewClick(OrderingMapActivity.this,adapter,i);
            }
        });
    }


}
