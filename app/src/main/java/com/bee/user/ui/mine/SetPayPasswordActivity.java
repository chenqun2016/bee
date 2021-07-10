package com.bee.user.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.UserBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.PayPassView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  15：03
 * 描述：
 */
public class SetPayPasswordActivity extends BaseActivity {
    @BindView(R.id.paypassview)
    PayPassView paypassview;

    private String msgCode = "";
    private String phone = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_setpaypassword;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        msgCode = intent.getStringExtra("msgCode");
        phone = intent.getStringExtra("phone");
        paypassview.setOnFinishInput(new PayPassView.OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                toSetPass(paypassview.getStrPassword());
            }

            @Override
            public void inputFirst() {

            }
        });
    }

    /**
     * 设置密码
     * @param payPassWord
     */
    private void toSetPass(String payPassWord) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("payPassword", payPassWord);
        map.put("smsCode", msgCode);
        if(Objects.deepEquals("Y", SPUtils.geTinstance().getUserInfo().payPasswordFlag)) {
            Api.getClient(HttpRequest.baseUrl_member).resetPayPassword(Api.getRequestBody(map))
                    .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object object) {
                            showEndDialog("支付密码重置成功");
                        }
                    });
        }else {
            Api.getClient(HttpRequest.baseUrl_member).setPayPassword(Api.getRequestBody(map))
                    .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object object) {
                            UserBean userInfo = SPUtils.geTinstance().getUserInfo();
                            userInfo.payPasswordFlag = "Y";
                            SPUtils.geTinstance().setUserBean(userInfo);
                            showEndDialog("支付密码设置成功");
                        }
                    });
        }

    }

    private Dialog systemDialog;
    private void showEndDialog(String inform) {
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(this, R.layout.dialog_set_end, null);
            TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
            tv_des.setText(inform);
            TextView tv_queding = (TextView) inflate.findViewById(R.id.btn_sure);
            tv_queding.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(SetPayPasswordActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity(intent);
                    finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }
}
