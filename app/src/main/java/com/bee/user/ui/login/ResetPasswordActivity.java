package com.bee.user.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/21  20：52
 * 描述：忘记密码重置密码页面
 */
public class ResetPasswordActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);
    }
}
