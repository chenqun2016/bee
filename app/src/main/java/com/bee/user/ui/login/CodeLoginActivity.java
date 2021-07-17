package com.bee.user.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.event.CloseEvent;
import com.bee.user.event.ReflushEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.CommonWebActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.ClearEditText;
import com.bee.user.widget.SendCodeView;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/20  22：01
 * 描述： 手机验证码登录页面
 */
public class CodeLoginActivity extends BaseActivity {

    @BindView(R.id.ed_user_phone)
    ClearEditText ed_user_phone;

    @BindView(R.id.ed_user_code)
    EditText ed_user_code;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    @BindView(R.id.code_text)
    SendCodeView code_text;

    private boolean sigal = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_code_login;
    }

    @OnClick({R.id.tv_agree})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_agree:
                String phone = ed_user_phone.getText().toString();
                String code = ed_user_code.getText().toString();

                showLoadingDialog();

                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("smsCode", code);

                Api.getClient(HttpRequest.baseUrl_user).login_code(Api.getRequestBody(map)).
                        subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>() {
                            @Override
                            public void onSuccess(String token) {
                                closeLoadingDialog();

                                EventBus.getDefault().post(new ReflushEvent(ReflushEvent.TYPE_REFLUSH_LOGIN,token));
                                finish();
                            }

                            @Override
                            public void onFail(String fail) {
                                super.onFail(fail);
                                closeLoadingDialog();
                            }
                        });

                break;

        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if(null != code_text){
            code_text.onDestory();
        }
        super.onDestroy();
    }
    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        findViewById(R.id.tv_mimalogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CodeLoginActivity.this, PasswordLoginActivity.class));
            }
        });

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


        TextView tv_xieyi = findViewById(R.id.tv_xieyi);
        try {
            String str = "未注册手机号码登录将自动生成账号，且代表您已阅读并同意";
            String str1 = "《用户服务协议》";
            String str2 = "和";
            String str3 = "《隐私政策》";
            SpannableString msp = new SpannableString(str+str1 + str2 + str3);

            msp.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(CodeLoginActivity.this, CommonWebActivity.class);
                    intent.putExtra("url", HttpRequest.xieyi_regist);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, str.length(), str.length()+str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            msp.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CodeLoginActivity.this, CommonWebActivity.class);
                    intent.putExtra("url", HttpRequest.xieyi_yinsi);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, str.length()+str1.length()+str2.length(), msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_3e7dfb)), str.length(), str.length()+str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_3e7dfb)), str.length()+str1.length()+str2.length(), msp.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            tv_xieyi.setMovementMethod(LinkMovementMethod.getInstance());
            tv_xieyi.setHighlightColor(Color.TRANSPARENT);
            tv_xieyi.setText(msp);
        } catch (Exception e) {
            LogUtil.d(e.getMessage());
        }



        ed_user_phone.setOnClearClickListener(new ClearEditText.OnClearClickListener() {
            @Override
            public void onClearClick() {
                ed_user_code.setText("");
            }
        });

        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(ed_user_phone);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(ed_user_code);

        Observable.combineLatest(c1, c2, (BiFunction<CharSequence, CharSequence, Boolean>) (charSequence, charSequence2) -> {

            boolean isMobileNoAll = CommonUtil.isMobileNoAll(charSequence);

            if (isMobileNoAll) {
                if (!sigal) {
                    ed_user_code.requestFocus();
                    ed_user_code.setSelection(ed_user_code.length());
                    ed_user_code.post(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtil.showKeyBoard(CodeLoginActivity.this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        if(event.type == CloseEvent.TYPE_LOGIN){
            finish();
        }
    }
}
