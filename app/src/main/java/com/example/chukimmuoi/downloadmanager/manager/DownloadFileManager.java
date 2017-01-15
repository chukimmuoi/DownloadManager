package com.example.chukimmuoi.downloadmanager.manager;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coolerfall.download.DownloadManager;
import com.coolerfall.download.DownloadRequest;
import com.coolerfall.download.Logger;
import com.coolerfall.download.OkHttpDownloader;
import com.coolerfall.download.Priority;
import com.coolerfall.download.URLDownloader;
import com.example.chukimmuoi.downloadmanager.callback.DownloadExecuteCallback;
import com.example.chukimmuoi.downloadmanager.utils.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author : Hanet Electronics
 * @Skype : chukimmuoi
 * @Mobile : +84 167 367 2505
 * @Email : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : DownloadManager
 * Created by chukimmuoi on 15/01/2017.
 */

public class DownloadFileManager {

    private final String TAG = DownloadFileManager.class.getSimpleName();

    private static DownloadFileManager ourInstance;

    private DownloadManager mDownloadManager;

    private SparseIntArray ids;

    private static final String[] PERMISSION
            = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static DownloadFileManager getInstance() {
        if (ourInstance == null) {
            synchronized (DownloadFileManager.class) {
                ourInstance = new DownloadFileManager();
            }
        }
        return ourInstance;
    }

    public DownloadFileManager() {
        this.ids = new SparseIntArray();
    }

    public DownloadFileManager onCreate(Context context, boolean isOkHttp, int poolSize) {
        initialDownLoad(context, isOkHttp, poolSize);
        return ourInstance;
    }

    public void onStartDownload(Context context, int index, String url,
                                ProgressBar progressBar, TextView textView,
                                Priority priority,
                                String destinationDirectory) {
        onStartDownload(context, index, url, progressBar, textView, priority,
                destinationDirectory, null);
    }

    public void onStartDownload(Context context, int index, String url,
                                ProgressBar progressBar, TextView textView,
                                Priority priority,
                                String destinationDirectory, String filePath) {
        if (!EasyPermissions.hasPermissions(context, PERMISSION)) {
            EasyPermissions.requestPermissions(this, "Download need external permission", 0x01,
                    PERMISSION);
            return;
        }

        if (!TextUtils.isEmpty(destinationDirectory)) {
            File file = new File(destinationDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (TextUtils.isEmpty(filePath)) {
                startDownloadDirectory(index, url, progressBar, textView, priority,
                        destinationDirectory);
            } else {
                String destinationFilePath = destinationDirectory + File.separator + filePath;
                startDownloadFilePath(index, url, progressBar, textView, priority,
                        destinationFilePath);
            }
        } else {
            startDownloadDefault(index, url, progressBar, textView, priority);
        }
    }


    public void onDestroy() {
        if (ids != null) {
            ids.clear();
            ids = null;
        }
        if (mDownloadManager != null) {
            mDownloadManager.cancelAll();
            mDownloadManager.release();
            mDownloadManager = null;
        }
        if (ourInstance != null) {
            ourInstance = null;
        }
    }


    private void initialDownLoad(Context context, boolean isOkHttp, int poolSize) {
        initialDownload(context, isOkHttp, null, poolSize);
    }

    private void initialDownload(Context context, boolean isOkHttp,
                                 OkHttpClient okHttpClient, int poolSize) {
        mDownloadManager = new DownloadManager.Builder().context(context)
                .downloader(isOkHttp
                        ? OkHttpDownloader.create(okHttpClient)
                        : URLDownloader.create())
                .threadPoolSize(poolSize)
                .logger(new Logger() {
                    @Override
                    public void log(String message) {
                        LogUtils.d(TAG, message);
                    }
                }).build();
    }


    private void startDownloadDirectory(int index, String url,
                                        ProgressBar progressBar, TextView textView,
                                        Priority priority, String directory) {
        try {
            int id = ids.get(index, -1);
            if (mDownloadManager.isDownloading(id)) {
                mDownloadManager.cancel(id);
            } else {
                DownloadRequest request = new DownloadRequest.Builder()
                        .url(url)
                        .downloadCallback(new DownloadExecuteCallback(progressBar, textView))
                        .retryTime(5)
                        .retryInterval(3, TimeUnit.SECONDS)
                        .progressInterval(1, TimeUnit.SECONDS)
                        .priority(priority)
                        .allowedNetworkTypes(DownloadRequest.NETWORK_WIFI)
                        .destinationDirectory(directory)
                        .build();
                int downloadId = mDownloadManager.add(request);
                ids.put(index, downloadId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startDownloadFilePath(int index, String url,
                                       ProgressBar progressBar, TextView textView,
                                       Priority priority, String filePath) {
        try {
            int id = ids.get(index, -1);
            if (mDownloadManager.isDownloading(id)) {
                mDownloadManager.cancel(id);
            } else {
                DownloadRequest request = new DownloadRequest.Builder()
                        .url(url)
                        .downloadCallback(new DownloadExecuteCallback(progressBar, textView))
                        .retryTime(5)
                        .retryInterval(3, TimeUnit.SECONDS)
                        .progressInterval(1, TimeUnit.SECONDS)
                        .priority(priority)
                        .allowedNetworkTypes(DownloadRequest.NETWORK_WIFI)
                        .destinationFilePath(filePath)
                        .build();
                int downloadId = mDownloadManager.add(request);
                ids.put(index, downloadId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startDownloadDefault(int index, String url,
                                      ProgressBar progressBar, TextView textView,
                                      Priority priority) {
        try {
            int id = ids.get(index, -1);
            if (mDownloadManager.isDownloading(id)) {
                mDownloadManager.cancel(id);
            } else {
                DownloadRequest request = new DownloadRequest.Builder()
                        .url(url)
                        .downloadCallback(new DownloadExecuteCallback(progressBar, textView))
                        .retryTime(5)
                        .retryInterval(3, TimeUnit.SECONDS)
                        .progressInterval(1, TimeUnit.SECONDS)
                        .priority(priority)
                        .allowedNetworkTypes(DownloadRequest.NETWORK_WIFI)
                        .build();
                int downloadId = mDownloadManager.add(request);
                ids.put(index, downloadId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
