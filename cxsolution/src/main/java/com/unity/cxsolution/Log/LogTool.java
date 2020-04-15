package com.unity.cxsolution.Log;

/**
 * 日志工具
 */
@SuppressWarnings("unused")
public class LogTool {
    /**
     * 日志TAG
     */
    private final static String TAG = "CxSolution";
    /**
     * 日志对象
     */
    private final static Logger logger = new Logger(TAG);

    /**
     * @param msg 信息
     */
    public static void v(String msg) {
        logger.v(msg);
    }

    /**
     * @param format 格式化字符串
     * @param args   参数
     */
    public static void v(String format, Object... args) {
        logger.v(format, args);
    }

    /**
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void v(String msg, Throwable throwable) {
        logger.v(msg, throwable);
    }

    /**
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public static void v(String format, Throwable throwable, Object... args) {
        logger.v(format, throwable, args);
    }

    /**
     * 调试
     *
     * @param msg 信息
     */
    public static void d(String msg) {
        logger.d(msg);
    }

    /**
     * @param format 格式化字符串
     * @param args   参数
     */
    public static void d(String format, Object... args) {
        logger.d(format, args);
    }

    /**
     * 调试
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void d(String msg, Throwable throwable) {
        logger.d(msg, throwable);
    }

    /**
     * 调试
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public static void d(String format, Throwable throwable, Object... args) {
        logger.d(format, throwable, args);
    }

    /**
     * 信息
     *
     * @param msg 信息
     */
    public static void i(String msg) {
        logger.i(msg);
    }

    /**
     * 信息
     *
     * @param format 格式化字符串
     * @param args   参数
     */
    public static void i(String format, Object... args) {
        logger.i(format, args);
    }

    /**
     * 信息
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void i(String msg, Throwable throwable) {
        logger.i(msg, throwable);
    }

    /**
     * 信息
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public static void i(String format, Throwable throwable, Object... args) {
        logger.i(format, throwable, args);
    }

    /**
     * 警告
     *
     * @param msg 信息
     */
    public static void w(String msg) {
        logger.w(msg);
    }

    /**
     * 警告
     *
     * @param format 格式化字符串
     * @param args   参数
     */
    public static void w(String format, Object... args) {
        logger.w(format, args);
    }

    /**
     * 警告
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void w(String msg, Throwable throwable) {
        logger.w(msg, throwable);
    }

    /**
     * 警告
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public static void w(String format, Throwable throwable, Object... args) {
        logger.w(format, throwable, args);
    }

    /**
     * 异常
     *
     * @param msg 信息
     */
    public static void e(String msg) {
        logger.e(msg);
    }

    /**
     * 异常
     *
     * @param format 格式化字符串
     * @param args   参数
     */
    public static void e(String format, Object... args) {
        logger.e(format, args);
    }

    /**
     * 异常
     *
     * @param msg       信息
     * @param throwable 详细信息
     */
    public static void e(String msg, Throwable throwable) {
        logger.e(msg, throwable);
    }

    /**
     * 异常
     *
     * @param format    格式化字符串
     * @param throwable 详细信息
     * @param args      参数
     */
    public static void e(String format, Throwable throwable, Object... args) {
        logger.e(format, throwable, args);
    }

    /**
     * 异常
     *
     * @param e 异常
     */
    public static void e(Exception e) {
        logger.e(e);

    }
}
