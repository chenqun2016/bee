package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.adapter.TagsCommentAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.widget.FlowTagLayout;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/28  15：38
 * 描述：
 */
public class CommentFragment extends BaseFragment {

    @BindView(R.id.tags)
    FlowTagLayout tags;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private Unbinder unbinder;
    CommentAdapter mAdapter;

    private static final int PAGE_SIZE = 7;
    private PageInfo pageInfo = new PageInfo();
    class PageInfo {
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

    @Override
    protected void getDatas() {

    }
    @Override
    public void onDestroy() {
        unbinder.unbind();

        super.onDestroy();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_store_comment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);

        initLoadMore();

        initTags();

        refresh();


    }

    private void initTags() {

        TagsCommentAdapter<String> tagsAdapter = new TagsCommentAdapter<>(getContext());

        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {

            }
        });

        List<String> dataSource = new ArrayList<>();
        dataSource.add("全部");
        dataSource.add("最新");
        dataSource.add("推荐");
        dataSource.add("吐槽");
        dataSource.add("有图");
        tagsAdapter.onlyAddAll(dataSource);
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        mAdapter.getLoadMoreModule().setAutoLoadMore(true);
    }

    /**
     * 刷新
     */
    private void refresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        // 下拉刷新，需要重置页数
        pageInfo.reset();
        request();
    }



    /**
     * 加载更多
     */
    private void loadMore() {
        request();
    }

    /**
     * 请求数据
     */
    private void request() {

        new Request(pageInfo.page, new RequestCallBack() {
            @Override
            public void success(List<CommentBean> data) {
                mAdapter.getLoadMoreModule().setEnableLoadMore(true);

                if (pageInfo.isFirstPage()) {
                    //如果是加载的第一页数据，用 setData()
                    mAdapter.setList(data);
                } else {
                    //不是第一页，则用add
                    mAdapter.addData(data);
                }

                if (data.size() < PAGE_SIZE) {
                    //如果不够一页,显示没有更多数据布局
                    mAdapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    mAdapter.getLoadMoreModule().loadMoreComplete();
                }

                // page加一
                pageInfo.nextPage();
            }

            @Override
            public void fail(Exception e) {
                mAdapter.getLoadMoreModule().setEnableLoadMore(true);
                mAdapter.getLoadMoreModule().loadMoreFail();
            }
        }).start();
    }


    /**
     * 模拟加载数据的类，不用特别关注
     */
    static class Request extends Thread {
        private int             mPage;
        private RequestCallBack mCallBack;
        private Handler mHandler;

        private static boolean mFirstPageNoMore;
        private static boolean mFirstError = true;

        public Request(int page, RequestCallBack callBack) {
            mPage = page;
            mCallBack = callBack;
            mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void run() {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }

            if (mPage == 2 && mFirstError) {
                mFirstError = false;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.fail(new RuntimeException("fail"));
                    }
                });
            } else {
                int size = PAGE_SIZE;
                if (mPage == 1) {
                    if (mFirstPageNoMore) {
                        size = 1;
                    }
                    mFirstPageNoMore = !mFirstPageNoMore;
                    if (!mFirstError) {
                        mFirstError = true;
                    }
                } else if (mPage == 4) {
                    size = 1;
                }

                final int dataSize = size;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.success(getSampleData(dataSize));
                    }
                });
            }
        }
    }
    static public List<CommentBean> getSampleData(int lenth) {
        List<CommentBean> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            CommentBean status = new CommentBean();

            list.add(status);
        }
        return list;
    }
    interface RequestCallBack {
        /**
         * 模拟加载成功
         *
         * @param data 数据
         */
        void success(List<CommentBean> data);

        /**
         * 模拟加载失败
         *
         * @param e 错误信息
         */
        void fail(Exception e);
    }
}
