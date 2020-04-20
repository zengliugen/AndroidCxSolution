package com.unity.cxsolution.Permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Random;

/**
 * 权限申请用Fragment
 */
@SuppressWarnings({"deprecation", "NullableProblems"})
public class PermissionFragment extends Fragment {
    /**
     * 权限申请回调列表
     */
    private SparseArray<PermissionInterface.PermissionsCallBack> permissionCallbacks = new SparseArray<>(2);
    /**
     * 随机对象
     */
    private Random mCodeGenerator = new Random();
    /**
     * 当前挂载的Activity对象
     */
    private Activity currentActivity;

    /**
     * 构建实例
     *
     * @return 实例对象
     */
    public static PermissionFragment NewInstance() {
        return new PermissionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        currentActivity = getActivity();
    }

    /**
     * 随机生成唯一的requestCode，最多尝试10次
     *
     * @return 请求Code
     */
    private int MakeRequestCode() {
        int requestCode;
        int tryCount = 0;
        do {
            requestCode = mCodeGenerator.nextInt(0x0000FFFF);
            tryCount++;
        } while (permissionCallbacks.indexOfKey(requestCode) >= 0 && tryCount < 10);
        return requestCode;
    }

    /**
     * 请求权限
     *
     * @param permissions         权限名称列表
     * @param permissionsCallBack 权限申请回调
     */
    public void requestPermissions(String[] permissions, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int requestCode = MakeRequestCode();
            permissionCallbacks.put(requestCode, permissionsCallBack);
            requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 当收到权限申请结果
     *
     * @param requestCode  申请Code
     * @param permissions  权限名称列表
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HandleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 处理权限申请结果
     *
     * @param requestCode  申请Code
     * @param permissions  权限名称列表
     * @param grantResults 授权结果
     */
    private void HandleRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        PermissionInterface.PermissionsCallBack permissionsCallBack = permissionCallbacks.get(requestCode);
        if (permissionsCallBack == null) {
            return;
        }
        permissionCallbacks.remove(requestCode);
        ArrayList<PermissionResultInfo> permissionResultInfoList = new ArrayList<>(permissions.length);
        for (int i = 0; i < permissions.length; i++) {
            PermissionResultInfo permissionResultInfo = new PermissionResultInfo();
            permissionResultInfo.PermissionName = permissions[i];
            permissionResultInfo.IsAccept = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            permissionResultInfo.ResultCode = grantResults[i];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionResultInfo.ShouldShowRequestPermissionRationale = currentActivity.shouldShowRequestPermissionRationale(permissions[i]);
            }
            permissionResultInfoList.add(permissionResultInfo);
        }
        permissionsCallBack.onRequestPermissionsResult(permissionResultInfoList);
    }
}
