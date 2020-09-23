package com.bee.user.ui.order;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.ui.adapter.OrderDetailAdapter;
import com.bee.user.ui.adapter.OrderingAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.xiadan.BeizhuActivity;
import com.bee.user.ui.xiadan.ChooseAddressActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  17：08
 * 描述：
 */
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        OrderDetailAdapter orderingAdapter = new OrderDetailAdapter();
        recyclerView.setAdapter(orderingAdapter);


        View head = View.inflate(this, R.layout.head_orderdetail, null);
        View foot = View.inflate(this, R.layout.foot_orderdetail, null);
        orderingAdapter.addHeaderView(head);
        orderingAdapter.addFooterView(foot);
        initHeadFootView(head,foot);

        ArrayList<StoreBean> beans = new ArrayList<>();
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        orderingAdapter.setNewInstance(beans);
    }



    private void initHeadFootView(View head, View foot) {


    }
}
