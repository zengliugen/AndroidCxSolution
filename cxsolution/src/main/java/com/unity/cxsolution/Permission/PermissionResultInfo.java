package com.unity.cxsolution.Permission;

/**
 * 权限申请结果信息
 */
@SuppressWarnings("WeakerAccess")
public class PermissionResultInfo {
    /**
     * 权限名称
     */
    public String PermissionName;
    /**
     * 是否同意
     */
    public boolean IsAccept;
    /**
     * 结果Code
     * 0:   同意
     * -1:  拒绝
     */
    public int ResultCode;
    /**
     * 是否需要向用户说明申请权限的目的
     * 未申请权限,直接通过接口获取该值,返回值为false
     * 申请权限后,用户同意了该权限,返回值为false
     * 申请权限后,用户拒绝了该权限,返回值为true
     * 申请权限后,用户拒绝了该权限,同时勾选了不再询问,返回值为false
     */
    public boolean ShouldShowRequestPermissionRationale;
}
