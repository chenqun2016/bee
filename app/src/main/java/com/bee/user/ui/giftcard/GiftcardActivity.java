package com.bee.user.ui.giftcard;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.GiftcardBean;
import com.bee.user.entity.GiftcardEntity;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/04  20：03
 * 描述：
 */
public class GiftcardActivity extends BaseActivity {

    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;

    @OnClick({R.id.tv_right,R.id.tv_1,R.id.tv_2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_right:
                startActivity(new Intent(this, GiftcardRecordActivity.class));
                break;
            case R.id.tv_1:
                startActivity(new Intent(this, BuyGiftcardActivity.class));
                break;
            case R.id.tv_2:
                startActivity(new Intent(this, ZengsongGiftcardActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_giftcard;
    }

    @Override
    public void initViews() {
        View empty = View.inflate(this, R.layout.empty_giftcard_list, null);


        TextView tv_guangguang  = empty.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GiftcardActivity.this, MainActivity.class));
            }
        });

        crecyclerview.setView(GiftcardEntity.class);
        crecyclerview.setEmptyView(empty);
        crecyclerview.setRow(8);
        crecyclerview.setCanLoadMore(true);

        crecyclerview.setOnRequestListener(new CRecyclerView.OnRequestListener() {
            @Override
            public void onRequestEnd() {

            }

            @Override
            public void onNoData() {
            }

            @Override
            public void onNoMoreDatas() {

            }

            @Override
            public void onRefreshStart() {

            }
        });
        crecyclerview.start();


        ArrayList<GiftcardBean> beans = new ArrayList<>();
        beans.add(new GiftcardBean());
        beans.add(new GiftcardBean());
        beans.add(new GiftcardBean());
        beans.add(new GiftcardBean());

        crecyclerview.getBaseAdapter().setList(beans);
        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd(true);
    }
}
