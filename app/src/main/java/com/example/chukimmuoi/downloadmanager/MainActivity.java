package com.example.chukimmuoi.downloadmanager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coolerfall.download.DownloadCallback;
import com.coolerfall.download.Priority;
import com.example.chukimmuoi.downloadmanager.constanst.IDownloadConstants;
import com.example.chukimmuoi.downloadmanager.constanst.ISystemConstants;
import com.example.chukimmuoi.downloadmanager.manager.DownloadFileManager;
import com.example.chukimmuoi.downloadmanager.utils.LogUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements ISystemConstants,
        IDownloadConstants,
        View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTextView;

    private ProgressBar mProgressBar;

    private Button mButton;

    private DownloadFileManager mDownloadFileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtils.setLog(IS_DEBUG);

        initialUI();

        mDownloadFileManager = DownloadFileManager.getInstance().onCreate(this, false,
                NUMBER_THREAD_POOL_SIZE_DOWNLOAD);

    }

    private void initialUI() {
        mTextView = (TextView) findViewById(R.id.tv_test);

        mProgressBar = (ProgressBar) findViewById(R.id.prg_test);

        mButton = (Button) findViewById(R.id.btn_test);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String destination = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "DownloadManager";

        mDownloadFileManager.onStartDownload(this, INDEX_DOWNLOAD_FIRST, URL_DOWNLOAD_FIRST,
                Priority.NORMAL,
                destination, new DownloadCallback() {

                    private long startTimestamp = 0;

                    private long startSize      = 0;

                    @Override
                    public void onStart(int downloadId, long totalBytes) {
                        super.onStart(downloadId, totalBytes);

                        LogUtils.i(TAG, "onProgress: downloadId = " + downloadId
                                + ", totalBytes = " + totalBytes);

                        startTimestamp = System.currentTimeMillis();
                    }

                    @Override
                    public void onRetry(int downloadId) {
                        super.onRetry(downloadId);

                        LogUtils.i(TAG, "onProgress: downloadId = " + downloadId);
                    }

                    @Override
                    public void onProgress(int downloadId, long bytesWritten, long totalBytes) {
                        super.onProgress(downloadId, bytesWritten, totalBytes);

                        LogUtils.i(TAG, "onProgress: downloadId = " + downloadId
                                + ", bytesWritten = " + bytesWritten
                                + ", totalBytes = " + totalBytes);

                        int progress = Math.round(bytesWritten * 100f / totalBytes);
                        progress = progress == 100 ? 0 : progress;

                        long currentTimestamp = System.currentTimeMillis();

                        int deltaTimes = (int) (currentTimestamp - startTimestamp + 1);
                        int speed      = (int) ((bytesWritten - startSize) * 1000 / deltaTimes) / 1024;

                        startSize = bytesWritten;

                        mTextView.setText(progress + "%, " + speed + " kb/s");
                        mProgressBar.setProgress(progress);
                    }

                    @Override
                    public void onSuccess(int downloadId, String filePath) {
                        super.onSuccess(downloadId, filePath);

                        LogUtils.i(TAG, "onProgress: downloadId = " + downloadId
                                + ", filePath = " + filePath);
                    }

                    @Override
                    public void onFailure(int downloadId, int statusCode, String errMsg) {
                        super.onFailure(downloadId, statusCode, errMsg);

                        LogUtils.i(TAG, "onProgress: downloadId = " + downloadId
                                + ", statusCode = " + statusCode
                                + ", errMsg = " + errMsg);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDownloadFileManager != null) {
            mDownloadFileManager.onDestroy();
            mDownloadFileManager = null;
        }
    }
}
