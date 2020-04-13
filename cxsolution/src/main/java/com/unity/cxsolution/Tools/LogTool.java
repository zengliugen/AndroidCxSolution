package com.unity.cxsolution.Tools;

import android.util.Log;

/**
 * 日志工具
 */
@SuppressWarnings("unused")
public class LogTool {
    private final static String TAG = "CxSolution";

    /**
     * @param msg 信息
     */
    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    /**
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void v(String msg, Throwable throwable) {
        Log.v(TAG, msg, throwable);
    }

    /**
     * 调试
     *
     * @param msg 信息
     */
    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * 调试
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void d(String msg, Throwable throwable) {
        Log.d(TAG, msg, throwable);
    }

    /**
     * 信息
     *
     * @param msg 信息
     */
    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    /**
     * 信息
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void i(String msg, Throwable throwable) {
        Log.i(TAG, msg, throwable);
    }

    /**
     * 警告
     *
     * @param msg 信息
     */
    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    /**
     * 警告
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void w(String msg, Throwable throwable) {
        Log.w(TAG, msg, throwable);
    }

    /**
     * 异常
     *
     * @param msg 信息
     */
    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    /**
     * 异常
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void e(String msg, Throwable throwable) {
        Log.e(TAG, msg, throwable);
    }

    /**
     * 异常
     *
     * @param e 异常
     */
    public static void e(Exception e) {
        if (e == null) {
            return;
        }
        e(e.getMessage());
        e.printStackTrace();
    }
}
