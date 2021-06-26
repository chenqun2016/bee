package com.bee.user.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 - @Description: 
 - @Author:  bixueyun
 - @Time:  2021/6/24 下午2:58
 */
public class MyExecutors {

    private MyExecutors() {
    }

    private static class MyExecutorsInner {
        private static ExecutorService threadPool = Executors.newCachedThreadPool();
        private static MyExecutors myExecutors = new MyExecutors();
    }

    public static ExecutorService getThreadPool() {
        return MyExecutorsInner.threadPool;
    }

    public static MyExecutors getInstance() {
        return MyExecutorsInner.myExecutors;
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (getThreadPool() != null) {
            getThreadPool().execute(runnable);
        }
    }

    // 从线程队列中移除对象
    public void cancel() {
        if (getThreadPool() != null) {
            getThreadPool().shutdown();
        }
    }

    // 从线程队列中移除对象
    public void cancelAll() {
        if (getThreadPool() != null) {
            getThreadPool().shutdownNow();
        }
    }
}
