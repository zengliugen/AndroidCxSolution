package com.unity.cxsolution.View;

import android.content.Context;
import android.widget.Toast;

import com.unity.cxsolution.Log.LogTool;
import com.unity.cxsolution.System.SystemTool;

/**
 * 显示工具
 */
@SuppressWarnings({"unused"})
public class ViewTool {
    /**
     * 指定Context展示消息提示
     *
     * @param context 指定Context
     * @param msg     消息
     */
    public static void MakeText(Context context, String msg) {
        if (context == null || msg == null) {
            LogTool.e("MakeText Fail. Params(context,msg) cant not is null.");
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示消息提示
     *
     * @param msg 消息
     */
    public static void MakeSelfText(String msg) {
        if (msg == null) {
            LogTool.e("MakeSelfText Fail. Params(msg) cant not is null.");
            return;
        }
        Toast.makeText(SystemTool.GetCurrentContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
