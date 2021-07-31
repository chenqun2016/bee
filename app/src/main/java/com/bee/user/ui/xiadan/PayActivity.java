package com.bee.user.ui.xiadan;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.MyMiLiBean;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.event.CloseEvent;
import com.bee.user.event.ReflushEvent;
import com.bee.user.params.PayParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.mine.SetPasswordActivity1;
import com.bee.user.ui.order.OrderListActivity;
import com.bee.user.ui.trade.MiLiActivity;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.PayPassView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/13  19：33
 * 描述：
 */
public class PayActivity extends BaseActivity {
    @BindView(R.id.tv_money)
    TextView tv_money;

    @BindView(R.id.tv_mili)
    TextView tv_mili;

    int totalMoney;

    @OnClick({R.id.tv_pay, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay:
                showPayDialog();
                break;
            case R.id.tv_about:
                Intent intent = new Intent(PayActivity.this, OrderListActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }


    public static Intent newIntent(Context context, PayParams datas, int totalMoney) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("params", datas);
        intent.putExtra("totalMoney", totalMoney);
        return intent;
    }


    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        totalMoney = getIntent().getIntExtra("totalMoney", 0);
        tv_money.setText("¥" + totalMoney);
        getMiLiDatas();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    BaseDialog bottomSheetDialog;

    private void showPayDialog() {
        if (null == miLiBean) {
            return;
        }
        //余额不足
        if (miLiBean.surplusAmount <= totalMoney) {
            showCommonDialog("对不起，您的米粒当前余额不足", "暂不支付", "立即充值", new DialogClickListener() {
                @Override
                public void onDialogCancle() {
                    Intent intent = new Intent(PayActivity.this, OrderListActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onDialogSure() {
                    startActivity(new Intent(PayActivity.this, MiLiActivity.class));
                }
            });
        } else {
            hasPayPassword();
        }
    }

    PayPassView paypassview;

    private void hasPayPassword() {
        if (SPUtils.geTinstance().hasPayPassword()) {
            bottomSheetDialog = new BaseDialog(this) {
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

                    paypassview = dialog.findViewById(R.id.paypassview);
                    paypassview.setOnFinishInput(new PayPassView.OnPasswordInputFinish() {
                        @Override
                        public void inputFinish() {
                            doMiLiPay(paypassview.getStrPassword());
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
            doMiLiPay("");
        }
    }

    private void doMiLiPay(String password) {
        PayParams params = (PayParams) getIntent().getSerializableExtra("params");
        params.payPassword = password;
        Api.getClient(HttpRequest.baseUrl_order).riceGrainsOrder(Api.getRequestBody(params)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object userBean) {
                        toPayStatusActivity();
                    }

                    @Override
                    protected void onFail(String errorMsg, int errorCode) {
                        onPayFail(errorCode);
                    }
                });
    }

    private void onPayFail(int errorCode) {
        //有设置支付密码的情况
        if(404 == errorCode && SPUtils.geTinstance().hasPayPassword()){
            //支付密码验证失败
            showCommonDialog("支付密码错误，请重试", "忘记密码", "重新支付", new DialogClickListener() {
                @Override
                public void onDialogCancle() {
                    //忘记支付密码，重新设置支付密码
                    SetPasswordActivity1.toSetPassword(PayActivity.this, "payPass");
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

    private void toPayStatusActivity() {
        EventBus.getDefault().post(new ReflushEvent(ReflushEvent.TYPE_REFLUSH_MILI));
        EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_REFLUSH));
        startActivity(new Intent(PayActivity.this, PayStatusActivity.class));

    }


    MyMiLiBean miLiBean;

    public void getMiLiDatas() {
        Api.getClient(HttpRequest.baseUrl_pay).getMemberRice().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MyMiLiBean>() {
                    @Override
                    public void onSuccess(MyMiLiBean s) {
                        miLiBean = s;
                        if (s.surplusAmount <= 0) {
                            tv_mili.setText("余额不足");
                        } else {
                            tv_mili.setText(s.surplusAmount + "");
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        if (event.type == CloseEvent.TYPE_PAY) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflushEvent(ReflushEvent event) {
        if (ReflushEvent.TYPE_REFLUSH_MILI == event.type) {
            getMiLiDatas();
        }
    }

}
