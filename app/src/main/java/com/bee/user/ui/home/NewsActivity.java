package com.bee.user.ui.home;

import android.view.View;

import com.bee.user.R;
import com.bee.user.bean.NewsBean;
import com.bee.user.params.NewsParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.NewsAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.sputils.SPUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/21  20：45
 * 描述：
 */
public class NewsActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;

    private NewsAdapter newsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void initViews() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter();
        recyclerview.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(this);
        getPlateFormMessage();
    }

    private void getPlateFormMessage() {
        NewsParams params = new NewsParams();
        params.memberId = SPUtils.geTinstance().getUserInfo().id;
        params.messageType = "";
        Api.getClient(HttpRequest.baseUrl_file).getPlatFormMessage(Api.getRequestBody(params)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<NewsBean>>() {
                    @Override
                    public void onSuccess(List<NewsBean> beans) {
                        if(!ObjectUtils.isEmpty(beans)) {
                            newsAdapter.setList(beans);
                        }else {
                            // TODO: 2021/11/7  空状态页
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        // TODO: 2021/11/7  空状态页
                    }
                });
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        List<NewsBean> data = newsAdapter.getData();
        NewsItemActivity.startNewsItemActivity(this,data.get(position).getMessageType(),data.get(position).getMessageTitle());
    }
}
