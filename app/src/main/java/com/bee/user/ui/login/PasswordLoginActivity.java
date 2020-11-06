package com.bee.user.ui.login;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.UserBean;
import com.bee.user.event.LoginEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.CommonWebActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.ClearEditText;
import com.bee.user.widget.MyPasswordView;
import com.google.gson.Gson;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

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
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/21  17：17
 * 描述：密码登录页面
 */
public class PasswordLoginActivity extends BaseActivity {

    @BindView(R.id.ed_user_phone)
    ClearEditText ed_user_phone;

    @BindView(R.id.ed_user_pass)
    MyPasswordView ed_user_pass;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    private boolean sigal = false;



    @OnClick({R.id.tv_mimalogin,R.id.tv_forgetmima,R.id.tv_agree,R.id.tv_xieyi_regist,R.id.tv_xieyi_yinsi})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_xieyi_regist:
                Intent intent1 = new Intent(PasswordLoginActivity.this, CommonWebActivity.class);
                intent1.putExtra("url", HttpRequest.xieyi_regist);
                startActivity(intent1);
                break;
            case R.id.tv_xieyi_yinsi:
                Intent intent2 = new Intent(PasswordLoginActivity.this, CommonWebActivity.class);
                intent2.putExtra("url", HttpRequest.xieyi_yinsi);
                startActivity(intent2);
                break;
            case R.id.tv_mimalogin:
                finish();
                break;
            case R.id.tv_forgetmima:
                startActivity(new Intent(PasswordLoginActivity.this, ResetPasswordActivity.class));
                break;
            case R.id.tv_agree:
                String phone = ed_user_phone.getText().toString();
                String pass = ed_user_pass.getEditTextView().getText().toString();


                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showSafeToast(PasswordLoginActivity.this, "请输入手机号");
                    return;
                }
                if (!CommonUtil.isMobileNoAll(phone)) {
                    ToastUtil.ToastShort(this, "请输入正确的手机号码");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    ToastUtil.showSafeToast(PasswordLoginActivity.this, "请输入密码");
                    return;
                }

                tv_agree.setEnabled(false);

                showLoadingDialog();

                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", pass);
                map.put("username", phone);

                Api.getClient().login_password(Api.getRequestBody(map))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>() {
                            @Override
                            public void onSuccess(String userBean) {
                                closeLoadingDialog();
                                tv_agree.setEnabled(true);
//                                SPUtils.geTinstance().setLoginCache(userBean);

                                EventBus.getDefault().post(new LoginEvent());
                                finish();
                            }

                            @Override
                            public void onFail(String fail) {
                                super.onFail(fail);
                                tv_agree.setEnabled(true);
                                closeLoadingDialog();
                            }
                        });

                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_login;
    }

    @Override
    public void initViews() {

        ed_user_phone.requestFocus();
        ed_user_phone.setSelection(ed_user_phone.length());
        tv_agree.setBackgroundResource(R.drawable.btn_gradient_grey_round);

        ed_user_phone.setOnClearClickListener(new ClearEditText.OnClearClickListener() {
            @Override
            public void onClearClick() {
                ed_user_pass.getEditTextView().setText("");
            }
        });

        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(ed_user_phone);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(ed_user_pass.getEditTextView());

        Observable.combineLatest(c1, c2, (BiFunction<CharSequence, CharSequence, Boolean>) (charSequence, charSequence2) -> {

            if (CommonUtil.isMobileNoAll(charSequence)) {
                if (!sigal) {
                    ed_user_pass.password_edit.requestFocus();
                    ed_user_pass.password_edit.setSelection(ed_user_pass.password_edit.length());
                    ed_user_pass.password_edit.post(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtil.showKeyBoard(PasswordLoginActivity.this);
                        }
                    });

                }

                sigal = true;
            } else {
                sigal = false;
            }
            return CommonUtil.isMobileNoAll(charSequence) && !TextUtils.isEmpty(charSequence2);
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
