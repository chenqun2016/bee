package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.event.UserInfoItemEvent;
import com.bee.user.ui.base.activity.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/06  14：38
 * 描述：
 */
public class UserInfoItemActivity extends BaseActivity {

    private int  type;
    @BindView(R.id.tv_3)
    EditText tv_3;

    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;

    @OnClick(R.id.tv_sure)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_sure:
                UserInfoItemEvent userInfoItemEvent = new UserInfoItemEvent(type,tv_3.getText().toString());
                EventBus.getDefault().post(userInfoItemEvent);
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo_item;
    }

    @Override
    public void initViews() {

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);

        String str1 = intent.getStringExtra("str1");
        tv_1.setText(str1);
        String str2 = intent.getStringExtra("str2");
        tv_2.setText(str2);
        String str3 = intent.getStringExtra("str3");
        tv_3.setText(str3);
    }
}
