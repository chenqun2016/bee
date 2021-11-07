package com.bee.user.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.bee.user.bean.NewsItemBean;
import com.bee.user.entity.NewsEntity;
import com.bee.user.entity.NewsItemEntity;
import com.bee.user.params.NewsParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.NewsAdapter;
import com.bee.user.ui.adapter.NewsDetailAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.mine.coupon.CouponActivity;
import com.bee.user.ui.order.OrderListActivity;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.sputils.SPUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  15：35
 * 描述：
 */
public class NewsItemActivity extends BaseActivity implements OnItemChildClickListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    private String type;
    private NewsDetailAdapter newsDetailAdapter;
    private LoadmoreUtils loadmoreUtils;

    public static void  startNewsItemActivity(Activity activity, String type,String messageTitle) {
        Intent intent = new Intent(activity,NewsItemActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("messageTitle",messageTitle);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initViews() {
        type = getIntent().getStringExtra("type");
        String messageTitle = getIntent().getStringExtra("messageTitle");
        toolbar_title.setText(messageTitle);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        newsDetailAdapter = new NewsDetailAdapter();
        recyclerview.setAdapter(newsDetailAdapter);
        newsDetailAdapter.addChildClickViewIds(R.id.tv_click);
        newsDetailAdapter.setOnItemChildClickListener(this);
        loadmoreUtils = new LoadmoreUtils() {

            @Override
            protected void getDatas(int page) {
                getPlateFormMessage(page);
            }
        };
        loadmoreUtils.refresh(newsDetailAdapter);
        loadmoreUtils.initLoadmore(newsDetailAdapter,swipeRefreshLayout);

    }

    private void getPlateFormMessage(int page) {
        NewsParams params = new NewsParams();
        params.memberId = SPUtils.geTinstance().getUserInfo().id;
        params.messageType = type;
        Api.getClient(HttpRequest.baseUrl_file).getPlatFormMessage(Api.getRequestBody(params)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<NewsBean>>() {
                    @Override
                    public void onSuccess(List<NewsBean> beans) {
                        if(!ObjectUtils.isEmpty(beans)) {
                            loadmoreUtils.onSuccess(newsDetailAdapter,beans);
                        }else {
                            // TODO: 2021/11/7  空状态页
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        // TODO: 2021/11/7  空状态页
                        loadmoreUtils.onFail(newsDetailAdapter,fail);
                    }
                });
    }

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        List<NewsBean> data = newsDetailAdapter.getData();
        //SYSTEM,系统通知
        //REWARD,红包助手
        //ORDER,订单消息
        //MERCHANT,商家消息
        String messageType = data.get(position).getMessageType();
        switch (messageType) {
            case "REWARD":
                startActivity(new Intent(this, CouponActivity.class));
                break;
            case "ORDER":
                startActivity(OrderListActivity.newInstance(this,0));
                break;
        }
    }
}
