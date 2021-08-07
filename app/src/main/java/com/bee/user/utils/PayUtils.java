package com.bee.user.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.bee.user.R;
import com.bee.user.bean.MyMiLiBean;
import com.bee.user.params.PayParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.mine.SetPasswordActivity1;
import com.bee.user.ui.order.OrderListActivity;
import com.bee.user.ui.trade.MiLiActivity;
import com.bee.user.ui.xiadan.PayStatusActivity;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.PayPassView;

import java.util.HashMap;
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
    支付业务ID[米粒配置/配送卡 /礼品卡ID]

    bizType	string
    支付业务类型[1.米粒面值2.配送卡，4.礼品卡]

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


    //下单
    public static final int PAY_TYPE_ORDER = 2;
    //配送卡
    public static final int PAY_TYPE_PEISONG_CARD = 3;
    public static void pay(int type,MyMiLiBean miLiBean,int totalMoney,Object params, BaseActivity activity){
        if(null == activity){
            return;
        }
        if (null == miLiBean) {
            return;
        }
        //余额不足
        if (miLiBean.surplusAmount <= totalMoney) {
            activity.showCommonDialog("对不起，您的米粒当前余额不足", "暂不支付", "立即充值", new BaseActivity.DialogClickListener() {
                @Override
                public void onDialogCancle() {
                    //下单的情况跳转订单列表
                    if(type == PAY_TYPE_ORDER){
                        Intent intent = new Intent(activity, OrderListActivity.class);
                        activity.startActivity(intent);
                    }
                }

                @Override
                public void onDialogSure() {
                    activity.startActivity(new Intent(activity, MiLiActivity.class));
                }
            });
        } else {
            hasPayPassword(type,params,activity);
        }
    }

    private static void hasPayPassword(int type,Object params,BaseActivity activity) {
        if(null == activity){
            return;
        }

        if (SPUtils.geTinstance().hasPayPassword()) {
            BaseDialog bottomSheetDialog = new BaseDialog(activity) {
                @Override
                protected int provideContentViewId() {
                    return R.layout.dialog_pay;
                }

                @Override
                protected void initViews(BaseDialog dialog) {
                    dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != dialog && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });

                    PayPassView paypassview = dialog.findViewById(R.id.paypassview);
                    paypassview.setOnFinishInput(new PayPassView.OnPasswordInputFinish() {
                        @Override
                        public void inputFinish() {
                            if(params instanceof PayParams){
                                PayParams payParams = (PayParams) params;
                                payParams.payPassword = paypassview.getStrPassword();
                                doMiLiPay(type,payParams,paypassview,activity);
                            }else if(params instanceof HashMap){
                                Map<String, String> map = (Map<String, String>) params;
                                map.put("payPassword",paypassview.getStrPassword());
                                doMiLiPay(type,map,paypassview,activity);
                            }else{
                                doMiLiPay(type,params,paypassview,activity);
                            }
                        }

                        @Override
                        public void inputFirst() {
                        }

                        @Override
                        public void inputNoFull() {
                        }
                    });


                }
            };
            bottomSheetDialog.show();
        } else {
            doMiLiPay(type,params,null,activity);
        }
    }


    private static void doMiLiPay(int type,Object params,PayPassView paypassview,BaseActivity activity) {
        if(null == activity){
            return;
        }
        if(type == PAY_TYPE_PEISONG_CARD){
            Api.getClient(HttpRequest.baseUrl_pay).buyCard(Api.getRequestBody(params)).
                    subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object userBean) {
                            onPaySucess(type,activity);
                        }

                        @Override
                        protected void onFail(String errorMsg, int errorCode) {
                            onPayFail(errorCode,activity,paypassview);
                        }
                    });
        }
        if(type == PAY_TYPE_ORDER){
            Api.getClient(HttpRequest.baseUrl_order).riceGrainsOrder(Api.getRequestBody(params)).
                    subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object userBean) {
                            onPaySucess(type,activity);
                        }

                        @Override
                        protected void onFail(String errorMsg, int errorCode) {
                            onPayFail(errorCode,activity,paypassview);
                        }
                    });

        }
    }

    private static void onPayFail( int errorCode, BaseActivity activity, PayPassView paypassview) {
        if(null == activity){
            return;
        }
        //有设置支付密码的情况
        if(404 == errorCode && SPUtils.geTinstance().hasPayPassword()){
            //支付密码验证失败
            activity.showCommonDialog("支付密码错误，请重试", "忘记密码", "重新支付", new BaseActivity.DialogClickListener() {
                @Override
                public void onDialogCancle() {
                    //忘记支付密码，重新设置支付密码
                    SetPasswordActivity1.toSetPassword(activity, "payPass");
                }

                @Override
                public void onDialogSure() {
                    //重新支付
                    if (null != paypassview) {
                        paypassview.clearText();
                    }
                }
            });
        }
    }

    private static void onPaySucess(int type,BaseActivity activity) {
        if(null == activity){
            return;
        }
        Intent intent = new Intent(activity, PayStatusActivity.class);
        intent.putExtra("type",type);
        activity.startActivity(intent);
    }
}
