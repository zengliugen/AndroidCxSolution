package com.unity.testsdk;

import android.os.Bundle;

public class TestSdk {
    public static void Add(Byte a, Byte b) {
        System.out.println("a+b=" + (a + b));
    }

    public static void onCreate_Before(Bundle bundle) {
        System.out.println("onCreate_Before");
    }

    public static void onCreate_After(Bundle bundle) {
        System.out.println("onCreate_After");
    }

    public static void Test_Before(Integer a, Integer b) {
        System.out.println("Test_Before:" + (a + b));
    }
}
