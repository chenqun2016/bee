package com.bee.user.service;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.bee.user.utils.DeviceUtils;
import com.bee.user.utils.LogUtil;
import com.blankj.utilcode.util.AppUtils;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

/**
 - @Description: 版本更新下载
 - @Author:  bixueyun
 - @Time:  2021/4/14 下午9:12
 */
public class CheckUpdateService extends JobIntentService {
    DownloadManager downloadManager;
    long mTaskId;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public CheckUpdateService() {
        super();
    }

    /**
     * 这个Service 唯一的id
     */
    static final int JOB_ID = 10111011;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, CheckUpdateService.class, JOB_ID, work);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
       String url =  intent.getStringExtra("url");
        downloadAPK(url);
    }

    private void downloadAPK(String url) {
        DeviceUtils.clearApk(getApplicationContext(), "beeUser.apk");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);

        request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS , "beeUser.apk");

        downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        mTaskId = downloadManager.enqueue(request);
        getApplicationContext().registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();
        }
    };

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    LogUtil.i("下载成功");
                    File file = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS ), "beeUser.apk");
                    AppUtils.installApp(file);
                    break;
                case DownloadManager.STATUS_FAILED:
                    LogUtil.i("下载失败");
                    break;
            }
        }
    }




}
