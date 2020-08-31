package com.bee.user.ui.nearby;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.LogUtil;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  20：48
 * 描述：
 */
public class FoodActivity extends BaseActivity {
    @BindView(R.id.app_barbar)
    AppBarLayout app_barbar;

    @BindView(R.id.background)
    View background;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;





    @Override
    public int getLayoutId() {
        return R.layout.activity_food;
    }

    @Override
    public void initViews() {
        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset=="+verticalOffset+"--scrollRangle=="+scrollRangle);

                if (verticalOffset == 0) {
                    background.setAlpha(0);
                } else if(Math.abs(verticalOffset) >= scrollRangle){
                    background.setAlpha(1);
                }else{
                    //保留一位小数
                    float alpha =( Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    background.setAlpha(alpha);
                }


            }
        });



        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        CommentAdapter  mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);


        View inflate = View.inflate(this, R.layout.head_activity_food_comment, null);
        TextView tv_food_comment = inflate.findViewById(R.id.tv_food_comment);
        tv_food_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodActivity.this,CommentActivity.class));
            }
        });
        mAdapter.addHeaderView(inflate);

        List<CommentBean> sampleData = CommentFragment.getSampleData(5);
        mAdapter.setList(sampleData);
    }
}
