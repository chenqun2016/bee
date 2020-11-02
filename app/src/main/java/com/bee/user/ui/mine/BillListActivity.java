package com.bee.user.ui.mine;

import com.bee.user.R;
import com.bee.user.bean.BillBean;
import com.bee.user.entity.BillListEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/02  21：28
 * 描述：
 */
public class BillListActivity extends BaseActivity {

    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_list;
    }

    @Override
    public void initViews() {
        crecyclerview.setView(BillListEntity.class);
        ArrayList<BillBean> beans = new ArrayList<>();
        beans.add(new BillBean());
        beans.add(new BillBean());
        beans.add(new BillBean());
        beans.add(new BillBean());


        crecyclerview.getBaseAdapter().setList(beans);
        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd(true);
    }
}
