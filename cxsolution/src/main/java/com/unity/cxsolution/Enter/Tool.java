package com.unity.cxsolution.Enter;

import android.content.res.AssetManager;

import com.unity.cxsolution.Tools.CallMethodTool;
import com.unity.cxsolution.Tools.LogTool;
import com.unity.cxsolution.Tools.SystemTool;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 入口工具
 */
class Tool {

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
        applicationMethodInfoList = new ArrayList<>();
        activityMethodInfoList = new ArrayList<>();
        try {
            AssetManager assetManager = SystemTool.GetCurrentAssetManager();
            assert assetManager != null;
            String[] configFiles = assetManager.list("EnterConfig");
//            String[] configFiles = {"cxsolution/src/main/assets/EnterConfig/EnterConfig_default.xml"};
            if (configFiles != null) {
                for (String configFile : configFiles) {
                    InputStream inputStream = assetManager.open("EnterConfig/" + configFile);
//                    InputStream inputStream = new FileInputStream(configFile);
                    HandleEnterConfig(inputStream);
                }
            }
        } catch (Exception e) {
            LogTool.e(e);
        }
        LogTool.d("初始化SDK函数信息列表完成");
        isInit = true;
    }

    /**
     * 处理入口配置
     *
     * @param inputStream 输入流
     */
    private static void HandleEnterConfig(InputStream inputStream) {
        if (inputStream == null) return;
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);
            Element root = document.getRootElement();
            Element temp;
            String sdkName = null;
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                temp = (Element) i.next();
                if (temp != null) {
                    if (temp.getName().equals("SDKName")) {
                        sdkName = temp.getStringValue();
                    } else if (temp.getName().equals("Class")) {
                        String classType;
                        classType = temp.attribute("classType").getValue();
                        String className = null;
                        ArrayList<String> beforeMethodNames = new ArrayList<>();
                        ArrayList<String> afterMethodNames = new ArrayList<>();
                        for (Iterator j = temp.elementIterator(); j.hasNext(); ) {
                            temp = (Element) j.next();
                            if (temp != null) {
                                String keyName = temp.getName();
                                switch (keyName) {
                                    case "ClassName":
                                        className = temp.getStringValue();
                                        break;
                                    case "BeforeMethods":
                                        for (Iterator k = temp.elementIterator(); k.hasNext(); ) {
                                            temp = (Element) k.next();
                                            if (temp != null) {
                                                if (temp.getName().equals("Method")) {
                                                    beforeMethodNames.add(temp.getStringValue());
                                                }
                                            }
                                        }
                                        break;
                                    case "AfterMethods":
                                        for (Iterator k = temp.elementIterator(); k.hasNext(); ) {
                                            temp = (Element) k.next();
                                            if (temp != null) {
                                                if (temp.getName().equals("Method")) {
                                                    afterMethodNames.add(temp.getStringValue());
                                                }
                                            }
                                        }
                                        break;
                                }
                            }
                        }
                        HandleMethodInfo(sdkName, className, classType, beforeMethodNames, afterMethodNames);
                    }
                }
            }
            LogTool.d("处理SDK入口完成 SDKName:" + sdkName);
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
        LogTool.d(String.format("Trigger classType:%s methodName:%s params:%s\n", classType, methodName, Arrays.toString(params)));
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
                            CallMethodTool.StaticCall(methodInfo.className, methodName, params, paramTypes);
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
