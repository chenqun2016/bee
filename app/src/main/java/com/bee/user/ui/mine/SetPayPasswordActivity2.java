package com.bee.user.ui.mine;

import android.app.Activity;
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
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.PayPassView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 - @Description: 设置交易密码确认页面
 - @Author:  bixueyun
 - @Time:  2021/7/10 下午4:56
 */
public class SetPayPasswordActivity2 extends BaseActivity {
    @BindView(R.id.paypassview)
    PayPassView paypassview;

    @BindView(R.id.tv_setpaypassword_code)
    TextView tv_setpaypassword_code;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    private String msgCode = "";
    private String phone = "";
    private String payOnePassword = "";

    public static void toSetPassword(Activity activity, String msgCode,String phone,String payOnePassword) {
        Intent intent = new Intent(activity,SetPayPasswordActivity2.class);
        intent.putExtra("msgCode",msgCode);
        intent.putExtra("phone",phone);
        intent.putExtra("payOnePassword",payOnePassword);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setpaypassword;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        msgCode = intent.getStringExtra("msgCode");
        phone = intent.getStringExtra("phone");
        payOnePassword = intent.getStringExtra("payOnePassword");
        tv_setpaypassword_code.setText("请再次填写以确认");
        setButtonStatus(false);
        paypassview.setOnFinishInput(new PayPassView.OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                setButtonStatus(true);
            }

            @Override
            public void inputFirst() {

            }

            @Override
            public void inputNoFull() {
                setButtonStatus(false);
            }
        });
    }

    @OnClick({R.id.tv_agree})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_agree:
                String strPassword = paypassview.getStrPassword();
                if(!Objects.deepEquals(payOnePassword,strPassword)) {
                    ToastUtil.ToastShort(SetPayPasswordActivity2.this, "两次密码不一致，请重新设置");
                    return;
                }
                toSetPass(paypassview.getStrPassword());
               break;
        }
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
                    Intent intent =  new Intent(SetPayPasswordActivity2.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity(intent);
                    finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }

    private void setButtonStatus(Boolean aBoolean) {
        if (aBoolean) {
            tv_agree.setEnabled(true);
            tv_agree.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
        } else {
            tv_agree.setEnabled(false);
            tv_agree.setBackgroundResource(R.drawable.btn_gradient_grey_round);
        }
    }
}
