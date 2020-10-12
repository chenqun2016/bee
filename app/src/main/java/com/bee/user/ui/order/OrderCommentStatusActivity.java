package com.bee.user.ui.order;

import android.content.Intent;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/28  21：04
 * 描述：
 */
public class OrderCommentStatusActivity extends BaseActivity {

    @OnClick(R.id.btn_2)
    public void onClick(){
        startActivity(new Intent(this,OrderActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment_status;
    }

    @Override
    public void initViews() {

    }
}
