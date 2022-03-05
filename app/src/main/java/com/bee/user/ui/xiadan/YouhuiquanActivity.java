package com.bee.user.ui.xiadan;

import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.entity.YouhuiquanEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CMultiRecyclerView;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/05  21：01
 * 描述：
 */
public class YouhuiquanActivity extends BaseActivity {

    @BindView(R.id.crecyclerview)
    CMultiRecyclerView crecyclerview;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_youhuiquan;
    }

    List<OrderingConfirmBean.OrderingCouponBean> datas;
    @Override
    public void initViews() {
        toolbar_title.setText("选择红包/优惠券");

        datas = (List<OrderingConfirmBean.OrderingCouponBean>) getIntent().getSerializableExtra("datas");
        crecyclerview.setView(YouhuiquanEntity.class);
        crecyclerview.setCanLoadMore(false);
        crecyclerview.setOnRequestListener(new CRecyclerView.OnRequestListener() {
            @Override
            public void onRequestEnd() {

            }

            @Override
            public void onNoData() {

            }

            @Override
            public void onNoMoreDatas() {

            }

            @Override
            public void onRefreshStart() {

            }
        });
//        crecyclerview.start();

        List<OrderingConfirmBean.OrderingCouponBean> lists = new ArrayList<>();
        List<OrderingConfirmBean.OrderingCouponBean> lists2 = new ArrayList<>();

        //TODO 需要重新排序？
        for(int i=0;i<datas.size();i++){
            OrderingConfirmBean.OrderingCouponBean orderingCouponBean = datas.get(i);
            if(orderingCouponBean.status == 0){
                lists.add(orderingCouponBean);
            }else{
                lists2.add(orderingCouponBean);
            }
        }
        crecyclerview.getBaseAdapter().setList(lists);
        crecyclerview.getBaseAdapter().addData(lists2);
        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd(true);
    }
}
