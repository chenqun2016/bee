package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/03  17：59
 * 描述：
 */
public class InvoicingMoreActivity extends BaseActivity {


    @OnClick({R.id.tv_right,R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                startActivity(new Intent(this,BillListActivity.class));
                break;
            case R.id.tv_sure:
                break;

        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_invoice_more;
    }

    @Override
    public void initViews() {

    }
}
