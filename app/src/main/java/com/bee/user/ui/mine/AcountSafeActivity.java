package com.bee.user.ui.mine;

import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.sputils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  19：52
 * 描述：
 */
public class AcountSafeActivity extends BaseActivity {
    @BindView(R.id.tv_mimalogin_text)
    TextView tv_mimalogin_text;

    @BindView(R.id.tv_zhifumima_text)
    TextView tv_zhifumima_text;
    @OnClick({R.id.tv_mimalogin_text,R.id.tv_zhifumima_text,R.id.tv_zhuxiao_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_mimalogin_text:
                SetPasswordActivity1.toSetPassword(this,"loginPass");
                break;
            case R.id.tv_zhifumima_text:
                SetPasswordActivity1.toSetPassword(this,"payPass");
                break;
            case R.id.tv_zhuxiao_text:
                CancelAccountActivity.toCancelAccount(this);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acountsafe;
    }

    @Override
    public void initViews() {
        if(SPUtils.geTinstance().hasPayPassword()) {
            tv_zhifumima_text.setText("已设置");
        }else {
            tv_zhifumima_text.setText("未设置");
        }
    }
}
