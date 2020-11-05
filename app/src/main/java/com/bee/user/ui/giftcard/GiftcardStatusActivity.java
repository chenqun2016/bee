package com.bee.user.ui.giftcard;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/05  20：30
 * 描述：
 */
public class GiftcardStatusActivity extends BaseActivity {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;

    @BindView(R.id.pay_status)
    TextView pay_status;

    @BindView(R.id.des)
    TextView des;


    @BindView(R.id.btn_1)
    TextView btn_1;

    @BindView(R.id.btn_2)
    TextView btn_2;


    @OnClick({R.id.btn_1,R.id.btn_2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_1:
                finish();
                break;
            case R.id.btn_2:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trade_status;
    }

    @Override
    public void initViews() {


        int type = 0;
        if(0 == type){
            iv_icon.setImageResource(R.drawable.icon_zhifuchenggong);
            pay_status.setText("购买成功");
            des.setText("1张（10米粒）礼品卡已进入您的账户");
            btn_1.setText("查看我的礼品卡");
            btn_2.setText("赠送好友");
        }else if(1 == type){
            iv_icon.setImageResource(R.drawable.icon_zhifushibai);
            pay_status.setText("支付失败");
            des.setText("支付米粒余额不足");
            btn_1.setText("查看我的礼品卡");
            btn_2.setText("重新支付");
        }else{
            iv_icon.setImageResource(R.drawable.icon_dengdaichuli);
            pay_status.setText("等待处理");
            des.setText("已提交申请，等待处理");
            btn_1.setText("查看我的礼品卡");
            btn_2.setText("重新支付");
        }
    }
}
