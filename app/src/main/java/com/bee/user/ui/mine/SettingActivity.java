package com.bee.user.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.event.ExitloginEvent;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.huaxiafinance.www.crecyclerview.crecyclerView.EventBusUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  19：29
 * 描述：
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_quit)
    TextView tv_quit;

    @OnClick({R.id.tv_safe,R.id.tv_quit,R.id.tv_about})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_safe:
                if(!ObjectUtils.isEmpty(SPUtils.geTinstance().getUserInfo())) {
                    startActivity(new Intent(this,AcountSafeActivity.class));
                }else {
                    ToastUtil.ToastShort(this, "您还未登陆，请先登录");
                }

                break;
            case R.id.tv_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.tv_quit:
                showExitDialog();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initViews() {
        if(!ObjectUtils.isEmpty(SPUtils.geTinstance().getUserInfo())) {
            tv_quit.setVisibility(View.VISIBLE);
        }else {
            tv_quit.setVisibility(View.GONE);
        }
    }

    private Dialog systemDialog;
    private void showExitDialog() {
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(SettingActivity.this, R.layout.dialog_hint3, null);
            TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
            tv_des.setText("确定退出登录?");
            TextView tv_quxiao = (TextView) inflate.findViewById(R.id.btn_cancel);
            TextView tv_queding = (TextView) inflate.findViewById(R.id.btn_sure);

            tv_quxiao.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }

                }
            });
            tv_queding.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SPUtils.geTinstance().setExitlogin();
                    EventBus.getDefault().post(new ExitloginEvent());
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }
                    SettingActivity.this.finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }
}
