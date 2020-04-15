package com.unity.cxsolution.Enter;

import android.os.Bundle;

import com.unity.cxsolution.Log.LogTool;

/**
 * 入口例子类
 */
public class Example_Activity {

    public static void onCreate_Before(Bundle bundle) {
        LogTool.d("Example_Activity onCreate_Before");
    }

    public static void onCreate_After(Bundle bundle) {
        LogTool.d("Example_Activity onCreate_After");
    }
}
