package com.unity.cxsolution.Permission;

import android.app.Activity;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.unity.cxsolution.Log.LogTool;

/**
 * 权限申请辅助者
 */
@SuppressWarnings("WeakerAccess")
public class PermissionHelper {
    /**
     * 权限申请标签
     */
    private final static String FRAGMENT_TAG = "FRAGMENT_TAG_PERMISSION";

    /**
     * 获取权限申请Fragment
     *
     * @param appCompatActivity 挂载的Activity
     * @return 权限申请Fragment
     */
    private static PermissionFragmentAndroidx GetPermissionFragmentX(AppCompatActivity appCompatActivity) {
        if (appCompatActivity == null) {
            return null;
        }
        PermissionFragmentAndroidx permissionFragment = null;
        FragmentManager fragmentManager;
        try {
            fragmentManager = appCompatActivity.getSupportFragmentManager();
            permissionFragment = (PermissionFragmentAndroidx) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (permissionFragment == null) {
                permissionFragment = PermissionFragmentAndroidx.NewInstance();
                fragmentManager.beginTransaction().add(permissionFragment, FRAGMENT_TAG).commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
        } catch (Exception e) {
            LogTool.e(e);
        }
        return permissionFragment;
    }

    /**
     * 获取权限申请Fragment
     *
     * @param activity 挂载的Activity
     * @return 权限申请Fragment
     */
    private static PermissionFragment GetPermissionFragment(Activity activity) {
        if (activity == null) {
            return null;
        }
        PermissionFragment permissionFragment = null;
        android.app.FragmentManager fragmentManager;
        try {
            fragmentManager = activity.getFragmentManager();
            permissionFragment = (PermissionFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (permissionFragment == null) {
                permissionFragment = PermissionFragment.NewInstance();
                fragmentManager.beginTransaction().add(permissionFragment, FRAGMENT_TAG).commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
        } catch (Exception e) {
            LogTool.e(e);
        }
        return permissionFragment;
    }

    /**
     * 在指定Activity对象中进行权限申请
     *
     * @param activity            Activity对象
     * @param permissions         权限名称列表
     * @param permissionsCallBack 权限申请回调
     */
    public static void RequestPermissions(Activity activity, String[] permissions, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (activity == null || permissions == null || permissionsCallBack == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (activity.getClass().isAssignableFrom(AppCompatActivity.class)) {
                    PermissionFragmentAndroidx permissionFragmentAndroidx = GetPermissionFragmentX((AppCompatActivity) activity);
                    permissionFragmentAndroidx.requestPermissions(permissions, permissionsCallBack);
                } else {
                    PermissionFragment permissionFragment = GetPermissionFragment(activity);
                    permissionFragment.requestPermissions(permissions, permissionsCallBack);
                }
            } catch (NoClassDefFoundError ignored) {
                // 可能有项目并未使用androidX
                PermissionFragment permissionFragment = GetPermissionFragment(activity);
                permissionFragment.requestPermissions(permissions, permissionsCallBack);
            }
        } else {
            permissionsCallBack.onError(String.format("系统SDK版本低于%s,无法动态申请权限.", Build.VERSION_CODES.M));
        }
    }
}
