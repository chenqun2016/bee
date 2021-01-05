package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  19：29
 * 描述：
 */
public class SettingActivity extends BaseActivity {

    @OnClick({R.id.tv_safe,R.id.tv_about})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_safe:
                startActivity(new Intent(this,AcountSafeActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initViews() {

    }
}
