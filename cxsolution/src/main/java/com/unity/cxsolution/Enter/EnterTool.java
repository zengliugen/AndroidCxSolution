package com.unity.cxsolution.Enter;

import android.content.res.AssetManager;

import com.unity.cxsolution.Log.LogTool;
import com.unity.cxsolution.Reflex.ReflexTool;
import com.unity.cxsolution.System.SystemTool;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 入口工具
 */
class EnterTool {

    /**
     * 函数信息
     */
    private static class MethodInfo {
        /**
         * SDK名称
         */
        String sdkName;
        /**
         * 类名称
         */
        String className;
        /**
         * 函数名称
         */
        String methodName;
        /**
         * 参数类型列表
         */
        Class[] paramTypes;

        /**
         * 获得函数信息类实例
         *
         * @return 函数信息类实例
         */
        static MethodInfo getInstance() {
            return new MethodInfo();
        }
    }

    /**
     * 类类型 Application
     */
    static final String ClassType_Application = "Application";
    /**
     * 类类型 Activity
     */
    static final String ClassType_Activity = "Activity";
    /**
     * 函数信息列表 Application
     */
    private static ArrayList<MethodInfo> applicationMethodInfoList;
    /**
     * 函数信息列表 Activity
     */
    private static ArrayList<MethodInfo> activityMethodInfoList;
    /**
     * 是否初始化
     */
    private static Boolean isInit = false;

    /**
     * 初始化函数信息列表
     */
    private static void InitMethodInfoList() {
        if (isInit) {
            return;
        }
        long startTime = System.nanoTime();
        LogTool.d("开始初始化SDK函数信息列表");
        applicationMethodInfoList = new ArrayList<>();
        activityMethodInfoList = new ArrayList<>();
        try {
            AssetManager assetManager = SystemTool.GetCurrentAssetManager();
            assert assetManager != null;
            String[] configFiles = assetManager.list("EnterConfig");
            if (configFiles != null) {
                for (String configFile : configFiles) {
                    InputStream inputStream = assetManager.open("EnterConfig/" + configFile);
                    HandleEnterConfig(inputStream);
                }
            }
        } catch (Exception e) {
            LogTool.e(e);
        }
        long consumingTime = System.nanoTime() - startTime;
        LogTool.d("初始化SDK函数信息列表完成 time:%s(ns)%s(ms)", consumingTime, consumingTime / 1000000);
        isInit = true;
    }

    /**
     * 处理入口配置
     *
     * @param inputStream 文件流
     */
    private static void HandleEnterConfig(InputStream inputStream) {
        if (inputStream == null) return;
        try {
            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParser.setInput(inputStream, "utf-8");
            String sdkName = "";
            String className = "";
            String classType = "";
            ArrayList<String> beforeMethodNames = new ArrayList<>();
            ArrayList<String> afterMethodNames = new ArrayList<>();
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String startTagName = xmlPullParser.getName();
                        switch (startTagName) {
                            case "ClassInfos":
                                sdkName = xmlPullParser.getAttributeValue(null, "sdkName");
                                break;
                            case "Class":
                                className = xmlPullParser.getAttributeValue(null, "className");
                                classType = xmlPullParser.getAttributeValue(null, "classType");
                                break;
                            case "Method":
                                String methodType = xmlPullParser.getAttributeValue(null, "methodType");
                                String methodName = xmlPullParser.getAttributeValue(null, "methodName");
                                if (methodType.equals("Before"))
                                    beforeMethodNames.add(methodName);
                                else if (methodType.equals("After"))
                                    afterMethodNames.add(methodName);
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("Class")) {
                            HandleMethodInfo(sdkName, className, classType, beforeMethodNames, afterMethodNames);
                            beforeMethodNames.clear();
                            afterMethodNames.clear();
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
            LogTool.d("处理SDK入口完成 sdkName:" + sdkName);
        } catch (Exception e) {
            LogTool.e(e);
        }
    }

    /**
     * 处理函数信息
     *
     * @param sdkName           SDK名称
     * @param className         类名称
     * @param classType         类类型
     * @param beforeMethodNames 之前函数名称列表
     * @param afterMethodNames  之后函数名称列表
     */
    private static void HandleMethodInfo(String sdkName, String className, String classType, ArrayList<String> beforeMethodNames, ArrayList<String> afterMethodNames) {
        try {
            Class classObject = Class.forName(className);
            Method[] methods = classObject.getMethods();
            for (Method method : methods) {
                if (beforeMethodNames.contains(method.getName()) || afterMethodNames.contains(method.getName())) {
                    MethodInfo methodInfo = MethodInfo.getInstance();
                    methodInfo.sdkName = sdkName;
                    methodInfo.className = className;
                    methodInfo.methodName = method.getName();
                    methodInfo.paramTypes = method.getParameterTypes();
                    if (classType.equals(ClassType_Application)) {
                        applicationMethodInfoList.add(methodInfo);
                    } else if (classType.equals(ClassType_Activity)) {
                        activityMethodInfoList.add(methodInfo);
                    }
                }
            }
        } catch (Exception e) {
            LogTool.e(e);
        }
    }

    /**
     * 触发函数
     *
     * @param classType  类类型
     * @param methodName 函数名称
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    private static void Trigger(String classType, String methodName, Object[] params, Class[] paramTypes) {
        if (methodName == null || methodName.length() == 0) {
            return;
        }
        InitMethodInfoList();
        LogTool.d("Trigger classType:%s methodName:%s params:%s\n", classType, methodName, Arrays.toString(params));
        try {
            if (params == null) {
                params = new Object[0];
            }
            if (paramTypes == null) {
                paramTypes = new Class[0];
            }
            ArrayList<MethodInfo> methodInfoArrayList = null;
            if (classType.equals(ClassType_Application)) {
                methodInfoArrayList = applicationMethodInfoList;
            } else if (classType.equals(ClassType_Activity)) {
                methodInfoArrayList = activityMethodInfoList;
            }
            if (methodInfoArrayList == null) return;
            for (MethodInfo methodInfo : methodInfoArrayList) {
                if (methodInfo.methodName.equals(methodName)) {
                    if (paramTypes.length == methodInfo.paramTypes.length) {
                        boolean isSame = true;
                        for (int i = 0; i < paramTypes.length; i++) {
                            if (!paramTypes[i].equals(methodInfo.paramTypes[i])) {
                                isSame = false;
                                break;
                            }
                        }
                        if (isSame) {
                            ReflexTool.StaticCall(methodInfo.className, methodName, params, paramTypes);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogTool.e(e);
        }
    }

    /**
     * 触发函数_之前
     *
     * @param classType  类类型
     * @param methodName 函数名称
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    static void TriggerBefore(String classType, String methodName, Object[] params, Class[] paramTypes) {
        Trigger(classType, methodName + "_Before", params, paramTypes);
    }

    /**
     * 触发函数_之后
     *
     * @param classType  类类型
     * @param methodName 函数名称
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    static void TriggerAfter(String classType, String methodName, Object[] params, Class[] paramTypes) {
        Trigger(classType, methodName + "_After", params, paramTypes);
    }
}
