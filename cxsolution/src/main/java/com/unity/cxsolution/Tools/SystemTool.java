package com.unity.cxsolution.Tools;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unity.cxsolution.Enter.CustomActivity;
import com.unity.cxsolution.Enter.CustomApplication;
import com.unity.cxsolution.Enter.CustomUnityActivity;
import com.unity.cxsolution.Tools.Permission.PermissionHelper;
import com.unity.cxsolution.Tools.Permission.PermissionInterface;
import com.unity.cxsolution.Tools.URLScheme.URLSchemeActivity;

import java.io.File;

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

    /**
     * 检测指定Context是否拥有指定权限
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
     * 在指定Activity对象中进行权限申请
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
     * 在指定Activity对象中进行权限申请
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
     * 在当前Activity对象中进行权限申请
     *
     * @param permission          权限名称
     * @param permissionsCallBack 权限申请回调
     */
    public static void RequestPermission(String permission, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (permission == null || permissionsCallBack == null) {
            LogTool.e("RequestPermission Fail. Params(permission,permissionsCallBack) cant not is null.");
            return;
        }
        String[] permissions = new String[]{permission};
        PermissionHelper.RequestPermissions(GetCurrentActivity(), permissions, permissionsCallBack);
    }

    /**
     * 在当前Activity对象中进行权限申请
     *
     * @param permissions         权限名称列表
     * @param permissionsCallBack 权限申请回调
     */
    public static void RequestPermissions(String[] permissions, PermissionInterface.PermissionsCallBack permissionsCallBack) {
        if (permissions == null || permissionsCallBack == null) {
            LogTool.e("RequestPermissions Fail. Params(permissions,permissionsCallBack) cant not is null.");
            return;
        }
        PermissionHelper.RequestPermissions(GetCurrentActivity(), permissions, permissionsCallBack);
    }

    /**
     * 启动Activity
     *
     * @param activity           载体Activity
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @return 是否成功
     */
    public static Boolean StartActivity(Activity activity, String sourcePackageName, String targetPackageName, String targetActivityName) {
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
     * 启动Activity
     *
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @return 是否成功
     */
    public static Boolean StartActivity(String sourcePackageName, String targetPackageName, String targetActivityName) {
        if (sourcePackageName == null || targetPackageName == null || targetActivityName == null) {
            LogTool.e("StartActivity Fail. Params(sourcePackageName,targetPackageName,targetActivityName) cant not is null.");
            return false;
        }
        return StartActivity(GetCurrentActivity(), sourcePackageName, targetPackageName, targetActivityName);
    }

    /**
     * 启动Activity并传参
     *
     * @param activity           载体Activity
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @param bundle             参数
     * @return 是否成功
     */
    public static Boolean StartActivity(Activity activity, String sourcePackageName, String targetPackageName, String targetActivityName, Bundle bundle) {
        if (activity == null || sourcePackageName == null || targetPackageName == null || targetActivityName == null || bundle == null) {
            LogTool.e("StartActivity Fail. Params(sourcePackageName,targetPackageName,targetActivityName,bundle) cant not is null.");
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
     * 启动Activity并传参
     *
     * @param sourcePackageName  来源包名
     * @param targetPackageName  目标包名
     * @param targetActivityName 目标Activity名称
     * @param bundle             参数
     * @return 是否成功
     */
    public static Boolean StartActivity(String sourcePackageName, String targetPackageName, String targetActivityName, Bundle bundle) {
        if (sourcePackageName == null || targetPackageName == null || targetActivityName == null || bundle == null) {
            LogTool.e("StartActivity Fail. Params(sourcePackageName,targetPackageName,targetActivityName,bundle) cant not is null.");
            return false;
        }
        return StartActivity(GetCurrentActivity(), sourcePackageName, targetPackageName, targetActivityName, bundle);
    }

    /**
     * 启动应用
     *
     * @param activity    载体Activity
     * @param packageName 应用包名
     * @return 是否成功
     */
    public static Boolean StartApp(Activity activity, String packageName) {
        if (packageName == null) {
            LogTool.e("StartApp Fail. Params(packageName) cant not is null.");
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
     * 启动应用
     *
     * @param packageName 应用包名
     * @return 是否成功
     */
    public static Boolean StartApp(String packageName) {
        if (packageName == null) {
            LogTool.e("StartApp Fail. Params(packageName) cant not is null.");
            return false;
        }
        return StartApp(GetCurrentActivity(), packageName);
    }

    /**
     * 启动应用
     *
     * @param activity    载体Activity
     * @param packageName 应用包名
     * @param bundle      参数
     * @return 是否成功
     */
    public static Boolean StartApp(Activity activity, String packageName, Bundle bundle) {
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
     * 启动应用
     *
     * @param packageName 应用包名
     * @param bundle      参数
     * @return 是否成功
     */
    public static Boolean StartApp(String packageName, Bundle bundle) {
        if (packageName == null || bundle == null) {
            LogTool.e("StartApp Fail. Params(packageName,bundle) cant not is null.");
            return false;
        }
        return StartApp(GetCurrentActivity(), packageName, bundle);
    }

    /**
     * 获取指定路径存储信息
     *
     * @param path 指定路径
     * @return 存储信息Json字符串
     */
    static JSONObject GetPathStorageInfoAsJSONObject(String path) {
        JSONObject storageInfo = new JSONObject();
        if (path == null) {
            LogTool.e("GetPathStorageInfoAsJSONObject Fail. Params(path) cant not is null.");
            return storageInfo;
        }
        try {
            StatFs statFs = new StatFs(path);
            storageInfo.put("path", path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                storageInfo.put("block_size", statFs.getBlockSizeLong());
                storageInfo.put("block_count", statFs.getBlockCountLong());
                storageInfo.put("available_blocks", statFs.getAvailableBlocksLong());
                storageInfo.put("free_blocks", statFs.getFreeBlocksLong());
                storageInfo.put("total_bytes", statFs.getTotalBytes());
                storageInfo.put("available_bytes", statFs.getAvailableBytes());
                storageInfo.put("free_bytes", statFs.getFreeBytes());
            } else {
                storageInfo.put("block_size", statFs.getBlockSize());
                storageInfo.put("block_count", statFs.getBlockCount());
                storageInfo.put("available_blocks", statFs.getAvailableBlocks());
                storageInfo.put("free_blocks", statFs.getFreeBlocks());
                storageInfo.put("total_bytes", statFs.getBlockSize() * statFs.getAvailableBlocks());
                storageInfo.put("available_bytes", statFs.getAvailableBlocks());
                storageInfo.put("free_bytes", statFs.getBlockSize() * statFs.getFreeBlocks());
            }
        } catch (Exception e) {
            storageInfo.put("error", e.getMessage());
            LogTool.e(e);
        }
        return storageInfo;
    }

    /**
     * 获取指定路径存储信息
     *
     * @param path 指定路径
     * @return 存储信息Json字符串
     */
    public static String GetPathStorageInfo(String path) {
        if (path == null) {
            LogTool.e("GetPathStorageInfo Fail. Params(path) cant not is null.");
            return "";
        }
        return GetPathStorageInfoAsJSONObject(path).toJSONString();
    }

    /**
     * 获取自身存储信息
     *
     * @return 存储信息Json字符串
     */
    public static String GetSelfStorageInfo() {
        try {
            String absolutePath = GetCurrentActivity().getFilesDir().getAbsolutePath();
            return GetPathStorageInfo(absolutePath);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取存储信息
     *
     * @return 存储信息Json字符串
     */
    public static String GetAllStorageInfo() {
        JSONObject storageInfo = new JSONObject();
        try {
            File dataFile = Environment.getDataDirectory();
            JSONObject dataStorageInfo = GetPathStorageInfoAsJSONObject(dataFile.getPath());
            storageInfo.put("data_storage_info", dataStorageInfo);
            JSONArray externalStorageInfoList = new JSONArray();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                File[] externalFiles = GetCurrentActivity().getExternalFilesDirs(Environment.MEDIA_MOUNTED);
                for (File externalFile : externalFiles) {
                    externalStorageInfoList.add(GetPathStorageInfoAsJSONObject(externalFile.getPath()));
                }
            } else {
                File externalFile = GetCurrentActivity().getExternalFilesDir(Environment.MEDIA_MOUNTED);
                if (externalFile != null) {
                    externalStorageInfoList.add(GetPathStorageInfoAsJSONObject(externalFile.getPath()));
                }
            }
            storageInfo.put("external_storage_info_list", externalStorageInfoList);
        } catch (Exception e) {
            storageInfo.put("error", e.getMessage());
            LogTool.e(e);
        }
        return storageInfo.toJSONString();
    }

    /**
     * 获取当前SchemeUri对象
     *
     * @return Uri对象
     */
    public static Uri GetCurrentSchemeUri() {
        return URLSchemeActivity.CurrentUri;
    }

    /**
     * 获取AndroidManifest文件中MetaData的Bundle
     *
     * @param context 上下文对象
     * @return Bundle
     */
    public static Bundle GetAndroidManifestMetaDataBundle(Context context) {
        if (context == null) {
            return null;
        }
        Bundle metaData = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            metaData = ai.metaData;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return metaData;
    }

    /**
     * 获取AndroidManifest文件中MetaData的Bundle
     *
     * @return Bundle
     */
    public static Bundle GetAndroidManifestMetaDataBundle() {
        return GetAndroidManifestMetaDataBundle(GetCurrentContext());
    }

    /**
     * 获取AndroidManifest文件中配置的meta-data值
     *
     * @param name 名称
     * @return 名称对应的值
     */
    public static String GetAndroidManifestMetaDataValue_String(String name) {
        Bundle bundle = GetAndroidManifestMetaDataBundle();
        return bundle.getString(name);
    }
}
