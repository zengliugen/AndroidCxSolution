package com.unity.cxsolution.Permission;

import java.util.ArrayList;

/**
 * 权限相关接口
 */
public interface PermissionInterface {
    /**
     * 权限申请回调
     */
    @SuppressWarnings({"UnnecessaryInterfaceModifier", "unused"})
    public interface PermissionsCallBack {
        /**
         * 权限申请结果函数
         *
         * @param permissionResultInfoList 权限申请结果信息列表
         */
        void onRequestPermissionsResult(ArrayList<PermissionResultInfo> permissionResultInfoList);

        /**
         * 当发生错误
         *
         * @param error 错误信息
         */
        void onError(String error);
    }
}
