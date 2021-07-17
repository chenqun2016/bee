package com.bee.user.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.event.ReflushEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
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

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  20：23
 * 描述：
 */
public class SetPasswordActivity2 extends BaseActivity {
    @BindView(R.id.ed_seting_password)
    EditText ed_seting_password;

    @BindView(R.id.ed_seting_password_again)
    EditText ed_seting_password_again;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    private String msgCode = "";
    private String phone = "";
    private CountDownTimer mCountDownTimer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setpassword2;
    }

    @Override
    public void initViews() {
        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(ed_seting_password);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(ed_seting_password_again);

        Observable.combineLatest(c1, c2,(BiFunction<CharSequence,  CharSequence, Boolean>) (charSequence, charSequence2) -> {

            return !TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(charSequence2);

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
        Intent intent = getIntent();
        msgCode = intent.getStringExtra("msgCode");
        phone = intent.getStringExtra("phone");
    }


    @OnClick({R.id.tv_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_agree:
                String passWordOne = ed_seting_password.getText().toString().trim();
                String passWordTwo = ed_seting_password_again.getText().toString().trim();
                if(!passWordOne.equals(passWordTwo)) {
                    ToastUtil.ToastShort(this, "两次密码不一致，请重新设置");
                    return;
                }
                toResetPass(passWordOne);
                break;
        }
    }

    /**
     * 重置密码
     * @param passWordOne
     */
    private void toResetPass(String passWordOne) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", passWordOne);
        map.put("smsCode", msgCode);

        Api.getClient(HttpRequest.baseUrl_user).resetPassword(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String userBean) {
                        showEndDialog();
                    }
                });
    }

    /**
     * 跳转登录页
     */
    private void toJump() {
        if (null != systemDialog) {
            systemDialog.dismiss();
            if(mCountDownTimer !=null) {
                mCountDownTimer.cancel();
            }
        }
        SPUtils.geTinstance().setExitlogin();
        EventBus.getDefault().post(new ReflushEvent(ReflushEvent.TYPE_REFLUSH_EXIT_LOGIN));
        Intent intent =  new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
        finish();
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

    private Dialog systemDialog;
    private void showEndDialog() {
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(this, R.layout.dialog_login_password_tips, null);
            TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
            TextView tv_queding = (TextView) inflate.findViewById(R.id.btn_sure);
            mCountDownTimer = new CountDownTimer(4000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv_des.setText((int)millisUntilFinished/1000+"");
                }

                @Override
                public void onFinish() {
                    toJump();
                }
            };
            mCountDownTimer.start();
            tv_queding.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(mCountDownTimer!= null) {
                        mCountDownTimer.cancel();
                    }
                    toJump();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }

}
