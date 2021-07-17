package com.bee.user.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2021/7/17
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class PayUtils {

/*    bizId	integer($int64)
    支付业务ID[米粒配置ID]

    bizType	string
    支付业务类型[1.米粒面值2.配送卡]

    cardType	string
    配送卡类型[a.月度 b.季度 c.年度]

    deviceType	string
    设备类型[安卓、IOS]

    memberId	integer($int64)
    会员ID

    payChannel	string
    支付渠道[ALIPAY.支付宝 WECHATPAY.微信支付]*/

    public static void toPay(Activity activity, Map<String, String> map, Handler mHandler){
        Api.getClient(HttpRequest.baseUrl_pay).zhifubao_pay(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String r) {
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(activity);
                                Map<String,String> result = alipay.payV2(r,true);

                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });

    }
}
