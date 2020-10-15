package com.bee.user.ui.xiadan;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.event.CloseEvent;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.PayPassView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/13  19：33
 * 描述：
 */
public class PayActivity extends BaseActivity {

    @OnClick({R.id.tv_pay})
    public void onClick(View view){
        showPayDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showPayDialog(){
        BaseDialog bottomSheetDialog = new BaseDialog(this){
            @Override
            protected int provideContentViewId() {
                return R.layout.dialog_pay;
            }

            @Override
            protected void initViews(BaseDialog dialog) {
                dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(null != dialog && dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });

                PayPassView paypassview = dialog.findViewById(R.id.paypassview);
                paypassview.setOnFinishInput(new PayPassView.OnPasswordInputFinish() {
                    @Override
                    public void inputFinish() {
                        startActivity(new Intent(PayActivity.this,PayStatusActivity.class));
                    }

                    @Override
                    public void inputFirst() {

                    }
                });


            }
        };
        bottomSheetDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        finish();
    }
}
