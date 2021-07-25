package com.bee.user.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 - @Description: 注销账号
 - @Author:  bixueyun
 - @Time:  2021/7/25 上午10:05
 */
public class CancelAccountActivity extends BaseActivity {


    public static void toCancelAccount(Activity activity) {
        Intent intent = new Intent(activity,CancelAccountActivity.class);
        activity.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_cancel_account;
    }

    @Override
    public void initViews() {

    }

    @OnClick({R.id.tv_agree})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_agree:
                ConfirmCancelAccountActivity.toConfirmCancelAccount(this);
                break;

        }
    }
}