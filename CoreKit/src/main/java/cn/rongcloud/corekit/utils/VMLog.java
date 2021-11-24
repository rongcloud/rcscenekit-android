package cn.rongcloud.corekit.utils;

import android.util.Log;

public class VMLog {
    private static boolean debug = true;

    public static void setDebug(boolean debug) {
        VMLog.debug = debug;
    }

    public static void v(String TAG, String message) {
        if (debug) Log.v(TAG, message);
    }

    public static void d(String TAG, String message) {
        if (debug) Log.d(TAG, message);
    }

    public static void i(String TAG, String message) {
        if (debug) Log.i(TAG, message);
    }

    public static void w(String TAG, String message) {
        if (debug) Log.w(TAG, message);
    }

    public static void e(String TAG, String message) {
        if (debug) Log.e(TAG, message);
    }

}
