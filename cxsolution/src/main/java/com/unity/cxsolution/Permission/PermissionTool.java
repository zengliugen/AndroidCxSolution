package com.unity.cxsolution.Permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.unity.cxsolution.Log.LogTool;

import static com.unity.cxsolution.System.SystemTool.GetCurrentActivity;
import static com.unity.cxsolution.System.SystemTool.GetCurrentContext;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PermissionTool {
    /**
     * 检测指定Context对象是否拥有指定权限
     *
     * @param context    待检测的Context对象
     * @param permission 权限名称
     * @return 是否拥有权限
     */
    public static Boolean CheckPermission(Context context, String permission) {
        if (context == null || permission == null) {
            LogTool.e("CheckPermission Fail. Params(context,permission) cant not is null.");
            return false;
        }
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检测当前Context对象是否拥有指定权限
     *
     * @param permission 权限名称
     * @return 是否拥有权限
     */
    public static Boolean CheckSelfPermission(String permission) {
        return CheckPermission(GetCurrentContext(), permission);
    }

    /**
     * 检测指定包名的应用是否拥有指定权限
     *
     * @param packageName 应用包名
     * @param permission  权限名称
     * @return 是否拥有权限
     */
    public static Boolean CheckAppPermission(String packageName, String permission) {
        if (packageName == null || permission == null) {
            LogTool.e("CheckAppPermission Fail. Params(packageName,permission) cant not is null.");
            return false;
        }
        Context context = GetCurrentContext();
        if (context == null) {
            //noinspection SpellCheckingInspection
            LogTool.e("Get current content fail.Please set (com.unity.cxsolution.Enter.CustomActivity) to MainActivity ");
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        return packageManager.checkPermission(permission, packageName) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 在指定Activity对象中进行权限申请(单权限申请)
     *
     * @param activity            Activity对象
     * @param permission          权限名称
     * @param permissionsCallBack 权限申请回调
     */
    public static void RequestPermission(Activity activity, String permission, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (activity == null || permission == null || permissionsCallBack == null) {
            LogTool.e("RequestPermission Fail. Params(activity,permission,permissionsCallBack) cant not is null.");
            return;
        }
        String[] permissions = new String[]{permission};
        PermissionHelper.RequestPermissions(activity, permissions, permissionsCallBack);
    }

    /**
     * 在指定Activity对象中进行权限申请(多权限申请)
     *
     * @param activity            Activity对象
     * @param permissions         权限名称列表
     * @param permissionsCallBack 权限申请回调
     */
    public static void RequestPermissions(Activity activity, String[] permissions, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (activity == null || permissions == null || permissionsCallBack == null) {
            LogTool.e("RequestPermissions Fail. Params(activity,permissions,permissionsCallBack) cant not is null.");
            return;
        }
        PermissionHelper.RequestPermissions(activity, permissions, permissionsCallBack);
    }

    /**
     * 在当前Activity对象中进行权限申请(单权限申请)
     *
     * @param permission          权限名称
     * @param permissionsCallBack 权限申请回调
     */
    public static void RequestSelfPermission(String permission, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (permission == null || permissionsCallBack == null) {
            LogTool.e("RequestSelfPermission Fail. Params(permission,permissionsCallBack) cant not is null.");
            return;
        }
        String[] permissions = new String[]{permission};
        PermissionHelper.RequestPermissions(GetCurrentActivity(), permissions, permissionsCallBack);
    }

    /**
     * 在当前Activity对象中进行权限申请(多权限申请)
     *
     * @param permissions         权限名称列表
     * @param permissionsCallBack 权限申请回调
     */
    public static void RequestSelfPermissions(String[] permissions, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (permissions == null || permissionsCallBack == null) {
            LogTool.e("RequestSelfPermissions Fail. Params(permissions,permissionsCallBack) cant not is null.");
            return;
        }
        PermissionHelper.RequestPermissions(GetCurrentActivity(), permissions, permissionsCallBack);
    }

}
