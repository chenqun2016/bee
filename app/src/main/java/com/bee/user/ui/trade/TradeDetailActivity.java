package com.bee.user.ui.trade;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.PaymentDetailBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/18  13：41
 * 描述：
 */
public class TradeDetailActivity extends BaseActivity {
    @BindView(R.id.mili)
    TextView mili;
    @BindView(R.id.mili_num)
    TextView mili_num;

    @BindView(R.id.jiaoyi)
    TextView jiaoyi;
    @BindView(R.id.tv_trade_type_value)
    TextView tv_trade_type_value;
    @BindView(R.id.tv_pay_type_value)
    TextView tv_pay_type_value;
    @BindView(R.id.tv_trade_time_value)
    TextView tv_trade_time_value;
    @BindView(R.id.tv_trade_num_value)
    TextView tv_trade_num_value;
    @BindView(R.id.tv_beizhu_value)
    TextView tv_beizhu_value;

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trade_detail;
    }

    @Override
    public void initViews() {
        int id = getIntent().getIntExtra("id",0);
        Api.getClient(HttpRequest.baseUrl_pay).getPaymentDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PaymentDetailBean>() {
                    @Override
                    public void onSuccess(PaymentDetailBean bean) {
                        mili.setText(bean.bizTypeDesc+"");
                        mili_num.setText(CommonUtil.getTradeType(bean.tranType)+ CommonUtil.getNomalMoneyType(bean.orderAmount));
                        if(!TextUtils.isEmpty(bean.status)){
                            jiaoyi.setText(CommonUtil.getTradeType(Integer.parseInt(bean.status)));
                        }
                        tv_trade_type_value.setText(bean.tranTypeDesc);
                        tv_pay_type_value.setText("*");
                        tv_trade_time_value.setText(CommonUtil.getNomalTime(bean.createTime));
                        tv_trade_num_value.setText(bean.orderId+"");
                        tv_beizhu_value.setText(bean.remarks+"");
                    }
                });
    }
}
