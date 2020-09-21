package com.bee.user.ui.home;

import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.bee.user.entity.NewsEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/21  20：45
 * 描述：
 */
public class NewsActivity extends BaseActivity {
    @BindView(R.id.crecyclerview)
    CRecyclerView<NewsBean> crecyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void initViews() {
        crecyclerview.setView(NewsEntity.class).setCanLoadMore(false).start();


        ArrayList<NewsBean> beans = new ArrayList<NewsBean>();
        beans.add(new NewsBean());
        beans.add(new NewsBean());
        beans.add(new NewsBean());
        beans.add(new NewsBean());

        crecyclerview.getBaseAdapter().setList(beans);

    }
}
