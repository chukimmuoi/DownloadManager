package com.example.chukimmuoi.downloadmanager.callback;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.coolerfall.download.DownloadCallback;
import com.example.chukimmuoi.downloadmanager.utils.LogUtils;

/**
 * @author : Hanet Electronics
 * @Skype : chukimmuoi
 * @Mobile : +84 167 367 2505
 * @Email : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : DownloadManager
 * Created by chukimmuoi on 10/01/2017.
 */

public class DownloadExecuteCallback extends DownloadCallback {

    private static final String TAG = DownloadExecuteCallback.class.getSimpleName();

    private ProgressBar mProgressBar;

    private TextView    mTextView;

    private long startTimestamp = 0;

    private long startSize      = 0;

    public DownloadExecuteCallback(ProgressBar progressBar, TextView textView) {
        super();
        mProgressBar = progressBar;
        mTextView    = textView;
    }

    @Override
    public void onStart(int downloadId, long totalBytes) {
        super.onStart(downloadId, totalBytes);

        LogUtils.i(TAG, "onStart: " + downloadId + " " + totalBytes);

        startTimestamp = System.currentTimeMillis();
    }

    @Override
    public void onRetry(int downloadId) {
        super.onRetry(downloadId);

        LogUtils.i(TAG, "onRetry: " + downloadId);
    }

    @Override
    public void onProgress(int downloadId, long bytesWritten, long totalBytes) {
        super.onProgress(downloadId, bytesWritten, totalBytes);

        LogUtils.i(TAG, "onProgress: " + downloadId);

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

        LogUtils.i(TAG, "onSuccess: " + downloadId + " " + filePath);
    }

    @Override
    public void onFailure(int downloadId, int statusCode, String errMsg) {
        super.onFailure(downloadId, statusCode, errMsg);

        LogUtils.i(TAG, "onFailure: " + downloadId + " " + statusCode + " " + errMsg);
    }
}
