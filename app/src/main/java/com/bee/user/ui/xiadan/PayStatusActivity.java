package com.bee.user.ui.xiadan;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.event.CloseEvent;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.order.OrderListActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/17  21：40
 * 描述：
 */
public class PayStatusActivity extends BaseActivity {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;

    @BindView(R.id.pay_status)
    TextView pay_status;

    @BindView(R.id.des)
    TextView des;

    @BindView(R.id.des2)
    TextView des2;

    @BindView(R.id.btn_1)
    TextView btn_1;

    @BindView(R.id.btn_2)
    TextView btn_2;


    @OnClick({R.id.btn_1,R.id.btn_2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_1:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btn_2:

                Intent intent = new Intent(this, OrderListActivity.class);
                startActivity(intent);

                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_status;
    }

    @Override
    public void initViews() {

        EventBus.getDefault().post(new CloseEvent(CloseEvent.TYPE_PAY));


        iv_icon.setImageResource(R.drawable.icon_dengdaichuli);
        iv_icon.setImageResource(R.drawable.icon_zhifushibai);
        iv_icon.setImageResource(R.drawable.icon_zhifuchenggong);
    }
}
