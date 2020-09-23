package com.bee.user.ui.xiadan;

import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.YouhuiquanBean;
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

    @Override
    public void initViews() {
        toolbar_title.setText("选择红包/优惠券");

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


      List<YouhuiquanBean> lists = new ArrayList<>();
        lists.add(new YouhuiquanBean());
        lists.add(new YouhuiquanBean());
        lists.add(new YouhuiquanBean());
        lists.add(new YouhuiquanBean());
        lists.add(new YouhuiquanBean());
        crecyclerview.getBaseAdapter().setList(lists);


        List<YouhuiquanBean> lists2 = new ArrayList<>();
        YouhuiquanBean youhuiquanBean = new YouhuiquanBean();
        youhuiquanBean.viewType = YouhuiquanBean.type2;
        lists2.add(youhuiquanBean);

        lists2.add(new YouhuiquanBean(1));
        lists2.add(new YouhuiquanBean(1));

        crecyclerview.getBaseAdapter().addData(lists2);

        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd(true);
    }
}
