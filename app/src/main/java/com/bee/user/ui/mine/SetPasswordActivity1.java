package com.bee.user.ui.mine;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.SendCodeView;
import com.blankj.utilcode.util.ObjectUtils;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import butterknife.Action;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  20：02
 * 描述：
 */
public class SetPasswordActivity1 extends BaseActivity {

    @BindView(R.id.ed_user_code)
    EditText ed_user_code;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    @BindView(R.id.code_text)
    SendCodeView code_text;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @OnClick({R.id.tv_agree})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_agree:
                String code = ed_user_code.getText().toString().trim();
                if(ObjectUtils.isEmpty(code)) {
                    return;
                }
                toCheckMsgCode(code);
                break;
        }
    }

    /**
     * 校验短信验证码
     * @param code
     */
    private void toCheckMsgCode(String code) {
        Api.getClient(HttpRequest.baseUrl_user).checkSmsCode(SPUtils.geTinstance().getUserInfo().phone,code).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Boolean>() {
                    @Override
                    public void onSuccess(Boolean s) {
                        if(s) {
                            Intent intent = new Intent(SetPasswordActivity1.this,SetPasswordActivity2.class);
                            intent.putExtra("msgCode",code);
                            intent.putExtra("phone",SPUtils.geTinstance().getUserInfo().phone);
                            startActivity(intent);
                        }else {
                            ToastUtil.ToastShort(SetPasswordActivity1.this, "短信验证码输入错误");
                        }

                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setpassword;
    }

    @Override
    public void initViews() {
        String phone = SPUtils.geTinstance().getUserInfo().phone;
        if(!ObjectUtils.isEmpty(phone)&&phone.length()>4) {
            String mobile = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
            tv_phone.setText(mobile);
        }
        code_text.initDatas(new SendCodeView.MyOnClickListener() {
            @Override
            public String onGetPhone() {

                return phone;
            }

            @Override
            public void onSuccess(String t) {

            }

            @Override
            public void onFailure(String t) {

            }
        });

        ed_user_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(TextUtils.isEmpty(charSequence)){
                    setButtonStatus(false);
                }else{
                    setButtonStatus(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setButtonStatus(false);
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
