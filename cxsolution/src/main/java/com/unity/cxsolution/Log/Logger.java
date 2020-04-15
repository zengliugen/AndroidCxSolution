package com.unity.cxsolution.Log;

import android.util.Log;

import java.text.Normalizer;

/**
 * 日志对象
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Logger {
    /**
     * 标记
     */
    private String TAG;

    /**
     * 初始化
     *
     * @param tag 标记
     */
    public Logger(String tag) {
        assert (tag != null);
        TAG = tag;
    }

    /**
     * 格式化字符串
     *
     * @param format 格式化字符串
     * @param args   参数
     * @return 格式化后的字符串
     */
    public static String Format(String format, Object... args) {
        String string = "";
        try {
            string = String.format(format, args);
        } catch (Exception ignored) {
        }
        return string;
    }

    /**
     * @param msg 信息
     */
    public void v(String msg) {
        Log.v(TAG, msg);
    }

    /**
     * @param format 格式化字符串
     * @param args   参数
     */
    public void v(String format, Object... args) {
        v(Format(format, args));
    }

    /**
     * @param msg       信息
     * @param throwable 详细信息
     */
    public void v(String msg, Throwable throwable) {
        Log.v(TAG, msg, throwable);
    }

    /**
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public void v(String format, Throwable throwable, Object... args) {
        v(Format(format, args), throwable);
    }

    /**
     * 调试
     *
     * @param msg 信息
     */
    public void d(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * @param format 格式化字符串
     * @param args   参数
     */
    public void d(String format, Object... args) {
        d(Format(format, args));
    }

    /**
     * 调试
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public void d(String msg, Throwable throwable) {
        Log.d(TAG, msg, throwable);
    }

    /**
     * 调试
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public void d(String format, Throwable throwable, Object... args) {
        d(Format(format, args), throwable);
    }

    /**
     * 信息
     *
     * @param msg 信息
     */
    public void i(String msg) {
        Log.i(TAG, msg);
    }

    /**
     * 信息
     *
     * @param format 格式化字符串
     * @param args   参数
     */
    public void i(String format, Object... args) {
        i(Format(format, args));
    }

    /**
     * 信息
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public void i(String msg, Throwable throwable) {
        Log.i(TAG, msg, throwable);
    }

    /**
     * 信息
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public void i(String format, Throwable throwable, Object... args) {
        i(Format(format, args), throwable);
    }

    /**
     * 警告
     *
     * @param msg 信息
     */
    public void w(String msg) {
        Log.w(TAG, msg);
    }

    /**
     * 警告
     *
     * @param format 格式化字符串
     * @param args   参数
     */
    public void w(String format, Object... args) {
        w(Format(format, args));
    }

    /**
     * 警告
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public void w(String msg, Throwable throwable) {
        Log.w(TAG, msg, throwable);
    }

    /**
     * 警告
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public void w(String format, Throwable throwable, Object... args) {
        w(Format(format, args), throwable);
    }

    /**
     * 异常
     *
     * @param msg 信息
     */
    public void e(String msg) {
        Log.e(TAG, msg);
    }

    /**
     * 异常
     *
     * @param format 格式化字符串
     * @param args   参数
     */
    public void e(String format, Object... args) {
        e(Format(format, args));
    }

    /**
     * 异常
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public void e(String msg, Throwable throwable) {
        Log.e(TAG, msg, throwable);
    }

    /**
     * 异常
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public void e(String format, Throwable throwable, Object... args) {
        e(Format(format, args), throwable);
    }

    /**
     * 异常
     *
     * @param e 异常
     */
    public void e(Exception e) {
        if (e == null) {
            return;
        }
        e(e.getMessage());
        e.printStackTrace();
    }
}
