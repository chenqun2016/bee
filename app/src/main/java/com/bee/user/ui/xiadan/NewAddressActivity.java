package com.bee.user.ui.xiadan;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.RadioGroupPlus;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function4;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/17  20：39
 * 描述：
 */
public class NewAddressActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.rgp_sex)
    RadioGroupPlus rgp_sex;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_dizhi_text)
    TextView tv_dizhi_text;
    @BindView(R.id.tv_menpai_text)
    TextView tv_menpai_text;
    @BindView(R.id.rgp_tags)
    RadioGroupPlus rgp_tags;
    @BindView(R.id.tv_sure)
    TextView tv_sure;

    @OnClick({R.id.tv_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_sure:
                finish();
                break;

        }
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_address;
    }

    @Override
    public void initViews() {
        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(tv_name);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(tv_phone);
        InitialValueObservable<CharSequence> c3 = RxTextView.textChanges(tv_dizhi_text);
        InitialValueObservable<CharSequence> c4 = RxTextView.textChanges(tv_menpai_text);
        rgp_tags.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {
                checkStatus();
            }
        });
        rgp_sex.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {
                checkStatus();
            }
        });

        Observable.combineLatest(c1, c2,c3,c4, ( Function4<CharSequence, CharSequence,CharSequence, CharSequence, Boolean>) (charSequence, charSequence2,charSequence3, charSequence4) -> {

            return !TextUtils.isEmpty(charSequence) &&
                    !TextUtils.isEmpty(charSequence2) &&
                    !TextUtils.isEmpty(charSequence3) &&
                    !TextUtils.isEmpty(charSequence4) &&
                    rgp_tags.getCheckedRadioButtonId() != -1 &&
                    rgp_sex.getCheckedRadioButtonId() != -1;

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


    private void checkStatus() {
        setButtonStatus(!TextUtils.isEmpty(tv_name.getText().toString()) &&
                !TextUtils.isEmpty(tv_phone.getText().toString()) &&
                !TextUtils.isEmpty(tv_dizhi_text.getText().toString()) &&
                !TextUtils.isEmpty(tv_menpai_text.getText().toString()) &&
                rgp_tags.getCheckedRadioButtonId() != -1 &&
                rgp_sex.getCheckedRadioButtonId() != -1);

    }

    private void setButtonStatus(Boolean aBoolean) {
        if (aBoolean) {
            tv_sure.setEnabled(true);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
        } else {
            tv_sure.setEnabled(false);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_grey_round);
        }
    }
}
