package com.bee.user.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bee.user.R;
import com.bee.user.bean.UserBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.utils.sputils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SendCodeView extends FrameLayout implements View.OnClickListener {


    //倒计时的总共时间
    public static final int TIME = 60;

    private TextView tv_getcode;

    private MyOnClickListener mListener;


    public boolean mOnDestory = false;
    public boolean mIsWorking = false;
    private int i;

    private int clickableColor = R.color.color_3e7dfb;
    private int unClickableColor = R.color.color_ccc;



    public SendCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public SendCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SendCodeView(Context context) {
        super(context);
    }


    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.view_code, this);

        tv_getcode = (TextView) findViewById(R.id.tv_getcode);
        tv_getcode.setOnClickListener(this);
    }


    private void sendCode() {
        Api.getClient().smsCode(mListener.onGetPhone())
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String str) {
                        onHttpNext(str);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        onHttpError();
                    }
                });
    }


    private void onHttpNext(String s) {
        mListener.onSuccess(s);
        startThread();
    }

    private void onHttpError() {
        mListener.onFailure("");
        mIsWorking = false;

        setCodeState(true);
    }


    /**
     * 初始化   必须调用
     *
     * @param listener
     */
    public void initDatas(MyOnClickListener listener) {
        this.mListener = listener;
    }


    private void startThread() {
        mOnDestory = false;
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        i = TIME;
        doStart();
    }

    public void onDestory() {
        mOnDestory = true;
    }

    private void doStart() {
        if (mOnDestory || i <= 0) {
            Message msg = new Message();
            msg.what = 0;
            if (i <= 0)
                handler.sendMessageDelayed(msg, 1000);
            else
                handler.sendMessage(msg);
            return;
        }
        i--;
        Message msg = new Message();
        msg.what = 2;
        msg.obj = "剩余" + i + "s";
        msg.arg1 = i;
        handler.sendMessageDelayed(msg, 1000);
    }


    public interface MyOnClickListener {
        String onGetPhone();

        void onSuccess(String t);

        void onFailure(String t);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://倒计时结束
                    setCodeState(true);
                    tv_getcode.setText("重新发送");
                    mIsWorking = false;
                    break;
                case 1://倒计时开始
                    String strs = "剩余" + TIME + "s";
                    SpannableString msp = new SpannableString(strs);
                    msp.setSpan(new ForegroundColorSpan(getResources().getColor(clickableColor)), 2,
                            strs.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    tv_getcode.setText(msp);

                    break;
                case 2:

                    String strs2 = msg.obj.toString();
                    SpannableString msp2 = new SpannableString(strs2);
                    msp2.setSpan(new ForegroundColorSpan(getResources().getColor(clickableColor)), 2,
                            strs2.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    tv_getcode.setText(msp2);
                    doStart();
                    break;
            }
        }
    };


    @Override
    public void onClick(View view) {

        if (null == mListener) {
            return;
        }

        doGetCode();
    }

    public void doGetCode() {
        mIsWorking = true;

        //TODO
        setCodeState(false);

        sendCode();
    }



    //设置短信验证码的可点击状态，颜色
    private void setCodeState(boolean enable) {
        tv_getcode.setEnabled(enable);
        tv_getcode.setTextColor(enable ? getResources().getColor(clickableColor) : getResources().getColor(unClickableColor));
    }

    public void setItemClickable(boolean b) {
        if (mIsWorking) return;
        setCodeState(b);
    }

    public void setItemTextColor(int color) {
        if (mIsWorking) return;
        tv_getcode.setTextColor(color);

    }


}
