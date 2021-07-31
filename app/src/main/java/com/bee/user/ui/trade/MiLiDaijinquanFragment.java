package com.bee.user.ui.trade;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.event.ReflushEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  21：55
 * 描述：
 */
public class MiLiDaijinquanFragment extends BaseFragment {
    TextView tv_sure;
    EditText card_num;
    EditText pass;
    TextView tv_arror;
    @Override
    protected void getDatas() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milidaijinquan,container,false);
        tv_sure  = view.findViewById(R.id.tv_sure);
        card_num  = view.findViewById(R.id.card_num);
        pass  = view.findViewById(R.id.pass);
        tv_arror  = view.findViewById(R.id.tv_arror);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_arror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_arror.setVisibility(View.GONE);
            }
        });
        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(card_num);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(pass);
        Observable.combineLatest(c1, c2, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2) throws Throwable {

                return !TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(charSequence2);
            }
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
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                map.put("cardNo", card_num.getText().toString());
                map.put("password", pass.getText().toString());
                map.put("deviceType", "安卓");

                Api.getClient(HttpRequest.baseUrl_pay).fillCardBinding(Api.getRequestBody(map))
                        .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>(false) {
                            @Override
                            public void onSuccess(String object) {
                                tv_arror.setVisibility(View.GONE);
                                startActivity(TradeStatusActivity.newInstance(getContext(),object));
                                EventBus.getDefault().post(new ReflushEvent(ReflushEvent.TYPE_REFLUSH_MILI));

                                card_num.setText("");
                                pass.setText("");
                            }

                            @Override
                            public void onFail(String fail) {
                                tv_arror.setVisibility(View.VISIBLE);
                                tv_arror.setText(fail+"");
                                super.onFail(fail);
                            }
                        });
            }
        });


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
