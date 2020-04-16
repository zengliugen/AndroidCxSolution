package com.unity.cxsolution.Unity;

import android.net.Uri;
import android.os.Bundle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unity.cxsolution.Log.LogTool;
import com.unity.cxsolution.Permission.PermissionHelper;
import com.unity.cxsolution.Permission.PermissionInterface;
import com.unity.cxsolution.Permission.PermissionResultInfo;
import com.unity.cxsolution.Permission.PermissionTool;
import com.unity.cxsolution.System.ActivityTool;
import com.unity.cxsolution.System.StorageTool;
import com.unity.cxsolution.System.SystemTool;
import com.unity.cxsolution.URLScheme.URLSchemeTool;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;

/**
 * 封装提供给UNITY使用的接口
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class UnityTool {
    /**
     * 发送消息到UNITY
     *
     * @param gameObjectName UNITY侧组件名称
     * @param methodName     UNITY侧函数名称
     * @param message        消息
     */
    public static void SendMessageToUnity(String gameObjectName, String methodName, String message) {
        if (gameObjectName == null || methodName == null) {
            return;
        }
        UnityPlayer.UnitySendMessage(gameObjectName, methodName, message);
    }

    /**
     * 发送消息到UNITY(将消息关键字与消息组装为json)
     *
     * @param gameObjectName UNITY侧组件名称
     * @param methodName     UNITY侧函数名称
     * @param messageKey     消息关键字
     * @param message        消息
     */
    public static void SendMessageToUnityAsJson(String gameObjectName, String methodName, String messageKey, String message) {
        if (gameObjectName == null || methodName == null || messageKey == null) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageKey", messageKey);
            jsonObject.put("message", message);
            SendMessageToUnity(gameObjectName, methodName, jsonObject.toJSONString());
        } catch (Exception e) {
            LogTool.e(e);
        }
    }

    /**
     * 检测当前Context对象是否拥有指定权限
     *
     * @param permission 权限名称
     * @return 是否拥有权限
     */
    public static Boolean CheckSelfPermission(String permission) {
        return PermissionTool.CheckSelfPermission(permission);
    }

    /**
     * 在当前Activity对象中进行权限申请
     *
     * @param json json参数 "{permission:"权限名称",gameObjectName:"UNITY侧组件名称",methodName:"UNITY侧函数名称",exData="额外参数,原样回调到Unity"}"
     */
    public static void RequestSelfPermission(final String json) {
        if (json == null) {
            LogTool.e("RequestPermission Fail. Params(json) cant not is null.");
            return;
        }
        try {
            final JSONObject jsonObject = JSONObject.parseObject(json);
            // 获取需要申请的权限
            String permission = jsonObject.getString("permission");
            // 获取UNITY侧回调组件名称
            final String gameObjectName = jsonObject.getString("gameObjectName");
            // 获取UNITY侧回调函数名称
            final String methodName = jsonObject.getString("methodName");
            // 获取额外参数
            final String exData = jsonObject.getString("exData");
            if (permission == null || gameObjectName == null || methodName == null) {
                LogTool.e("RequestSelfPermission Fail. Json analysis params(permission,gameObjectName,methodName) cant not is null.");
                return;
            }
            // 申请权限
            PermissionTool.RequestSelfPermission(permission, new PermissionInterface.PermissionsCallBack() {
                @Override
                public void onRequestPermissionsResult(ArrayList<PermissionResultInfo> permissionResultInfoList) {
                    try {
                        String resultJson = JSONObject.toJSONString(permissionResultInfoList);
                        JSONObject resultJsonObject = new JSONObject();
                        resultJsonObject.put("result", resultJson);
                        resultJsonObject.put("exData", exData);
                        SendMessageToUnityAsJson(gameObjectName, methodName, "RequestSelfPermission", resultJsonObject.toJSONString());
                    } catch (Exception e) {
                        LogTool.e(e);
                        JSONObject errorJsonObject = new JSONObject();
                        errorJsonObject.put("error", e.getMessage());
                        errorJsonObject.put("exData", exData);
                        SendMessageToUnityAsJson(gameObjectName, methodName, "RequestSelfPermission", errorJsonObject.toJSONString());
                    }
                }

                @Override
                public void onError(String error) {
                    JSONObject errorJsonObject = new JSONObject();
                    errorJsonObject.put("error", error);
                    errorJsonObject.put("exData", exData);
                    SendMessageToUnityAsJson(gameObjectName, methodName, "RequestSelfPermission", errorJsonObject.toJSONString());
                }
            });
        } catch (Exception e) {
            LogTool.e(e);
        }
    }

    /**
     * 在当前Activity对象中进行权限申请
     *
     * @param json json参数 "{permissions:["权限名称一","权限名称二"],gameObjectName:"UNITY侧组件名称",methodName:"UNITY侧函数名称",exData="额外参数,原样回调到Unity"}"
     */
    public static void RequestSelfPermissions(final String json) {
        if (json == null) {
            LogTool.e("RequestPermissions Fail. Params(json) cant not is null.");
            return;
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String[] permissions = new String[0];
            // 获取需要申请的权限列表
            //noinspection SuspiciousToArrayCall
            permissions = jsonObject.getJSONArray("permissions").toArray(permissions);
            // 获取UNITY侧回调组件名称
            final String gameObjectName = jsonObject.getString("gameObjectName");
            // 获取UNITY侧回调函数名称
            final String methodName = jsonObject.getString("methodName");
            // 获取额外参数
            final String exData = jsonObject.getString("exData");
            if (permissions.length == 0 || gameObjectName == null || methodName == null) {
                LogTool.e("RequestSelfPermissions Fail. Json analysis params(permissions,gameObjectName,methodName) cant not is null.");
                return;
            }
            // 申请权限
            PermissionTool.RequestSelfPermissions(permissions, new PermissionInterface.PermissionsCallBack() {
                @Override
                public void onRequestPermissionsResult(ArrayList<PermissionResultInfo> permissionResultInfoList) {
                    try {
                        String resultJson = JSONObject.toJSONString(permissionResultInfoList);
                        JSONObject resultJsonObject = new JSONObject();
                        resultJsonObject.put("result", resultJson);
                        resultJsonObject.put("exData", exData);
                        SendMessageToUnityAsJson(gameObjectName, methodName, "RequestSelfPermissions", resultJsonObject.toJSONString());
                    } catch (Exception e) {
                        LogTool.e(e);
                        JSONObject errorJsonObject = new JSONObject();
                        errorJsonObject.put("error", e.getMessage());
                        errorJsonObject.put("exData", exData);
                        SendMessageToUnityAsJson(gameObjectName, methodName, "RequestSelfPermissions", errorJsonObject.toJSONString());
                    }
                }

                @Override
                public void onError(String error) {
                    JSONObject errorJsonObject = new JSONObject();
                    errorJsonObject.put("error", error);
                    errorJsonObject.put("exData", exData);
                    SendMessageToUnityAsJson(gameObjectName, methodName, "RequestSelfPermissions", errorJsonObject.toJSONString());
                }
            });
        } catch (Exception e) {
            LogTool.e(e);
        }
    }

    /**
     * 启动Activity
     *
     * @param json json参数 "{targetPackageName:"com.xxx.xxx",targetActivityName:"com.xxx.xxx.MainActivity",paramKeys:["key1","key2"],paramValues:["value1","value2"]}"
     * @return 是否成功
     */
    public static Boolean StartSelfActivity(String json) {
        if (json == null) {
            LogTool.e("StartActivity Fail. Params(json) cant not is null.");
            return false;
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String targetPackageName = jsonObject.getString("targetPackageName");
            String targetActivityName = jsonObject.getString("targetActivityName");
            JSONArray paramKeysJsonArray = jsonObject.getJSONArray("paramKeys");
            JSONArray paramValuesJsonArray = jsonObject.getJSONArray("paramValues");
            if (targetPackageName == null || targetActivityName == null) {
                LogTool.e("StartActivity Fail. Json analysis params(targetPackageName,targetActivityName) cant not is null.");
                return false;
            }
            Bundle bundle = new Bundle();
            if (paramKeysJsonArray != null && paramValuesJsonArray != null) {
                Object[] paramKeys = paramKeysJsonArray.toArray();
                Object[] paramValues = paramValuesJsonArray.toArray();
                if (paramKeys.length == paramValues.length) {
                    for (int i = 0; i < paramKeys.length; i++) {
                        bundle.putString(paramKeys[i].toString(), paramValues[i].toString());
                    }
                }
            }
            return ActivityTool.StartSelfActivityWithBundle(SystemTool.GetCurrentActivity().getPackageName(), targetPackageName, targetActivityName, bundle);
        } catch (Exception e) {
            LogTool.e(e);
            return false;
        }
    }

    /**
     * 启动应用
     *
     * @param json json参数
     * @return 是否成功 "{packageName:"com.xxx.xxx",paramKeys:["key1","key2"],paramValues:["value1","value2"]}"
     */
    public static Boolean StartSelfApp(String json) {
        if (json == null) {
            LogTool.e("StartApp Fail. Params(json) cant not is null.");
            return false;
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String packageName = jsonObject.getString("packageName");
            JSONArray paramKeysJsonArray = jsonObject.getJSONArray("paramKeys");
            JSONArray paramValuesJsonArray = jsonObject.getJSONArray("paramValues");
            if (packageName == null) {
                LogTool.e("StartApp Fail. Json analysis params(packageName) cant not is null.");
                return false;
            }
            Bundle bundle = new Bundle();
            if (paramKeysJsonArray != null && paramValuesJsonArray != null) {
                Object[] paramKeys = paramKeysJsonArray.toArray();
                Object[] paramValues = paramValuesJsonArray.toArray();
                if (paramKeys.length == paramValues.length) {
                    for (int i = 0; i < paramKeys.length; i++) {
                        bundle.putString(paramKeys[i].toString(), paramValues[i].toString());
                    }
                }
            }
            return ActivityTool.StartSelfAppWithBundle(packageName, bundle);
        } catch (Exception e) {
            LogTool.e(e);
            return false;
        }
    }

    /**
     * 获取指定路径的存储信息
     *
     * @param path 路径
     * @return 存储信息Json字符串
     */
    public static String GetStorageInfoAsJson(String path) {
        return StorageTool.GetStorageInfoAsJson(path);
    }

    /**
     * 获取自身存储信息
     *
     * @return 存储信息Json字符串
     */
    public static String GetSelfStorageInfoAsJson() {
        return StorageTool.GetSelfStorageInfoAsJson();
    }

    /**
     * 获取指定路径空余存储大小
     *
     * @param path 路径
     * @return 空余存储大小
     */
    public static Long GetFreeStorageSize(String path) {
        Long freeSize = 0L;
        try {
            JSONObject storageInfoJsonObject = StorageTool.GetStorageInfoAsJSONObject(path);
            freeSize = storageInfoJsonObject.getLong("free_bytes");
        } catch (Exception e) {
            LogTool.e(e);
        }
        return freeSize;
    }

    /**
     * 获取自身空余存储大小
     *
     * @return 空余存储大小
     */
    public static Long GetSelfFreeStorageSize() {
        Long freeSize = 0L;
        try {
            JSONObject storageInfoJsonObject = StorageTool.GetSelfStorageInfoAsJSONObject();
            freeSize = storageInfoJsonObject.getLong("free_bytes");
        } catch (Exception e) {
            LogTool.e(e);
        }
        return freeSize;
    }

    /**
     * 获取当前SchemeUri信息(最后一次触发的Url信息)
     *
     * @return SchemeUri信息Json字符串
     */
    public static String GetCurrentSchemeUriInfo() {
        Uri uri = URLSchemeTool.GetCurrentSchemeUri();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url", uri.toString());
            jsonObject.put("scheme", uri.getScheme());
            jsonObject.put("host", uri.getHost());
            jsonObject.put("port", uri.getPort());
            jsonObject.put("path", uri.getPath());
            jsonObject.put("pathSegments", uri.getPathSegments());
            jsonObject.put("encodedPath", uri.getEncodedPath());
            jsonObject.put("query", uri.getQuery());
        } catch (Exception e) {
            LogTool.e(e);
            jsonObject.put("error", e.getMessage());
        }
        return jsonObject.toJSONString();
    }
}
