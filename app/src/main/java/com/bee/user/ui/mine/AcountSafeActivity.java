package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  19：52
 * 描述：
 */
public class AcountSafeActivity extends BaseActivity {

    @OnClick({R.id.tv_mimalogin_text,R.id.tv_zhifumima_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_mimalogin_text:
                startActivity(new Intent(this,SetPasswordActivity1.class));
                break;
            case R.id.tv_zhifumima_text:
                startActivity(new Intent(this,SetPayPasswordActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acountsafe;
    }

    @Override
    public void initViews() {

    }
}
