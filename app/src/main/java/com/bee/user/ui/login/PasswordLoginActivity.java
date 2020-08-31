package com.bee.user.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/21  17：17
 * 描述：密码登录页面
 */
public class PasswordLoginActivity extends BaseActivity {



    @Override
    public int getLayoutId() {
        return R.layout.activity_password_login;
    }

    @Override
    public void initViews() {

        findViewById(R.id.tv_mimalogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.tv_forgetmima).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PasswordLoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }
}
