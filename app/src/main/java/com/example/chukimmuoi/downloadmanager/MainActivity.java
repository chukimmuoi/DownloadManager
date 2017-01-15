package com.example.chukimmuoi.downloadmanager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
                mProgressBar, mTextView,
                Priority.NORMAL,
                destination);
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
