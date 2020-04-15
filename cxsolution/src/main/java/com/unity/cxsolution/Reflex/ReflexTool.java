package com.unity.cxsolution.Reflex;

import com.unity.cxsolution.Log.LogTool;

import java.lang.reflect.Method;

/**
 * 调用函数工具
 */
public class ReflexTool {
    /**
     * 调用静态函数
     *
     * @param className  类名
     * @param methodName 函数名
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    public static void StaticCall(String className, String methodName, Object[] params, Class[] paramTypes) {
        if (className == null || className.length() == 0 || methodName == null || methodName.length() == 0) {
            return;
        }
        try {
            Class<?> classObj = Class.forName(className);
            Method methodObj;
            if (paramTypes != null && paramTypes.length != 0) {
                methodObj = classObj.getMethod(methodName, paramTypes);
            } else {
                methodObj = classObj.getMethod(methodName);
            }
            methodObj.invoke(classObj, params);
        } catch (Exception e) {
            LogTool.e(e);
        }
    }
}
