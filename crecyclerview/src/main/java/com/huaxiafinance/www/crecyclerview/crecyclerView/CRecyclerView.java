package com.huaxiafinance.www.crecyclerview.crecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.R;
import com.huaxiafinance.www.crecyclerview.itemdecoration.HorizontalDividerItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Created by chenqun on 2017/2/17.
 */

public class CRecyclerView<T> extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    protected int row = 8;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    protected BaseCEntity<T> model;
    protected View loadingView;
    protected View notDataView;
    protected View errorView;//无网络
    private View failView;//请求异常

    static class PageInfo {
        int page = 0;

        void nextPage() {
            page++;
        }

        void reset() {
            page = 0;
        }

        boolean isFirstPage() {
            return page == 0;
        }
    }
    private PageInfo pageInfo = new PageInfo();

    public CRecyclerView<T> setmCanReflush(boolean b) {
        this.mCanReflush = b;
        return this;
    }

    //控制能不能下拉刷新
    private boolean mCanReflush = true;


    protected Map<String, String> mParams;

    protected Map<String,Object> objectMap;


    protected boolean canLoadMore = true;

    protected Context mContext;
    private boolean isStarted;

    //是否显示 loadingView  errorView   notDataView
    private boolean showErrorEmptyLoading = true;
    private OnRequestListener mOnRequestListener;



    public CRecyclerView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public CRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        isStarted = false;
        View layout = LayoutInflater.from(getContext()).inflate(
                R.layout.crecyclerview_base, this);
//        layout.setLayoutParams(new LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        addView(layout);

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swiperefresh);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        loadingView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_loading, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_empty, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.findViewById(R.id.tv_empty).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        errorView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_error, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        failView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_fail, (ViewGroup) mRecyclerView.getParent(), false);
        failView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    public CRecyclerView<T> setLayoutManager(RecyclerView.LayoutManager layoutManager){
        mRecyclerView.setLayoutManager(layoutManager);
        return this;
    }
    public void setEmptyText(String text) {
        if (null != notDataView) {
            ((TextView) notDataView.findViewById(R.id.tv_empty)).setText(text);
        }
    }

    public CRecyclerView<T> setEmptyView(View view) {
        notDataView = view;
        notDataView.findViewById(R.id.tv_empty).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        return this;
    }

    public CRecyclerView<T> setFailView(View view){
        failView=view;
        failView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        return this;

    }

    public CRecyclerView<T> setErrorView(View view){
        errorView=view;
        errorView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        return this;

    }



    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    public void setAdapter(BaseQuickAdapter<T, BaseViewHolder> adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                request();
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                model.onClick(getContext(), mAdapter.getItem(position));
            }
        });
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                model.OnItemChildClick(adapter, view, position);
            }
        });
        if (showErrorEmptyLoading) mAdapter.setEmptyView(loadingView);
        mSwipeRefreshLayout.setEnabled(false);
    }

    public BaseQuickAdapter<T,BaseViewHolder> getBaseAdapter() {
        return mAdapter;
    }

    public CRecyclerView<T> setView(Class<? extends BaseCEntity<T>> cla) {
        try {
            model = (BaseCEntity<T>) cla.getConstructor().newInstance();
            model.setContext(mContext);
            if (null == mAdapter) setAdapter(new PullToRefreshAdapter());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    //需 setView 之后
    public CRecyclerView<T> addHeaderView(View view) {
        mAdapter.addHeaderView(view);
        return this;
    }

    //需 setView 之前
    public CRecyclerView<T> setShowErrorEmptyLoading(boolean b) {
        this.showErrorEmptyLoading = b;
        return this;
    }

    //需 setView 之后
    public CRecyclerView<T> addFooterView(View view) {
        mAdapter.addFooterView(view);
        return this;
    }

    /**
     * 设置空白页的提示
     *
     * @return
     */
    public CRecyclerView<T> setNotDataEmptyView(String text, int resouce) {
        if (notDataView == null) return this;
        TextView empty = (TextView) notDataView.findViewById(R.id.tv_empty);
        empty.setText(TextUtils.isEmpty(text) ? "" : text);
        Drawable drawable = mContext.getResources().getDrawable(resouce);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        empty.setCompoundDrawables(null, drawable, null, null);
        return this;
    }

    public CRecyclerView<T> addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.addOnScrollListener(listener);
        return this;
    }

    public CRecyclerView<T> setParams(String key, String value) {
        if (null == mParams) mParams = new HashMap<>();
        mParams.put(key, value);
        return this;
    }

    public String getParam(String key){
        if (null == mParams || !mParams.containsKey(key)) return "";

        return mParams.get(key);
    }

    public CRecyclerView<T> setObjectParams(String key, Object value) {
        if (null == objectMap) objectMap = new HashMap<>();
        objectMap.put(key, value);
        return this;
    }

    public CRecyclerView<T> setRow(int row) {
        this.row = row;
        return this;
    }

    public BaseCEntity<T> getModel(){
        return model;
    }

    //设置默认的 分割线
    public CRecyclerView<T> setItemDecoration() {
        if (null != mRecyclerView) {
            mRecyclerView.addItemDecoration(
                    new HorizontalDividerItemDecoration.Builder(getContext())
                            .color(getResources().getColor(R.color.background_default))
                            .sizeResId(R.dimen.divider)
                            .build());
        }
        return this;
    }

    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    public CRecyclerView<T> setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
        if (null != mAdapter) {
            mAdapter.getLoadMoreModule().setEnableLoadMore(canLoadMore);
        }
        return this;
    }

    public CRecyclerView<T> setItemDecoration(RecyclerView.ItemDecoration item) {
        if (null != mRecyclerView) {
            mRecyclerView.addItemDecoration(item);
        }
        return this;
    }

    protected boolean showLoadMoreEndStr = true;

    //canLoadMore = true 的情况下财会显示‘没有更多数据’
    public CRecyclerView<T> setShowLoadMoreEndStr(boolean show) {
        showLoadMoreEndStr = show;
        return this;
    }


    public CRecyclerView<T> start() {
        if (!isStarted) {
            if (!showErrorEmptyLoading)
                mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
            isStarted = true;
        }
        return this;
    }


    public CRecyclerView<T> start(boolean showProgress) {
        if (!isStarted) {
            if (!showErrorEmptyLoading && showProgress)
                mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
            isStarted = true;
        }
        return this;
    }

    public void reset() {
        isStarted = false;
    }



    public void reflush() {
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    public void reflush(boolean showProgress) {
        if (showProgress)
            mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }


    @Override
    public void onRefresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        // 下拉刷新，需要重置页数
        pageInfo.reset();


        if(null != mOnRequestListener){
            mOnRequestListener.onRefreshStart();
        }
        request();
    }

    private void request() {
        if (model == null) {
            Log.e("model", "null");
            return;
        }


        model.setParam(mParams);
        if (model.getPageAt(pageInfo.page, row) == null) return;


        if ((mAdapter.getData() == null || mAdapter.getData().size() <= 0) && showErrorEmptyLoading) {
            mAdapter.setEmptyView(loadingView);
            mSwipeRefreshLayout.setEnabled(false);
        }

        post(new Runnable() {
            @Override
            public void run() {
                model.getPageAt(pageInfo.page, row)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseResult<List<T>>>() {
                                       @Override
                                       public void accept(BaseResult<List<T>> subjects) throws Throwable {
                                           if (!subjects.success) {
                                               mAdapter.setList(null);
                                               if (showErrorEmptyLoading) {
                                                   mAdapter.setEmptyView(failView);
                                               }

                                               kickout(subjects);
                                           } else {

                                               if (pageInfo.isFirstPage()) {
                                                   //如果是加载的第一页数据，用 setData()
                                                   mAdapter.setList(subjects.getData());
                                               } else {
                                                   //不是第一页，则用add
                                                   mAdapter.addData(subjects.getData());
                                               }

                                               if (mAdapter.getData().size() == 0) {
                                                   if (null != mOnRequestListener) {
                                                       mOnRequestListener.onNoData();
                                                   }
                                                   if (onNoData() && showErrorEmptyLoading) {
                                                       mAdapter.setEmptyView(notDataView);
                                                   }
                                               } else {
                                                   if(null != subjects.getData() && subjects.getData().size() >= row){
                                                       mAdapter.getLoadMoreModule().loadMoreComplete();
                                                   }else{
                                                       mAdapter.getLoadMoreModule().loadMoreEnd(!showLoadMoreEndStr);
                                                       if(null != mOnRequestListener){
                                                           mOnRequestListener.onNoMoreDatas();
                                                       }
                                                   }
                                               }
                                           }
                                           mAdapter.getLoadMoreModule().setEnableLoadMore(canLoadMore);
                                           if (mSwipeRefreshLayout.isRefreshing()) {
                                               mSwipeRefreshLayout.postDelayed(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       mSwipeRefreshLayout.setRefreshing(false);
                                                   }
                                               }, 300);
                                           }
//                                           if (!showErrorEmptyLoading && !mSwipeRefreshLayout.isEnabled()) {
                                           mSwipeRefreshLayout.setEnabled(mCanReflush);
//                                           }
                                           if (null != mOnRequestListener) {
                                               mOnRequestListener.onRequestEnd();
                                           }
                                           // page加一
                                           pageInfo.nextPage();
                                       }


                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable e) throws Throwable {
                                           if (pageInfo.isFirstPage()) {
                                               //如果是加载的第一页数据，用 setData()
                                               mAdapter.setList(null);
                                               if (showErrorEmptyLoading) {
                                                   if (e instanceof SocketTimeoutException || e instanceof ConnectException || !isNetworkAvailable2(getContext())) {
                                                       mAdapter.setEmptyView(errorView);
                                                   } else {
                                                       mAdapter.setEmptyView(failView);
                                                   }
                                               }
                                               mAdapter.getLoadMoreModule().setEnableLoadMore(canLoadMore);

                                           }else{
                                               // 当前这次数据加载错误，调用此方法
                                               mAdapter.getLoadMoreModule().loadMoreFail();
                                           }



                                           e.printStackTrace();
                                           if (mSwipeRefreshLayout.isRefreshing()) {
                                               mSwipeRefreshLayout.postDelayed(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       mSwipeRefreshLayout.setRefreshing(false);
                                                   }
                                               }, 300);
                                           }
                                           mSwipeRefreshLayout.setEnabled(mCanReflush);
                                           if (null != mOnRequestListener) {
                                               mOnRequestListener.onRequestEnd();
                                           }
                                       }


                                   }
                        );
            }
        });
    }

    /**
     * Check if a network available
     */
    public static boolean isNetworkAvailable2(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }

    private void kickout(BaseResult<List<T>> subjects) {
        if (!TextUtils.isEmpty(subjects.getErrorCode()) && subjects.getErrorCode().equals(EventBusUtils.ErrorCode_login_out)) {//异地登录
            EventBusUtils.getInstance().kickOff(KICKOUTEvent.From_BACK);
        }
    }






    /**
     * 没有数据
     *
     * @return true 设置无数据页面     false 不设置无数据页面
     */
    protected boolean onNoData() {
        return true;
    }

    public void setDisloadmoreIFNotFullScreen() {
        if (null != mAdapter)
            mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }


    private class PullToRefreshAdapter extends BaseQuickAdapter<T, BaseViewHolder> implements LoadMoreModule {
          PullToRefreshAdapter() {
            super(model == null ? 0 : model.getItemLayou());

        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (model == null) {
                Log.e("model", "null");
                return;
            }
            model.convert(this,helper, item, helper.getLayoutPosition() - getHeaderLayoutCount());
        }
    }




    public interface OnRequestListener {
        void onRequestEnd();

        void onNoData();

        void onNoMoreDatas();

        void onRefreshStart();
    }

    public CRecyclerView<T> setOnRequestListener(OnRequestListener listener) {
        this.mOnRequestListener = listener;
        return this;
    }
}
