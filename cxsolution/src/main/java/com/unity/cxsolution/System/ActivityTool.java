package com.unity.cxsolution.System;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.unity.cxsolution.Log.LogTool;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActivityTool {
    /**
     * 在指定Activity对象中启动Activity
     *
     * @param activity           载体Activity
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @return 是否成功
     */
    public static boolean StartActivity(Activity activity, String sourcePackageName, String targetPackageName, String targetActivityName) {
        if (activity == null || sourcePackageName == null || targetPackageName == null || targetActivityName == null) {
            LogTool.e("StartActivity Fail. Params(activity,sourcePackageName,targetPackageName,targetActivityName) cant not is null.");
            return false;
        }
        try {
            Intent intent = new Intent(sourcePackageName);
            ComponentName comp = new ComponentName(targetPackageName, targetActivityName);
            intent.setComponent(comp);
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            LogTool.e(e);
            return false;
        }
    }

    /**
     * 在当前Activity对象中启动Activity
     *
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @return 是否成功
     */
    public static boolean StartSelfActivity(String sourcePackageName, String targetPackageName, String targetActivityName) {
        if (sourcePackageName == null || targetPackageName == null || targetActivityName == null) {
            LogTool.e("StartSelfActivity Fail. Params(sourcePackageName,targetPackageName,targetActivityName) cant not is null.");
            return false;
        }
        return StartActivity(SystemTool.GetCurrentActivity(), sourcePackageName, targetPackageName, targetActivityName);
    }

    /**
     * 在指定Activity对象中启动Activity并传参
     *
     * @param activity           载体Activity
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @param bundle             参数
     * @return 是否成功
     */
    public static boolean StartActivityWithBundle(Activity activity, String sourcePackageName, String targetPackageName, String targetActivityName, Bundle bundle) {
        if (activity == null || sourcePackageName == null || targetPackageName == null || targetActivityName == null || bundle == null) {
            LogTool.e("StartActivity Fail. Params(activity,sourcePackageName,targetPackageName,targetActivityName,bundle) cant not is null.");
            return false;
        }
        try {
            Intent intent = new Intent(sourcePackageName);
            intent.putExtras(bundle);
            ComponentName comp = new ComponentName(targetPackageName, targetActivityName);
            intent.setComponent(comp);
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            LogTool.e(e);
            return false;
        }
    }

    /**
     * 在当前Activity对象中启动Activity并传参
     *
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @param bundle             参数
     * @return 是否成功
     */
    public static boolean StartSelfActivityWithBundle(String sourcePackageName, String targetPackageName, String targetActivityName, Bundle bundle) {
        if (sourcePackageName == null || targetPackageName == null || targetActivityName == null || bundle == null) {
            LogTool.e("StartSelfActivity Fail. Params(sourcePackageName,targetPackageName,targetActivityName,bundle) cant not is null.");
            return false;
        }
        return StartActivityWithBundle(SystemTool.GetCurrentActivity(), sourcePackageName, targetPackageName, targetActivityName, bundle);
    }

    /**
     * 在指定Activity对象中启动应用
     *
     * @param activity    载体Activity
     * @param packageName 应用包名
     * @return 是否成功
     */
    public static boolean StartApp(Activity activity, String packageName) {
        if (packageName == null) {
            LogTool.e("StartApp Fail. Params(activity,packageName) cant not is null.");
            return false;
        }
        try {
            PackageManager packageManager = activity.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            LogTool.e(e);
            return false;
        }
    }

    /**
     * 在当前Activity对象中启动应用
     *
     * @param packageName 应用包名
     * @return 是否成功
     */
    public static boolean StartSelfApp(String packageName) {
        if (packageName == null) {
            LogTool.e("StartSelfApp Fail. Params(packageName) cant not is null.");
            return false;
        }
        return StartApp(SystemTool.GetCurrentActivity(), packageName);
    }

    /**
     * 在指定Activity对象中启动应用并传参
     *
     * @param activity    载体Activity
     * @param packageName 应用包名
     * @param bundle      参数
     * @return 是否成功
     */
    public static boolean StartAppWithBundle(Activity activity, String packageName, Bundle bundle) {
        if (activity == null || packageName == null || bundle == null) {
            LogTool.e("StartApp Fail. Params(activity,packageName,bundle) cant not is null.");
            return false;
        }
        try {
            PackageManager packageManager = activity.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            if (intent != null) {
                intent.putExtras(bundle);
                activity.startActivity(intent);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogTool.e(e);
            return false;
        }
    }

    /**
     * 在当前Activity对象中启动应用并传参
     *
     * @param packageName 应用包名
     * @param bundle      参数
     * @return 是否成功
     */
    public static boolean StartSelfAppWithBundle(String packageName, Bundle bundle) {
        if (packageName == null || bundle == null) {
            LogTool.e("StartApp Fail. Params(packageName,bundle) cant not is null.");
            return false;
        }
        return StartAppWithBundle(SystemTool.GetCurrentActivity(), packageName, bundle);
    }
}
