package com.example.chukimmuoi.downloadmanager.utils;

import android.util.Log;

/**
 * @author : Hanet Electronics
 * @Skype : chukimmuoi
 * @Mobile : +84 167 367 2505
 * @Email : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : DownloadManager
 * Created by chukimmuoi on 10/01/2017.
 */

public class LogUtils {

    public static boolean LOG = false;

    public static void setLog(boolean LOG) {
        LogUtils.LOG = LOG;
    }

    public static void i(String tag, String message) {
        if (LOG) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (LOG) {
            Log.e(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (LOG) {
            Log.d(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (LOG) {
            Log.v(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (LOG) {
            Log.w(tag, message);
        }
    }
}
