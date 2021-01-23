package com.bee.user.ui.mine.coupon;

import com.bee.user.R;
import com.bee.user.bean.YouhuiquanBean;
import com.bee.user.entity.CouponEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/23  15：24
 * 描述：
 */
public class UnuseCouponActivity extends BaseActivity {
    @BindView(R.id.crecyclerview)
     CRecyclerView crecyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_unuse_coupon;
    }

    @Override
    public void initViews() {

        crecyclerview.setView(CouponEntity.class);
//        crecyclerview.start();


        List<YouhuiquanBean> lists = new ArrayList<>();
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        lists.add(new YouhuiquanBean(1));
        crecyclerview.getBaseAdapter().setList(lists);
        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd(true);
    }
}
