package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bee.user.R;
import com.bee.user.bean.MyCommentWrapBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.order.OrderCommentActivity;
import com.bee.user.ui.order.OrderListActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/26  16：36
 * 描述：
 */
public class MyCommentActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;
    LoadmoreUtils loadmoreUtils;

    CommentListAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mycomment;
    }

    @Override
    public void initViews() {


        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new CommentListAdapter();
        recyclerview.setAdapter(mAdapter);

        initLoadMore();

    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        loadmoreUtils = new LoadmoreUtils() {
            @Override
            protected void getDatas(int page) {
                getComments(page);
            }
        };
        loadmoreUtils.initLoadmore(mAdapter, swipe_refresh_layout);


        View empty = View.inflate(this, R.layout.empty_trade_list, null);
        CommonUtil.initBuyCardView(empty);

        TextView tv_empty = empty.findViewById(R.id.tv_empty);
        tv_empty.setText("您还未对商品有过评价");

        Drawable icon = getResources().getDrawable(R.drawable.bg_kongbai9);
        int width = icon.getIntrinsicWidth();
        int height = icon.getIntrinsicHeight();
        icon.setBounds(0, 0, width, height);
        tv_empty.setCompoundDrawables(null, icon, null, null);

        TextView tv_guangguang = empty.findViewById(R.id.tv_guangguang);
        tv_guangguang.setText("我要评价");
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCommentActivity.this, OrderListActivity.class));
            }
        });
        loadmoreUtils.setEmptyView(empty);

        loadmoreUtils.refresh(mAdapter);
    }

    private void getComments(int page) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("pageNum", page);
        params.put("pageSize", LoadmoreUtils.PAGE_SIZE);

        Api.getClient(HttpRequest.baseUrl_eva).myOrderComment(Api.getRequestBody(params)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MyCommentWrapBean>() {
                    @Override
                    public void onSuccess(MyCommentWrapBean beans) {
                        loadmoreUtils.onSuccess(mAdapter, beans.records);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(mAdapter, fail);
                    }
                });
    }


    public static class CommentListAdapter extends BaseQuickAdapter<MyCommentWrapBean.CommentItemBean, BaseViewHolder> implements LoadMoreModule {

        public CommentListAdapter() {
            super(R.layout.item_mycomment_list);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, MyCommentWrapBean.CommentItemBean commentBean) {
            FrameLayout sl_content = helper.findView(R.id.sl_content);
            sl_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                OrderCommentActivity.newInstance(mContext,);
                    MyCommentDetailActivity.newInstance(sl_content.getContext(),commentBean.id);
                }
            });
            helper.findView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Api.getClient(HttpRequest.baseUrl_eva).myOrderCommentDelete(commentBean.id).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSubscriber<Object>() {
                                @Override
                                public void onSuccess(Object beans) {
                                    removeAt(helper.getLayoutPosition());
                                }

                                @Override
                                public void onFail(String fail) {
                                    super.onFail(fail);
                                }
                            });
                }
            });
            ImageView icon = helper.findView(R.id.icon);
            Picasso.with(icon.getContext()).load(commentBean.storeLogo);
            TextView tv_name = helper.findView(R.id.tv_name);
            tv_name.setText(commentBean.storeName);
            TextView tv_time = helper.findView(R.id.tv_time);
            tv_time.setText(CommonUtil.getNomalTime(commentBean.createTime));
        }
    }
}
