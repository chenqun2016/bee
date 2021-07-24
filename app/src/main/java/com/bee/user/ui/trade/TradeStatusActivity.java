package com.bee.user.ui.trade;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/17  20：56
 * 描述：
 */
public class TradeStatusActivity extends BaseActivity {
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

    public static Intent newInstance(Context context, String text) {
        Intent intent = new Intent(context, TradeStatusActivity.class);
        intent.putExtra("content",text);
        return intent;
    }

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
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");

        if(!TextUtils.isEmpty(content)){
            des.setText(content);
        }

//        iv_icon.setImageResource(R.drawable.icon_dengdaichuli);
//        iv_icon.setImageResource(R.drawable.icon_zhifushibai);
        iv_icon.setImageResource(R.drawable.icon_zhifuchenggong);


        btn_1.setVisibility(View.GONE);
        btn_2.setText("返回米粒");
    }
}
