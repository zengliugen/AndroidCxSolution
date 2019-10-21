package com.unity.unityandroidenter;

import com.unity.cxsolution.Enter.CustomActivity;
import com.unity.cxsolution.Enter.Tool;
import com.unity.cxsolution.Tools.CallMethodTool;

public class Main {
    public static void main(String[] args) {
        System.out.println("xxx");
        int a = 1, b = 2;
        byte b1 = 2, b2 = 3;
        CallMethodTool.StaticCall("com.unity.testsdk.TestSdk", "Add", b1, b2);

        Test();
    }

    public static void Test() {
        Tool.TriggerBefore("Test", 1, 2);
        Tool.TriggerAfter("Test", 1, 2);
    }
}
