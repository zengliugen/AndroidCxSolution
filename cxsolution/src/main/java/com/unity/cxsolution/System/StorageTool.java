package com.unity.cxsolution.System;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unity.cxsolution.Log.LogTool;

import java.io.File;

@SuppressWarnings({"WeakerAccess", "unused"})
public class StorageTool {
    /**
     * 获取指定路径存储信息(JSONObject格式)
     *
     * @param path 指定路径
     * @return 存储信息JSONObject对象
     */
    public static JSONObject GetStorageInfoAsJSONObject(String path) {
        JSONObject storageInfo = new JSONObject();
        if (path == null) {
            LogTool.e("GetStorageInfoAsJSONObject Fail. Params(path) cant not is null.");
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
     * 获取自身存储信息(JSONObject格式)
     *
     * @return 存储信息JSONObject对象
     */
    public static JSONObject GetSelfStorageInfoAsJSONObject() {
        try {
            String absolutePath = SystemTool.GetCurrentActivity().getFilesDir().getAbsolutePath();
            return GetStorageInfoAsJSONObject(absolutePath);
        } catch (Exception e) {
            JSONObject storageInfo = new JSONObject();
            storageInfo.put("error", e.getMessage());
            LogTool.e(e);
            return storageInfo;
        }
    }

    /**
     * 获取指定路径存储信息(Json格式)
     *
     * @param path 指定路径
     * @return 存储信息Json字符串
     */
    public static String GetStorageInfoAsJson(String path) {
        if (path == null) {
            LogTool.e("GetStorageInfoAsJson Fail. Params(path) cant not is null.");
            return "";
        }
        return GetStorageInfoAsJSONObject(path).toJSONString();
    }

    /**
     * 获取自身存储信息(Json格式)
     *
     * @return 存储信息Json字符串(Json格式)
     */
    public static String GetSelfStorageInfoAsJson() {
        try {
            String absolutePath = SystemTool.GetCurrentActivity().getFilesDir().getAbsolutePath();
            return GetStorageInfoAsJson(absolutePath);
        } catch (Exception e) {
            LogTool.e(e);
            return "";
        }
    }

    /**
     * 获取所有存储信息(所有挂载的存储介质)(JSONObject对象)
     *
     * @return 存储信息JSONObject对象
     */
    public static JSONObject GetAllStorageInfoAsJSONObject() {
        JSONObject storageInfo = new JSONObject();
        try {
            File dataFile = Environment.getDataDirectory();
            JSONObject dataStorageInfo = GetStorageInfoAsJSONObject(dataFile.getPath());
            storageInfo.put("data_storage_info", dataStorageInfo);
            JSONArray externalStorageInfoList = new JSONArray();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                File[] externalFiles = SystemTool.GetCurrentActivity().getExternalFilesDirs(Environment.MEDIA_MOUNTED);
                for (File externalFile : externalFiles) {
                    externalStorageInfoList.add(GetStorageInfoAsJSONObject(externalFile.getPath()));
                }
            } else {
                File externalFile = SystemTool.GetCurrentActivity().getExternalFilesDir(Environment.MEDIA_MOUNTED);
                if (externalFile != null) {
                    externalStorageInfoList.add(GetStorageInfoAsJSONObject(externalFile.getPath()));
                }
            }
            storageInfo.put("external_storage_info_list", externalStorageInfoList);
        } catch (Exception e) {
            storageInfo.put("error", e.getMessage());
            LogTool.e(e);
        }
        return storageInfo;
    }

    /**
     * 获取所有存储信息(所有挂载的存储介质)(Json字符串)
     *
     * @return 存储信息Json字符串
     */
    public static String GetAllStorageInfoAsJson() {
        return GetAllStorageInfoAsJSONObject().toJSONString();
    }
}
