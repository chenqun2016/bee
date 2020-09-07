package com.bee.user.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.bee.user.BeeApplication;

public class ToastUtil {


    public static void ToastLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void ToastShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void ToastShort(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showSafeToast(final Activity activity, final String msg) {
        if (Thread.currentThread().getName().equals("main")) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static long time = 0;

    public static void ToastShortFromNet(String text) {
        if (System.currentTimeMillis() - time >= 2000) {//吐司的间隔时间
            Toast.makeText(BeeApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        }
    }
}
