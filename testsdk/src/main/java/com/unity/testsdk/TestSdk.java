package com.unity.testsdk;

import android.os.Bundle;

import com.unity.cxsolution.Log.Logger;
import com.unity.cxsolution.View.ViewTool;


public class TestSdk {
    private static Logger logger = new Logger("TestSdk");

    public static void onCreate_Before(Bundle bundle) {
        logger.d("onCreate_Before");
        ViewTool.MakeSelfText("TestSdk onCreate_Before");
    }

    public static void onCreate_After(Bundle bundle) {
        logger.d("onCreate_After");
        ViewTool.MakeSelfText("TestSdk onCreate_After");
    }
}
