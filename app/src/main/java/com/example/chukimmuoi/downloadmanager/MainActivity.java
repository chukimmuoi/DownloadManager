package com.example.chukimmuoi.downloadmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coolerfall.download.DownloadManager;
import com.example.chukimmuoi.downloadmanager.constanst.SystemConstanst;
import com.example.chukimmuoi.downloadmanager.utils.LogUtils;

public class MainActivity extends AppCompatActivity
                          implements SystemConstanst {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DownloadManager mDownloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtils.setLog(IS_DEBUG);
    }

    private void initialDownloadManager() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
