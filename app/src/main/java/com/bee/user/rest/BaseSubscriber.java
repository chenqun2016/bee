package com.bee.user.rest;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bee.user.BeeApplication;
import com.bee.user.R;
import com.bee.user.utils.NetWorkUtil;
import com.bee.user.utils.ToastUtil;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.huaxiafinance.www.crecyclerview.crecyclerView.EventBusUtils;
import com.huaxiafinance.www.crecyclerview.crecyclerView.KICKOUTEvent;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by Administrator on 2016/11/23.
 */

public abstract class BaseSubscriber<T> implements BaseObserver<T>, Observer<BaseResult<T>> {

    private String mErrorText = "";
    private Context mContext;
    private boolean showErrorToash = true;

    protected BaseSubscriber(Context context) {
        super();
        this.mContext = context;
    }
    protected BaseSubscriber() {
        super();
    }
    protected BaseSubscriber(boolean showErrorToash) {
        super();
        this.showErrorToash = showErrorToash;
    }
    protected BaseSubscriber(String errorText) {
        super();
        this.mErrorText = errorText;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            ToastUtil.ToastShortFromNet("连接超时");
        } else if (e instanceof ConnectException) {
            ToastUtil.ToastShortFromNet("网络中断，请检查您的网络状态");
        } else {
            if (!NetWorkUtil.isNetConnected(BeeApplication.getInstance())) {
                ToastUtil.ToastShortFromNet("网络连接不可用,请检查网络设置");
            } else {
                ToastUtil.ToastShortFromNet(TextUtils.isEmpty(mErrorText)?e.getMessage():mErrorText);
//                ToastUtil.getInstance().ToastShortFromNet("请求异常");
            }
        }
        onFail(e.getMessage());
    }
    @Override
    public void onNext(BaseResult<T> baseBean) {
        if (baseBean.getSuccess()) {
            onSuccess(baseBean.getData());
        } else {
            if (!TextUtils.isEmpty(baseBean.getErrorCode()) && baseBean.getErrorCode().contains(EventBusUtils.ErrorCode_login_out)) {//异地登录
                ToastUtil.ToastShortFromNet(BeeApplication.getInstance().getResources().getString(R.string.text_kickout));
                //清除本地数据
                EventBusUtils.getInstance().kickOff(KICKOUTEvent.From_BACK);
            } else {
                if (!TextUtils.isEmpty(baseBean.getErrorMsg()) && showErrorToash) {
                    ToastUtil.ToastShortFromNet(TextUtils.isEmpty(mErrorText)?baseBean.getErrorMsg():mErrorText);
//                    ToastUtil.getInstance().ToastShortFromNet("请求异常");
                }
            }
            onFail(baseBean.getErrorMsg());
        }
    }

    @Override
    public void onFail(String fail) {
        Log.e("basesubscriber",fail+"");
    }
}
