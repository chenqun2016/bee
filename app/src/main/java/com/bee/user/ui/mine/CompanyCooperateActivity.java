package com.bee.user.ui.mine;

import android.content.Intent;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/27  19：31
 * 描述：
 */
public class CompanyCooperateActivity extends BaseActivity {

    @OnClick(R.id.tv_shengqing)
    public void onClick(){
        Intent intent = new Intent(this,CompanyOrderingActivity.class);
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_cooperate;
    }

    @Override
    public void initViews() {

    }
}
