package com.bee.user.ui.nearby;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.ui.base.activity.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  21：33
 * 描述：
 */
public class CommentActivity extends BaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void initViews() {

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        CommentAdapter  mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

        List<CommentBean> sampleData = CommentFragment.getSampleData(5);
        mAdapter.setList(sampleData);
    }
}
