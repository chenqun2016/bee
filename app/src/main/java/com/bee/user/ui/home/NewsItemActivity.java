package com.bee.user.ui.home;

import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.bee.user.bean.NewsItemBean;
import com.bee.user.entity.NewsEntity;
import com.bee.user.entity.NewsItemEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  15：35
 * 描述：
 */
public class NewsItemActivity extends BaseActivity {
    @BindView(R.id.crecyclerview)
    CRecyclerView<NewsItemBean> crecyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void initViews() {
        crecyclerview.setView(NewsItemEntity.class).setCanLoadMore(false).start();


        ArrayList<NewsItemBean> beans = new ArrayList<NewsItemBean>();
        beans.add(new NewsItemBean());
        beans.add(new NewsItemBean());
        beans.add(new NewsItemBean());
        beans.add(new NewsItemBean());

        crecyclerview.getBaseAdapter().setList(beans);

    }
}
