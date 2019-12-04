package com.unity.cxsolution.Enter;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

public class CustomApplication extends Application {
    /**
     * 当前Application对象
     */
    public static Application CurrentApplication;

    /**
     * @param methodName 函数名称
     * @param args       参数列表
     */
    private static void TriggerBefore(String methodName, Object... args) {
        Tool.TriggerBefore(Tool.ClassType_Application, methodName, args);
    }

    /**
     * @param methodName 函数名称
     * @param args       参数列表
     */
    private static void TriggerAfter(String methodName, Object... args) {
        Tool.TriggerAfter(Tool.ClassType_Application, methodName, args);
    }

    @Override
    public void onCreate() {
        CurrentApplication = this;
        TriggerBefore("onCreate");
        super.onCreate();
        TriggerAfter("onCreate");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        TriggerBefore("onConfigurationChanged", newConfig);
        super.onConfigurationChanged(newConfig);
        TriggerAfter("onConfigurationChanged", newConfig);
    }

    @Override
    public void onLowMemory() {
        TriggerBefore("onLowMemory");
        super.onLowMemory();
        TriggerAfter("onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        TriggerBefore("onTrimMemory", level);
        super.onTrimMemory(level);
        TriggerAfter("onTrimMemory", level);
    }

    @Override
    public void onTerminate() {
        TriggerBefore("onTerminate");
        super.onTerminate();
        TriggerAfter("onTerminate");
    }
}
