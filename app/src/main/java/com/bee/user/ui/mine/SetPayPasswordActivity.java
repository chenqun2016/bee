package com.bee.user.ui.mine;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.ToastUtil;
import com.bee.user.widget.PayPassView;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  15：03
 * 描述：
 */
public class SetPayPasswordActivity extends BaseActivity {
    @BindView(R.id.paypassview)
    PayPassView paypassview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setpaypassword;
    }

    @Override
    public void initViews() {
        paypassview.setOnFinishInput(new PayPassView.OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                ToastUtil.ToastShort(SetPayPasswordActivity.this,"设置成功");
            }

            @Override
            public void inputFirst() {

            }
        });
    }
}
