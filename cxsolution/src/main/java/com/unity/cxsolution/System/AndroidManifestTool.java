package com.unity.cxsolution.System;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.unity.cxsolution.Log.LogTool;

/**
 * AndroidManifest相关工具
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class AndroidManifestTool {
    /**
     * 获取指定Context对象的AndroidManifest文件中MetaData的Bundle
     *
     * @param context 上下文对象
     * @return Bundle
     */
    public static Bundle GetMetaDataBundle(Context context) {
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
     * 获取当前Context对象的AndroidManifest文件中MetaData的Bundle
     *
     * @return Bundle
     */
    public static Bundle GetSelfMetaDataBundle() {
        return GetMetaDataBundle(SystemTool.GetCurrentContext());
    }

    /**
     * 获取指定Context对象的AndroidManifest文件中配置的meta-data值(String)
     *
     * @param name 名称
     * @return 名称对应的值
     */
    public static String GetMetaDataValue_String(Context context, String name) {
        if (context == null || name == null) {
            LogTool.e("GetMetaDataValue_String Fail. Params(context,name) cant not is null.");
            return "";
        }
        String value;
        try {
            Bundle bundle = GetMetaDataBundle(context);
            value = bundle.getString(name);
        } catch (Exception e) {
            value = "";
            LogTool.e(e);
        }
        return value;
    }

    /**
     * 获取当前Context对象的AndroidManifest文件中配置的meta-data值(String)
     *
     * @param name 名称
     * @return 名称对应的值
     */
    public static String GetSelfMetaDataValue_String(String name) {
        if (name == null) {
            LogTool.e("GetSelfMetaDataValue_String Fail. Params(name) cant not is null.");
            return "";
        }
        String value;
        try {
            Bundle bundle = GetSelfMetaDataBundle();
            value = bundle.getString(name);
        } catch (Exception e) {
            value = "";
            LogTool.e(e);
        }
        return value;
    }
}
