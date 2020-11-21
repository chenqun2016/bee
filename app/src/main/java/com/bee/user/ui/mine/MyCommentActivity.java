package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.MyCommentBean;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.entity.MyCommentEntity;
import com.bee.user.entity.TradeListEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.nearby.CommentActivity;
import com.bee.user.ui.order.OrderActivity;
import com.bee.user.ui.order.OrderCommentActivity;
import com.bee.user.utils.CommonUtil;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/26  16：36
 * 描述：
 */
public class MyCommentActivity extends BaseActivity {

    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mycomment;
    }

    @Override
    public void initViews() {
        View empty = View.inflate(this, R.layout.empty_trade_list, null);
        CommonUtil.initBuyCardView(empty);

        TextView tv_empty  = empty.findViewById(R.id.tv_empty);
        tv_empty.setText("您还未对商品有过评价");

        Drawable icon = getResources().getDrawable(R.drawable.bg_kongbai9);
        int width =  icon.getIntrinsicWidth() ;
        int height =  icon.getIntrinsicHeight() ;
        icon.setBounds(0, 0, width, height);
        tv_empty.setCompoundDrawables(null,icon,null,null);

        TextView tv_guangguang  = empty.findViewById(R.id.tv_guangguang);
        tv_guangguang.setText("我要评价");
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCommentActivity.this, OrderActivity.class));
            }
        });

        crecyclerview.setView(MyCommentEntity.class);
        crecyclerview.setEmptyView(empty);
        crecyclerview.setRow(20);
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


        ArrayList<MyCommentBean> beans = new ArrayList<>();
        beans.add(new MyCommentBean());
        beans.add(new MyCommentBean());
        beans.add(new MyCommentBean());
        beans.add(new MyCommentBean());




        crecyclerview.getBaseAdapter().setList(beans);
        crecyclerview.getBaseAdapter().getLoadMoreModule().loadMoreEnd();
    }
}
