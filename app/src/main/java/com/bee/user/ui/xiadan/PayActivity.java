package com.bee.user.ui.xiadan;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.event.CloseEvent;
import com.bee.user.params.OrderingParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.PayPassView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/13  19：33
 * 描述：
 */
public class PayActivity extends BaseActivity {

    @OnClick({R.id.tv_pay})
    public void onClick(View view){
        showPayDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
    }

    public static Intent newIntent(Context context, List<StoreBean> datas) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("data", (Serializable) datas);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showPayDialog(){
        BaseDialog bottomSheetDialog = new BaseDialog(this){
            @Override
            protected int provideContentViewId() {
                return R.layout.dialog_pay;
            }

            @Override
            protected void initViews(BaseDialog dialog) {
                dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(null != dialog && dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });

                PayPassView paypassview = dialog.findViewById(R.id.paypassview);
                paypassview.setOnFinishInput(new PayPassView.OnPasswordInputFinish() {
                    @Override
                    public void inputFinish() {
                        OrderingParams params = (OrderingParams) getIntent().getSerializableExtra("params");
                        Api.getClient(HttpRequest.baseUrl_order).ordering(Api.getRequestBody(params)).
                                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseSubscriber<Object>() {
                                    @Override
                                    public void onSuccess(Object userBean) {
                                        EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_REFLUSH));
                                        startActivity(new Intent(PayActivity.this,PayStatusActivity.class));
                                    }

                                    @Override
                                    public void onFail(String fail) {
                                        super.onFail(fail);
                                    }
                                });
                    }

                    @Override
                    public void inputFirst() {

                    }
                });


            }
        };
        bottomSheetDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        finish();
    }
}
