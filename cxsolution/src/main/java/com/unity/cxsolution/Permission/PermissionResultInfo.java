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
    public Boolean IsAccept;
    /**
     * 结果Code
     * 0:   同意
     * -1:  拒绝
     */
    public Integer ResultCode;
    /**
     * 是否不再询问
     */
    public Boolean IsNeverInquiry;
}
