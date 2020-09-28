package com.bee.user.ui.order;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.ui.adapter.OrderCommentFoodAdapter;
import com.bee.user.ui.adapter.TagsAdapter;
import com.bee.user.ui.adapter.TagsOrderCommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  16：07
 * 描述：
 */
public class OrderCommentActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.tags)
    FlowTagLayout tags;


    @BindView(R.id.tv_tijiao)
    TextView tv_tijiao;

    @OnClick({R.id.tv_tijiao})
    public void onClick(){
        startActivity(new Intent(this,OrderCommentStatusActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment;
    }

    @Override
    public void initViews() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        OrderCommentFoodAdapter orderCommentFoodAdapter = new OrderCommentFoodAdapter();
        recyclerview.setAdapter(orderCommentFoodAdapter);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("王牌鸭腿饭");
        strings.add("金牌牛肉面");
        orderCommentFoodAdapter.setNewInstance(strings);





        TagsOrderCommentAdapter<String> tagsAdapter = new TagsOrderCommentAdapter<>(this);

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {


            }
        });
        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {

            }
        });
        List<String> dataSource = new ArrayList<>();
        dataSource.add("不送上楼");
        dataSource.add("送餐速度");
        dataSource.add("食品凉了");
        dataSource.add("未穿制服");
        dataSource.add("配送慢");
        dataSource.add("写评价");
        tagsAdapter.onlyAddAll(dataSource);
    }
}
