package com.bee.user.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.search.SearchCityActivity;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.ToastUtil;
import com.bee.user.widget.RadioGroupPlus;
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
import io.reactivex.rxjava3.functions.Function5;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.ui.search.SearchCityActivity.REQUEST_CODE;
import static com.bee.user.ui.search.SearchCityActivity.RESULT_CODE;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/26  20：05
 * 描述：
 */
public class CompanyOrderingActivity extends BaseActivity {
    @BindView(R.id.tv_qiye_text)
    EditText tv_qiye_text;
    @BindView(R.id.tv_chengshi_text)
    TextView tv_chengshi_text;
    @BindView(R.id.tv_guimo_text)
    EditText tv_guimo_text;

    @BindView(R.id.tv_lianxiren_text)
    EditText tv_lianxiren_text;
    @BindView(R.id.rgp_sex)
    RadioGroupPlus rgp_sex;
    @BindView(R.id.tv_phone_text)
    EditText tv_phone_text;

    @BindView(R.id.tv_agree)
    TextView tv_agree;

    private String cityCode = "";
    private int sexId = 0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_company_ordering;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setmAdjustView(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        rgp_sex.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {
                if(checkedId == R.id.rb_1) {
                    sexId = 2;
                }else if(checkedId == R.id.rb_2) {
                    sexId = 1;
                }
                String string1 = tv_qiye_text.getText().toString();
                String string2 = tv_chengshi_text.getText().toString();
                String string3 = tv_guimo_text.getText().toString();
                String string4 = tv_lianxiren_text.getText().toString();
                String string5 = tv_phone_text.getText().toString();

                if(!TextUtils.isEmpty(string1)
                        && !TextUtils.isEmpty(string2)
                        && !TextUtils.isEmpty(string3)
                        && !TextUtils.isEmpty(string4)
                        && !TextUtils.isEmpty(string5)){
                    setButtonStatus(true);
                }else{

                    setButtonStatus(false);
                }
            }
        });

        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(tv_qiye_text);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(tv_chengshi_text);
        InitialValueObservable<CharSequence> c3 = RxTextView.textChanges(tv_guimo_text);
        InitialValueObservable<CharSequence> c4 = RxTextView.textChanges(tv_lianxiren_text);
        InitialValueObservable<CharSequence> c5 = RxTextView.textChanges(tv_phone_text);

        Observable.combineLatest(c1, c2,c3,c4,c5,
                (Function5<CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, Boolean>)
                (charSequence, charSequence2, charSequence3, charSequence4, charSequence5) -> {

                    LogUtil.d("CompanyOrderingActivity",rgp_sex.getCheckedRadioButtonId()+"");

            return !TextUtils.isEmpty(charSequence)
                    && !TextUtils.isEmpty(charSequence2)
                    && !TextUtils.isEmpty(charSequence3)
                    && !TextUtils.isEmpty(charSequence4)
                    && !TextUtils.isEmpty(charSequence5)
                    && -1 != rgp_sex.getCheckedRadioButtonId();
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

    @OnClick({R.id.tv_chengshi_text, R.id.tv_agree})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_chengshi_text:
                startActivityForResult(new Intent(this, SearchCityActivity.class),REQUEST_CODE);
                break;
            case R.id.tv_agree:
                toSubmit();
                break;
        }
    }

    /**
     * 企业订餐提交
     */
    private void toSubmit() {
        Map map = new HashMap<>();
        map.put("cityId", Integer.parseInt(cityCode));
        map.put("contactGender", sexId);
        map.put("contactName", tv_lianxiren_text.getText().toString().trim());
        map.put("enterpriseName", tv_qiye_text.getText().toString().trim());
        map.put("mobile", tv_phone_text.getText().toString().trim());
        map.put("number", Integer.parseInt(tv_guimo_text.getText().toString().trim()));
        Api.getClient(HttpRequest.baseUrl_sys).enterpriseOrder(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        ToastUtil.ToastShort(CompanyOrderingActivity.this,"提交成功");
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            String city =  data.getStringExtra("city");
            cityCode = data.getStringExtra("cityCode");
            if(null != city){
                tv_chengshi_text.setText(city+"");
            }
        }
    }

}
