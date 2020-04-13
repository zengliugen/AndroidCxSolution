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
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    private static void TriggerBefore(String methodName, Object[] params, Class[] paramTypes) {
        Tool.TriggerBefore(Tool.ClassType_Activity, methodName, params, paramTypes);
    }

    /**
     * @param methodName 函数名称
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    private static void TriggerAfter(String methodName, Object[] params, Class[] paramTypes) {
        Tool.TriggerAfter(Tool.ClassType_Activity, methodName, params, paramTypes);
    }

    @Override
    public void onCreate() {
        CurrentApplication = this;
        TriggerBefore("onCreate", null, null);
        super.onCreate();
        TriggerAfter("onCreate", null, null);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        TriggerBefore("onConfigurationChanged", new Object[]{newConfig}, new Class[]{Configuration.class});
        super.onConfigurationChanged(newConfig);
        TriggerAfter("onConfigurationChanged", new Object[]{newConfig}, new Class[]{Configuration.class});
    }

    @Override
    public void onLowMemory() {
        TriggerBefore("onLowMemory", null, null);
        super.onLowMemory();
        TriggerAfter("onLowMemory", null, null);
    }

    @Override
    public void onTrimMemory(int level) {
        TriggerBefore("onTrimMemory", new Object[]{level}, new Class[]{int.class});
        super.onTrimMemory(level);
        TriggerAfter("onTrimMemory", new Object[]{level}, new Class[]{int.class});
    }

    @Override
    public void onTerminate() {
        TriggerBefore("onTerminate", null, null);
        super.onTerminate();
        TriggerAfter("onTerminate", null, null);
    }
}
