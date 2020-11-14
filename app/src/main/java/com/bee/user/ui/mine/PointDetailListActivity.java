package com.bee.user.ui.mine;

import com.bee.user.R;
import com.bee.user.bean.PointDetailBen;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.entity.PointListEntity;
import com.bee.user.entity.TradeListEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/14  19：16
 * 描述：
 */
public class PointDetailListActivity extends BaseActivity {

    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;


    @Override
    public int getLayoutId() {
        return R.layout.activity_point_list;
    }

    @Override
    public void initViews() {
        crecyclerview.setView(PointListEntity.class);
        crecyclerview.setRow(20);
        crecyclerview.setCanLoadMore(true);
        crecyclerview.start();


        ArrayList<PointDetailBen> beans = new ArrayList<>();
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        beans.add(new PointDetailBen());
        crecyclerview.getBaseAdapter().setList(beans);
    }
}
