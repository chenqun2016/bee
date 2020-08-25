//package com.huaxiafinance.www.crecyclerview.crecyclerView;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.viewholder.BaseViewHolder;
//import com.huaxiafinance.www.crecyclerview.R;
//import com.huaxiafinance.www.crecyclerview.itemdecoration.HorizontalDividerItemDecoration;
//
//import java.net.ConnectException;
//import java.net.SocketTimeoutException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//
//
///**
// * 创建时间：2019/4/18
// * 编写人： chenqun
// * 功能描述：
// */
//public class CRecyclerView2<T> extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
//    protected int row = 8;
//
//    protected SwipeRefreshLayout mSwipeRefreshLayout;
//    protected RecyclerView mRecyclerView;
//    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
//    protected BaseCEntity<T> model;
//    protected View loadingView;
//    protected View notDataView;
//    protected View errorView;//无网络
//    private View failView;//请求异常
//
//
//
//    private Boolean canReflush = true;
//
//    protected Map<String, String> mParams;
//
//
//    protected boolean canLoadMore = true;
//    protected OnRequestStartListener mOnRequestStartListener;
//
//    protected Context mContext;
//    private boolean isStarted;
//
//    //是否显示 loadingView  errorView   notDataView
//    private boolean showErrorEmptyLoading = true;
//    private OnRequestListener mOnRequestListener;
//
//    private SimpleClickListener simpleClickListener = new SimpleClickListener() {
//        @Override
//        public void onItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//            model.onClick(getContext(), mAdapter.getItem(position));
//        }
//
//        @Override
//        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
//        }
//
//        @Override
//        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//            model.OnItemChildClick(adapter, view, position);
//        }
//
//        @Override
//        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
//        }
//    };
//
//
//    public CRecyclerView2(Context context) {
//        this(context, null);
//        this.mContext = context;
//    }
//
//    public CRecyclerView2(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.mContext = context;
//        init(context);
//    }
//
//    private void init(Context context) {
//        isStarted = false;
//        View layout = LayoutInflater.from(getContext()).inflate(
//                R.layout.crecyclerview_base, this);
////        layout.setLayoutParams(new LayoutParams(
////                ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.MATCH_PARENT));
////        addView(layout);
//
//        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swiperefresh);
//        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                R.color.colorPrimaryDark);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//
//        mSwipeRefreshLayout.setEnabled(canReflush);
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.addOnItemTouchListener(simpleClickListener);
//
//        loadingView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_loading, (ViewGroup) mRecyclerView.getParent(), false);
//        notDataView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_empty, (ViewGroup) mRecyclerView.getParent(), false);
//        notDataView.findViewById(R.id.tv_empty).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRefresh();
//            }
//        });
//
//        errorView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_error, (ViewGroup) mRecyclerView.getParent(), false);
//        errorView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRefresh();
//            }
//        });
//
//        failView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_fail, (ViewGroup) mRecyclerView.getParent(), false);
//        failView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRefresh();
//            }
//        });
//    }
//
//    public CRecyclerView2 setLayoutManager(RecyclerView.LayoutManager layoutManager){
//        mRecyclerView.setLayoutManager(layoutManager);
//        return this;
//    }
//    public void setEmptyText(String text) {
//        if (null != notDataView) {
//            ((TextView) notDataView.findViewById(R.id.tv_empty)).setText(text);
//        }
//    }
//
//    public View getEmptyView(){
//        return notDataView;
//    }
//
//    public CRecyclerView2 setEmptyView(View view) {
//        notDataView = view;
//        if(null != notDataView){
//            View tv_empty = notDataView.findViewById(R.id.tv_empty);
//            if(null != tv_empty){
//                tv_empty.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onRefresh();
//                    }
//                });
//            }
//        }
//
//        return this;
//    }
//
//    public CRecyclerView2 setFailView(View view){
//        failView=view;
//        failView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRefresh();
//            }
//        });
//        return this;
//
//    }
//
//    public CRecyclerView2 setStartFlag(boolean flag) {
//        this.isStarted = flag;
//        return this;
//    }
//    public CRecyclerView2 setErrorView(View view){
//        errorView=view;
//        errorView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRefresh();
//            }
//        });
//        return this;
//
//    }
//    public void notifyItemClear() {
//        if (mAdapter == null) return;
//        mAdapter.getData().clear();
//        mAdapter.notifyDataSetChanged();
//    }
//    public void notifyItemRemovedPosition(int position) {
//        if (mAdapter == null) return;
//        mAdapter.getData().remove(position);
//        mAdapter.notifyDataSetChanged();
//    }
//    public void notifyItemRemovedPosition(Object o) {
//        if (mAdapter == null) return;
//        if(null!=o) mAdapter.getData().remove(o);
//        mAdapter.notifyDataSetChanged();
//    }
//
//    public RecyclerView getRecyclerView() {
//        return mRecyclerView;
//    }
//
//
//    public void setAdapter(BaseQuickAdapter<T, BaseViewHolder> adapter) {
//        this.mAdapter = adapter;
//        this.mAdapter.setOnLoadMoreListener(this, mRecyclerView);
////        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        mRecyclerView.setAdapter(adapter);
//
//        if (showErrorEmptyLoading) mAdapter.setEmptyView(loadingView);
//        mSwipeRefreshLayout.setEnabled(false);
//    }
//
//    public BaseQuickAdapter getBaseAdapter() {
//        return mAdapter;
//    }
//
//    public CRecyclerView2 setView(Class<? extends BaseCEntity> cla) {
//        try {
//            model = (BaseCEntity<T>) cla.getConstructor().newInstance();
//            model.setContext(mContext);
//            if (null == mAdapter) setAdapter(new PullToRefreshAdapter());
//            bindParamsToModel();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this;
//    }
//
//    public void setCanReflush(Boolean canReflush){
//        this.canReflush = canReflush;
//    }
//    //需 setView 之后
//    public CRecyclerView2 addHeaderView(View view) {
//        mAdapter.addHeaderView(view);
//        return this;
//    }
//
//    //需 setView 之前
//    public CRecyclerView2 setShowErrorEmptyLoading(boolean b) {
//        this.showErrorEmptyLoading = b;
//        return this;
//    }
//
//    //需 setView 之后
//    public CRecyclerView2 addFooterView(View view) {
//        mAdapter.addFooterView(view);
//        return this;
//    }
//
//    /**
//     * 设置空白页的提示
//     *
//     * @return
//     */
//    public CRecyclerView2 setNotDataEmptyView(String text, int resouce) {
//        if (notDataView == null) return this;
//        TextView empty = (TextView) notDataView.findViewById(R.id.tv_empty);
//        empty.setText(TextUtils.isEmpty(text) ? "" : text);
//        Drawable drawable = mContext.getResources().getDrawable(resouce);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        empty.setCompoundDrawables(null, drawable, null, null);
//        return this;
//    }
//
//    public CRecyclerView2 addOnScrollListener(RecyclerView.OnScrollListener listener) {
//        mRecyclerView.addOnScrollListener(listener);
//        return this;
//    }
//
//    public CRecyclerView2 setParams(String key, String value) {
//        if (null == mParams) mParams = new HashMap<>();
//        mParams.put(key, value);
//        bindParamsToModel();
//        return this;
//    }
//
//    public CRecyclerView2 setParams(HashMap<String,String> map) {
//        if(null != map && map.size()>0){
//            if (null == mParams) mParams = new HashMap<>();
//
//            mParams.putAll(map);
//            bindParamsToModel();
//        }
//
//        return this;
//    }
//
//    private CRecyclerView2 bindParamsToModel(){
//        if(null != model && null != mParams){
//            model.setParam(mParams);
//        }
//        return this;
//    }
//
//    public CRecyclerView2 setRow(int row) {
//        this.row = row;
//        return this;
//    }
//
//    public BaseCEntity<T> getModel(){
//        return model;
//    }
//
//    //设置默认的 分割线
//    public CRecyclerView2 setItemDecoration() {
//        if (null != mRecyclerView) {
//            mRecyclerView.addItemDecoration(
//                    new HorizontalDividerItemDecoration.Builder(getContext())
//                            .color(getResources().getColor(R.color.background_default))
//                            .sizeResId(R.dimen.divider)
//                            .build());
//        }
//        return this;
//    }
//
//    public void scrollToPosition(int position) {
//        mRecyclerView.scrollToPosition(position);
//    }
//
//    public CRecyclerView2 setCanLoadMore(boolean canLoadMore) {
//        this.canLoadMore = canLoadMore;
//        if (null != mAdapter) {
//            mAdapter.setEnableLoadMore(canLoadMore);
//        }
//        return this;
//    }
//
//    public CRecyclerView2 setItemDecoration(RecyclerView.ItemDecoration item) {
//        if (null != mRecyclerView) {
//            mRecyclerView.addItemDecoration(item);
//        }
//        return this;
//    }
//
//    protected boolean showLoadMoreEndStr = true;
//
//    //canLoadMore = true 的情况下财会显示‘没有更多数据’
//    public CRecyclerView2 setShowLoadMoreEndStr(boolean show) {
//        showLoadMoreEndStr = show;
//        return this;
//    }
//
//
//    public CRecyclerView2 start() {
//        if (!isStarted) {
//            if (!showErrorEmptyLoading)
//                mSwipeRefreshLayout.setRefreshing(true);
//            onRefresh();
//            isStarted = true;
//        }
//        return this;
//    }
//
//
//    public CRecyclerView2 start(boolean showProgress) {
//        if (!isStarted) {
//            if (!showErrorEmptyLoading && showProgress)
//                mSwipeRefreshLayout.setRefreshing(true);
//            onRefresh();
//            isStarted = true;
//        }
//        return this;
//    }
//
//    public void reset() {
//        isStarted = false;
//    }
//
//    protected int index = 1;
//
//    public void setPageIndex(int a) {
//        this.index = a;
//    }
//
//    public void reflush() {
//        mSwipeRefreshLayout.setRefreshing(true);
//        onRefresh();
//    }
//
//    public void reflush(boolean showProgress) {
//        if (showProgress)
//            mSwipeRefreshLayout.setRefreshing(true);
//        onRefresh();
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        if (model == null) {
//            Log.e("model", "null");
//            return;
//        }
//        model.setParam(mParams);
//        if (model.getPageAt(index, row) == null) return;
//
//        index++;
//
//        mSwipeRefreshLayout.setEnabled(false);
//        post(new Runnable() {
//            @Override
//            public void run() {
//                if(null != mRefreshAndLoadmoreMethod){
//                    mRefreshAndLoadmoreMethod.setLoadmoreMethod(model,index,row);
//                    return;
//                }
//                model.getPageAt(index, row)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<BaseResult<List<T>>>() {
//                                       @Override
//                                       public void call(BaseResult<List<T>> subjects) {
//                                           loadSuccess(subjects,subjects.getData());
//
//                                       }
//                                   }, new Action1<Throwable>() {
//                                       @Override
//                                       public void call(Throwable e) {
//                                           loadFail(e);
//
//                                       }
//                                   }
//                        );
//            }
//        });
//    }
//
//    @Override
//    public void onRefresh() {
//        if (model == null) {
//            Log.e("model", "null");
//            return;
//        }
//        if (null != mOnRequestStartListener) {
//            mOnRequestStartListener.onRequestStart();
//        }
//        onRefreshStart();
//        if(null != mOnRequestListener){
//            mOnRequestListener.onRefreshStart();
//        }
//        model.setParam(mParams);
//        if (model.getPageAt(index, row) == null) return;
//
//        index = 1;
//
//        if ((mAdapter.getData() == null || mAdapter.getData().size() <= 0) && showErrorEmptyLoading) {
//            mAdapter.setEmptyView(loadingView);
//            mSwipeRefreshLayout.setEnabled(false);
//        }
//
//        mAdapter.setEnableLoadMore(false);
//
//        post(new Runnable() {
//            @Override
//            public void run() {
//                if(null != mRefreshAndLoadmoreMethod){
//                    mRefreshAndLoadmoreMethod.setRefreshMethod(model,index,row);
//                    return;
//                }
//
//                model.getPageAt(index, row)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<BaseResult<List<T>>>() {
//                                       @Override
//                                       public void call(BaseResult<List<T>> subjects) {
//                                           requestSuccess(subjects,subjects.getData());
//
//                                       }
//                                   }, new Action1<Throwable>() {
//                                       @Override
//                                       public void call(Throwable e) {
//                                           requestFail(e);
//                                       }
//                                   }
//                        );
//            }
//        });
//    }
//
//    /**
//     * Check if a network available
//     */
//    public static boolean isNetworkAvailable2(Context context) {
//        boolean connected = false;
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm != null) {
//            NetworkInfo ni = cm.getActiveNetworkInfo();
//            if (ni != null) {
//                connected = ni.isConnected();
//            }
//        }
//        return connected;
//    }
//
//    private void kickout(BaseResult<List<T>> subjects) {
//        if (!TextUtils.isEmpty(subjects.getErrorCode()) && subjects.getErrorCode().equals(EventBusUtils.ErrorCode_login_out)) {//异地登录
//            EventBusUtils.getInstance().kickOff(KICKOUTEvent.From_BACK);
//        }
//    }
//
//
//    /**
//     * 没有数据
//     *
//     * @return true 设置无数据页面     false 不设置无数据页面
//     */
//    protected boolean onNoData() {
//        return true;
//    }
//
//    public void setDisloadmoreIFNotFullScreen() {
//        if (null != mAdapter)
//            mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
//    }
//
//    public void requestSuccess(BaseResult subjects, List<T> beans) {
//        if (!subjects.getSuccess()) {
//            mAdapter.setNewData(null);
//            if (showErrorEmptyLoading) {
//                mAdapter.setEmptyView(failView);
//            }
//
//            kickout(subjects);
//        } else {
//            mAdapter.setNewData(beans);
//            if (mAdapter.getData().size() == 0) {
//                if (null != mOnRequestListener) {
//                    mOnRequestListener.onNoData();
//                }
//                if (onNoData() && showErrorEmptyLoading) {
//                    mAdapter.setEmptyView(notDataView);
//                }
//            } else {
//                if (null != subjects.getData() && beans.size() < row) {
//                    onNoMoreDatas();
//                    if(null != mOnRequestListener){
//                        mOnRequestListener.onNoMoreDatas();
//                    }
//                    mAdapter.loadMoreEnd(!showLoadMoreEndStr);
//                } else {
//                    if (!canLoadMore) {
//                        onNoMoreDatas();
//                        if(null != mOnRequestListener){
//                            mOnRequestListener.onNoMoreDatas();
//                        }
//                    }
//                }
//                if (!mSwipeRefreshLayout.isEnabled()) {
//                     mSwipeRefreshLayout.setEnabled(canReflush);
//                }
//
//            }
//        }
//        mAdapter.setEnableLoadMore(canLoadMore);
//        if (mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }, 300);
//        }
//        if (!mSwipeRefreshLayout.isEnabled()) {
//              mSwipeRefreshLayout.setEnabled(canReflush);
//        }
//        onRequestEnd();
//        if (null != mOnRequestListener) {
//            mOnRequestListener.onRequestEnd();
//        }
//
//    }
//
//    public void requestFail(Throwable e) {
//        mAdapter.setNewData(null);
//        if (showErrorEmptyLoading) {
//            if (e instanceof SocketTimeoutException || e instanceof ConnectException || !isNetworkAvailable2(getContext())) {
//                mAdapter.setEmptyView(errorView);
//            } else {
//                mAdapter.setEmptyView(failView);
//            }
//        }
//        mAdapter.setEnableLoadMore(canLoadMore);
//        e.printStackTrace();
//        if (mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }, 300);
//        }
//        if (!showErrorEmptyLoading && !mSwipeRefreshLayout.isEnabled()) {
//              mSwipeRefreshLayout.setEnabled(canReflush);
//        }
//        onRequestEnd();
//        if (null != mOnRequestListener) {
//            mOnRequestListener.onRequestEnd();
//        }
//    }
//    public void loadSuccess(BaseResult subjects, List<T> beans) {
//        if (!subjects.getSuccess()) {
//            index--;
//            mAdapter.loadMoreFail();
//            kickout(subjects);
//        } else {
////                                               List<T> datas = subjects.getData();
//            if (null != subjects.getData()) {
//                mAdapter.addData(beans);
//            }
//
//            if (null != subjects.getData() && beans.size() >= row) {
//                mAdapter.loadMoreComplete();
//            } else {
//                onNoMoreDatas();
//                if(null != mOnRequestListener){
//                    mOnRequestListener.onNoMoreDatas();
//                }
//                mAdapter.loadMoreEnd(!showLoadMoreEndStr);
//            }
//
//            //自动滑动到下一个
////                                               if (datas.size() > 0) {
////                                                   mRecyclerView.scrollToPosition((mAdapter.getData().size() - datas.size()));
////                                               }
//        }
//         mSwipeRefreshLayout.setEnabled(canReflush);
//    }
//
//    public void loadFail(Throwable throwable) {
//        index--;
//        mAdapter.loadMoreFail();
//         mSwipeRefreshLayout.setEnabled(canReflush);
//        throwable.printStackTrace();
//    }
//
//
//    //BaseMultiItemQuickAdapter 内容需要更改
//    private class PullToRefreshAdapter extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {
//        PullToRefreshAdapter() {
//            super(model == null ? 0 : model.getItemLayou());//单一item状态下
//            if (model != null && model.getItemLayou() == 0) {//multi item状态下
//                model.addItemType(this);
//            }
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, T item) {
//            if (model == null) {
//                Log.e("model", "null");
//                return;
//            }
//            model.convert(this,helper, item, helper.getLayoutPosition() - getHeaderLayoutCount());
//        }
//    }
//
//    public interface OnRequestStartListener {
//        void onRequestStart();
//    }
//
//    public CRecyclerView2 setOnRequestStartListener(OnRequestStartListener listener) {
//        this.mOnRequestStartListener = listener;
//        return this;
//    }
//
//    public interface OnRequestListener {
//        void onRequestEnd();
//
//        void onNoData();
//
//        void onNoMoreDatas();
//
//        void onRefreshStart();
//    }
//
//    public CRecyclerView2 setOnRequestListener(OnRequestListener listener) {
//        this.mOnRequestListener = listener;
//        return this;
//    }
//
//    /**
//     * 返回结果code
//     */
////    OnResultCodeListener onResultCodeListener;
//
////    public void setOnResultCodeListener(OnResultCodeListener listener) {
////        onResultCodeListener = listener;
////    }
//
//
//    public interface SetRefreshAndLoadmoreMethod<T> {
//        void setRefreshMethod(BaseCEntity<T> model, int index, int row);
//
//        void setLoadmoreMethod(BaseCEntity<T> model, int index, int row);
//    }
//
//    private SetRefreshAndLoadmoreMethod mRefreshAndLoadmoreMethod ;
//
//    public CRecyclerView2 setRefreshAndLoadmoreMethod(SetRefreshAndLoadmoreMethod method) {
//        mRefreshAndLoadmoreMethod = method;
//        return this;
//    }
//}