package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.bee.user.R;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.PayPassView;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
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

    @BindView(R.id.tv_agree)
    TextView tv_agree;

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
        Api.getClient(HttpRequest.baseUrl_member).checkPayPassword(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        toJump(payPassWord);
                    }
                });
    }

    private void toJump(String payPassWord) {
        SetPayPasswordActivity2.toSetPassword(this,msgCode,phone,payPassWord);
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
