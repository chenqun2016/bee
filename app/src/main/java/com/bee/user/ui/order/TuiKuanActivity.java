package com.bee.user.ui.order;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.ImageBean;
import com.bee.user.bean.TraceBean;
import com.bee.user.ui.adapter.CommentImagesAdapter;
import com.bee.user.ui.adapter.OrderFoodAdapter;
import com.bee.user.ui.adapter.OrderTraceAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.nearby.ImagesActivity;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/27  18：59
 * 描述：
 */
public class TuiKuanActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview_trace;

    @BindView(R.id.recyclerview_food)
    RecyclerView recyclerview_food;

    @BindView(R.id.images)
    RecyclerView images;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tuikuan;
    }

    @Override
    public void initViews() {

        CommonUtil.initTraceAdapter(recyclerview_trace);

        CommonUtil.initOrderFoodAdapter(recyclerview_food);

        CommonUtil.initCommentAdapter(images);

    }
}
