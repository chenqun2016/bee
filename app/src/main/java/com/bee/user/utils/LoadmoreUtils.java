package com.bee.user.utils;
import android.os.Handler;
import android.os.Looper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/20  13：33
 * 描述：Recyclerview 加载更多封装类。
 */
public class LoadmoreUtils {
    public  Class aClass;//用于创建测试数据。接入接口后可以删除。

    public LoadmoreUtils(Class c) {
        aClass = c;
    }

    //TODO
    private static final int PAGE_SIZE = 3;


    PageInfo pageInfo = new PageInfo();


    static  class PageInfo {
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

    public  void initLoadmore(BaseQuickAdapter adapter){
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore(adapter);
            }
        });
        adapter.getLoadMoreModule().setAutoLoadMore(true);
    }


    public void refresh(BaseQuickAdapter mAdapter) {
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        // 下拉刷新，需要重置页数
        pageInfo.reset();
        request(mAdapter);
    }

    /**
     * 加载更多
     */
    private void loadMore(BaseQuickAdapter mAdapter) {
        request(mAdapter);
    }
    /**
     * 请求数据
     */
    private  void request(BaseQuickAdapter mAdapter) {


        new Request(pageInfo.page, new RequestCallBack() {
            @Override
            public void success(List data) {
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
     * 以下用于创建测试数据。接入接口后可以删除。
     */

    public   boolean mFirstPageNoMore;
    public  boolean mFirstError = true;
    /**
     * 模拟加载数据的类，不用特别关注
     */
     class Request extends Thread {
        private int             mPage;
        private RequestCallBack mCallBack;
        private Handler mHandler;



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

    private  List getSampleData(int lenth) {

        List list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {

            try {
                list.add( aClass.newInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    interface RequestCallBack {
        /**
         * 模拟加载成功
         *
         * @param data 数据
         */
        void success(List data);

        /**
         * 模拟加载失败
         *
         * @param e 错误信息
         */
        void fail(Exception e);
    }
}