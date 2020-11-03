package com.bee.user.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.RadioGroupPlus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/03  16：14
 * 描述：
 */
public class InvoicingActivity extends BaseActivity {
    @BindView(R.id.rgp_leixing)
    RadioGroupPlus rgp_leixing;
    @BindView(R.id.tv_invoice_taitou_et)
    EditText tv_invoice_taitou_et;
    @BindView(R.id.tv_invoice_shibiehao_et)
    EditText tv_invoice_shibiehao_et;
    @BindView(R.id.tv_more_et)
    TextView tv_more_et;
    @BindView(R.id.tv_money_et)
    TextView tv_money_et;


    @BindView(R.id.tv_shoujianren_et)
    EditText tv_shoujianren_et;
    @BindView(R.id.tv_phone_et)
    EditText tv_phone_et;
    @BindView(R.id.tv_area_et)
    TextView tv_area_et;
    @BindView(R.id.tv_dizhi_et)
    EditText tv_dizhi_et;

    @OnClick({R.id.tv_right,R.id.tv_more_et,R.id.tv_area_et,R.id.tv_sure})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.tv_right:
                startActivity(new Intent(this,BillListActivity.class));
                break;
            case R.id.tv_more_et:
                startActivity(new Intent(this,InvoicingMoreActivity.class));
                break;
            case R.id.tv_area_et:
                break;
            case R.id.tv_sure:
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_invoicing;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setmAdjustView(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        String str1 = 300 + "";
        SpannableString msp = new SpannableString(str1 + "元");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF564A)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_money_et.setText(msp);
    }
}
