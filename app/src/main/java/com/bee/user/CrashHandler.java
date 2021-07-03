package com.bee.user;

/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：异常处理类
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static final String TAG = CrashHandler.class.getSimpleName();
    private static CrashHandler INSTANCE = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
    private CrashHandler() {
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (mDefaultExceptionHandler != null) { //交给系统的UncaughtExceptionHandler处理
            mDefaultExceptionHandler.uncaughtException(thread, throwable);

            android.os.Process.killProcess(android.os.Process.myPid()); //主动杀死进程
        } else {
            android.os.Process.killProcess(android.os.Process.myPid()); //主动杀死进程
        }
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init() {
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();//获取当前默认ExceptionHandler，保存在全局对象
        Thread.setDefaultUncaughtExceptionHandler(this);//替换默认对象为当前对象
    }
}
