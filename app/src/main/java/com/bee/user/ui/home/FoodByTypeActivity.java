package com.bee.user.ui.home;

import android.content.Intent;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.HomeBean;
import com.bee.user.ui.base.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/01  21：52
 * 描述：
 */
public class FoodByTypeActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
     RecyclerView recyclerview;

    @BindView(R.id.toolbar_title)
     TextView toolbar_title;
    @Override
    public int getLayoutId() {
        return R.layout.activity_food_by_type;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int type = intent.getIntExtra("type", 0);
        toolbar_title.setText(title+"");

        HomeAdapter homeAdapter = new HomeAdapter();
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.setAdapter(homeAdapter);

        ArrayList<HomeBean> homeBeans = new ArrayList<>();
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeAdapter.setNewInstance(homeBeans);
    }
}
