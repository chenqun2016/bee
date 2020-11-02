package com.bee.user.ui.mine;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.BillBean;
import com.bee.user.bean.MyCommentBean;
import com.bee.user.entity.BillEntity;
import com.bee.user.entity.MyCommentEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/02  16：38
 * 描述：
 */
public class BillActivity extends BaseActivity {

    @BindView(R.id.tv_quanxuan_text)
    TextView tv_quanxuan_text;

    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;

    @OnClick({R.id.tv_right,R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                startActivity(new Intent(this,BillListActivity.class));
                break;
            case R.id.tv_confirm:
                break;

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill;
    }

    @Override
    public void initViews() {
        String str = "100";
        SpannableString msp = new SpannableString("共" + str + "元");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF564A)), 1, 1 + str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_quanxuan_text.setText(msp);

        View foot = View.inflate(this, R.layout.foot_activity_bill, null);
        foot.findViewById(R.id.ll_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<BillBean> beans = new ArrayList<>();
                beans.add(new BillBean());
                beans.add(new BillBean());
                beans.add(new BillBean());
                beans.add(new BillBean());

                crecyclerview.getBaseAdapter().addData(beans);
            }
        });


        crecyclerview.setView(BillEntity.class);
        crecyclerview.addFooterView(foot);
        crecyclerview.setRow(10);
        crecyclerview.setShowLoadMoreEndStr(false);
        crecyclerview.setCanLoadMore(true);
        crecyclerview.start();


        ArrayList<BillBean> beans = new ArrayList<>();
        beans.add(new BillBean());
        beans.add(new BillBean());
        beans.add(new BillBean());
        beans.add(new BillBean());


        crecyclerview.getBaseAdapter().setList(beans);
        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd(true);
    }
}
