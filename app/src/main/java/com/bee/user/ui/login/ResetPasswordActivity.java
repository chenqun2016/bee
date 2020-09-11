package com.bee.user.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.UserBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.ClearEditText;
import com.bee.user.widget.MyPasswordView;
import com.bee.user.widget.SendCodeView;
import com.google.gson.Gson;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/21  20：52
 * 描述：忘记密码重置密码页面
 */
public class ResetPasswordActivity extends BaseActivity {
    @BindView(R.id.ed_user_phone)
    ClearEditText ed_user_phone;

    @BindView(R.id.ed_user_code)
    EditText ed_user_code;
    @BindView(R.id.ed_user_pass)
    MyPasswordView ed_user_pass;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    @BindView(R.id.code_text)
    SendCodeView code_text;

    private boolean sigal = false;

    @OnClick({R.id.tv_agree})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_agree:
                String phone = ed_user_phone.getText().toString();
                String code = ed_user_code.getText().toString();
                String pass = ed_user_pass.getEditTextView().getText().toString();

                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", pass);
                map.put("smsCode", code);

                Api.getClient().resetPassword(Api.getRequestBody(map))
                        .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<UserBean>() {
                            @Override
                            public void onSuccess(UserBean userBean) {
                                SPUtils.geTinstance().setLoginCache(userBean);
                                finish();
                            }
                        });

                break;

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initViews() {
        code_text.initDatas(new SendCodeView.MyOnClickListener() {
            @Override
            public String onGetPhone() {
                if(null == ed_user_phone && null == ed_user_phone.getText()){
                    return "";
                }
                return ed_user_phone.getText().toString();
            }

            @Override
            public void onSuccess(String t) {

            }

            @Override
            public void onFailure(String t) {

            }
        });

        ed_user_phone.setOnClearClickListener(new ClearEditText.OnClearClickListener() {
            @Override
            public void onClearClick() {
                ed_user_code.setText("");
            }
        });
        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(ed_user_phone);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(ed_user_code);
        InitialValueObservable<CharSequence> c3 = RxTextView.textChanges(ed_user_pass.getEditTextView());

        Observable.combineLatest(c1, c2,c3, (Function3<CharSequence, CharSequence, CharSequence, Boolean>) (charSequence, charSequence2,charSequence3) -> {

            boolean isMobileNoAll = CommonUtil.isMobileNoAll(charSequence);

            if (isMobileNoAll) {
                if (!sigal) {
                    ed_user_code.requestFocus();
                    ed_user_code.setSelection(ed_user_code.length());
                    ed_user_code.post(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtil.showKeyBoard(ResetPasswordActivity.this);
                        }
                    });

                }

                sigal = true;
            } else {
                sigal = false;
            }

            if (!code_text.mIsWorking) {
                if (isMobileNoAll) {
                    code_text.setItemClickable(true);
                    code_text.setItemTextColor(getResources().getColor(R.color.color_3e7dfb));
                } else {
                    code_text.setItemClickable(false);
                    code_text.setItemTextColor(getResources().getColor(R.color.color_ccc));
                }
            }


            return CommonUtil.isMobileNoAll(charSequence)
                    && !TextUtils.isEmpty(charSequence2)
                    && !TextUtils.isEmpty(charSequence3);
        }).subscribe(new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                setButtonStatus(aBoolean);
            }
        });
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
