package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.GiftcardBean;
import com.bee.user.bean.MyCommentBean;
import com.bee.user.entity.GiftcardEntity;
import com.bee.user.entity.MyCommentEntity;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.order.OrderActivity;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/04  20：03
 * 描述：
 */
public class GiftcardActivity extends BaseActivity {

    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;


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
