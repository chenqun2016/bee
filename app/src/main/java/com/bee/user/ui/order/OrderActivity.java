package com.bee.user.ui.order;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.adapter.OrderAdapter;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  21：13
 * 描述：
 */
public class OrderActivity extends BaseActivity {

    @BindView(R.id.statusheight)
    View statusheight;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @OnClick({R.id.tv_confirm})
    public void onClick(View view){
        startActivity(new Intent(this,PayActivity.class));
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initViews() {
        toolbar_title.setText("确认订单");

        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        OrderAdapter orderAdapter = new OrderAdapter();
        recyclerView.setAdapter(orderAdapter);


        View head = View.inflate(this, R.layout.head_order, null);
        View foot = View.inflate(this, R.layout.foot_order, null);
        orderAdapter.addHeaderView(head);
        orderAdapter.addFooterView(foot);
        initHeadFootView(head,foot);

        ArrayList<StoreBean> beans = new ArrayList<>();
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        orderAdapter.setNewInstance(beans);

    }

    private void initHeadFootView(View head, View foot) {
        TextView tv_time_value = head.findViewById(R.id.tv_time_value);
        tv_time_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseTimeDialog();
            }
        });

        ImageView imageview = foot.findViewById(R.id.imageview);
        Picasso.with(this).
                load(R.drawable.banner).
                fit().
                transform(new PicassoRoundTransform(DisplayUtil.dip2px(this,10),0, PicassoRoundTransform.CornerType.ALL)).
                into(imageview);

        foot.findViewById(R.id.ll_beizhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OrderActivity.this,BeizhuActivity.class));
            }
        });

    }

    private void showChooseTimeDialog() {
        BaseDialog baseDialog = new BaseDialog(this) {
            @Override
            protected int provideContentViewId() {
                return R.layout.dialog_order_choose_time;
            }

            @Override
            protected void initViews(BaseDialog dialog) {

            }
        };
        baseDialog.show();
    }
}
