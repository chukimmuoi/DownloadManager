package com.example.chukimmuoi.downloadmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coolerfall.download.DownloadManager;
import com.coolerfall.download.DownloadRequest;
import com.coolerfall.download.Logger;
import com.coolerfall.download.OkHttpDownloader;
import com.coolerfall.download.Priority;
import com.example.chukimmuoi.downloadmanager.callback.DownloadExecuteCallback;
import com.example.chukimmuoi.downloadmanager.constanst.SystemConstanst;
import com.example.chukimmuoi.downloadmanager.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
                          implements SystemConstanst, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView    mTextView;

    private ProgressBar mProgressBar;

    private Button      mButton;

    private DownloadManager mDownloadManager;

    private static final String[] PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private SparseIntArray ids = new SparseIntArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtils.setLog(IS_DEBUG);

        initialUI();

        initialDownloadManager();
    }

    private void initialUI() {
        mTextView = (TextView) findViewById(R.id.tv_test);

        mProgressBar = (ProgressBar) findViewById(R.id.prg_test);

        mButton = (Button) findViewById(R.id.btn_test);
        mButton.setOnClickListener(this);
    }

    private void initialDownloadManager() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        mDownloadManager    = new DownloadManager.Builder().context(this)
                .downloader(OkHttpDownloader.create(client))
                .threadPoolSize(NUMBER_THREAD_POOL_SIZE_DOWNLOAD)
                .logger(new Logger() {
                    @Override
                    public void log(String message) {
                        LogUtils.d(TAG, message);
                    }
                }).build();
    }

    @Override
    public void onClick(View v) {
        if (!EasyPermissions.hasPermissions(this, PERMISSION)) {
            EasyPermissions.requestPermissions(this, "Download need external permission", 0x01,
                    PERMISSION);
            return;
        }

        startDownloadManager(INDEX_DOWNLOAD_FIRST, URL_DOWNLOAD_FIRST, mProgressBar, mTextView, Priority.NORMAL);
    }

    private void startDownloadManager(int index, String url, ProgressBar progressBar,
                                      TextView textView, Priority priority) {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDownloadManager.release();
    }
}
