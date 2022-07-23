package com.bee.user;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.multidex.MultiDex;

import com.bee.user.utils.LogUtil;
import com.bee.user.vm.AppViewModelStore;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

public class BeeApplication extends Application {
    private static BeeApplication mInstance;
    public static BeeApplication getInstance() {
        return mInstance;
    }


    private volatile boolean mBackground = true;

    public boolean getIsInBackground() {
        return mBackground;
    }

    public void setIsInBackground(boolean b) {
        mBackground = b;
    }

    private static AppViewModelStore appViewModelStore;
    public static AppViewModelStore appVMStore() {
        return appViewModelStore;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            mBackground = true;
        }
        if (level >= TRIM_MEMORY_UI_HIDDEN) {
//            ImageUtils.getInstance().clearMemoryCache();
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    //**********************设置字体不随系统变化*****************************
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
    //***************************************************


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mInstance = this;
            appViewModelStore = new AppViewModelStore(this);
            initBugly();

            //***********设置字体不随系统变化**********************
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            displayMetrics.scaledDensity = displayMetrics.density;
            //***************************************

            init();
            JPushInterface.setDebugMode(BuildConfig.DEBUG);
            JPushInterface.init(this);
//        if (getApplicationInfo().packageName.equals(CommonUtil.getCurProcessName(getApplicationContext())))
//            EventBus.getDefault().register(this);
        } catch (Exception e) {
            LogUtil.d(e.getMessage());
        }
    }


    private void initBugly() {
        CrashHandler.getInstance().init();
        CrashReport.initCrashReport(getApplicationContext(), "8a3adb0fad", true);
    }


    private void init() {

    }
}
