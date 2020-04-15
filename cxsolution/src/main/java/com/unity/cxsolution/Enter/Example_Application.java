package com.unity.cxsolution.Enter;

import com.unity.cxsolution.Log.LogTool;

/**
 * 入口例子类
 */
public class Example_Application {
    public static void onCreate_Before() {
        LogTool.d("Example_Application onCreate_Before");
    }

    public static void onCreate_After() {
        LogTool.d("Example_Application onCreate_After");
    }
}
