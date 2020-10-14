package com.bee.user.ui.mine;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.login.ResetPasswordActivity;
import com.bee.user.utils.CommonUtil;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import butterknife.BindView;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function3;

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
