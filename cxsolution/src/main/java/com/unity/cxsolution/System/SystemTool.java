package com.unity.cxsolution.System;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.unity.cxsolution.Enter.CustomActivity;
import com.unity.cxsolution.Enter.CustomApplication;
import com.unity.cxsolution.Enter.CustomUnityActivity;

/**
 * 系统工具
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SystemTool {
    /**
     * 获取当前Application对象
     *
     * @return Application对象
     */
    public static Application GetCurrentApplication() {
        return CustomApplication.CurrentApplication;
    }

    /**
     * 获取当前Activity对象,优先从CustomUnityActivity获取
     *
     * @return Activity对象
     */
    public static Activity GetCurrentActivity() {
        Activity activity = CustomUnityActivity.CurrentActivity;
        if (activity == null) {
            activity = CustomActivity.CurrentActivity;
        }
        return activity;
    }

    /**
     * 获取当前Context对象,优先从Application中获取
     *
     * @return Context对象
     */
    public static Context GetCurrentContext() {
        Context context = GetCurrentApplication();
        if (context == null) {
            context = GetCurrentActivity();
        }
        return context;
    }

    /**
     * 获取当前资源管理器
     *
     * @return 资源管理器
     */
    public static AssetManager GetCurrentAssetManager() {
        Context context = GetCurrentContext();
        if (context == null) {
            return null;
        }
        return context.getAssets();
    }
}
