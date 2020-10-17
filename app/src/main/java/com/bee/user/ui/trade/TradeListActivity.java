package com.bee.user.ui.trade;

import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/17  21：08
 * 描述：
 */
public class TradeListActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trade_list;
    }

    @Override
    public void initViews() {

    }
}
