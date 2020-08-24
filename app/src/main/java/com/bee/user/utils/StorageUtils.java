package com.bee.user.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：存储工具类
 */
public class StorageUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    private static final String SDCARDPATH = "/huaxia/finance/pic/";
    private static final String CONTRACT_SDCARDPATH = "/huaxia/contract/";
    private static final String ZHAIQUAN_SDCARDPATH = "/huaxia/zhaiquan/";
    private static final String PDF_SDCARDPATH = "/huaxia/pdf/";

    private static String getCachePath(Context context){
        String filePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {//手机有sd卡
            File externalCacheDir = context.getExternalCacheDir();
            if(null != externalCacheDir){
                filePath = externalCacheDir.getAbsolutePath();
            }else{
                filePath = context.getCacheDir().getAbsolutePath();
            }

        }else{
            filePath = context.getCacheDir().getAbsolutePath();
        }
        return filePath ;
    }

    private static String getStoragePath(Context context){
        String filePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)){//手机有sd卡
            File externalDir = Environment.getExternalStorageDirectory();
            if(null != externalDir){
                filePath = externalDir.getAbsolutePath();
            }else{
                filePath = context.getFilesDir().getAbsolutePath();
            }

        }else{
            filePath = context.getFilesDir().getAbsolutePath();
        }
        return filePath ;
    }

    public static String getSdcardpath(Context context){
        return getStoragePath(context)+SDCARDPATH;
    }
    public static String getContractSdcardpath(Context context){
        return getStoragePath(context)+CONTRACT_SDCARDPATH;
    }

    public static String getZhaiquanSdcardpath(Context context){
        return getStoragePath(context)+ZHAIQUAN_SDCARDPATH;
    }

    public static String getPDFSdcardpath(Context context){
        return getCachePath(context)+PDF_SDCARDPATH;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
