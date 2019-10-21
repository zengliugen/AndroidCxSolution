package com.unity.cxsolution.Enter;

import android.content.res.AssetManager;

import com.unity.cxsolution.Tools.CallMethodTool;
import com.unity3d.player.UnityPlayer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
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
     * 函数信息列表
     */
    private static ArrayList<MethodInfo> methodInfoList;

    static {
        InitMethodInfoList();
    }

    /**
     * 初始化函数信息列表
     */
    private static void InitMethodInfoList() {
        methodInfoList = new ArrayList<>();
        try {
            AssetManager assetManager = UnityPlayer.currentActivity.getAssets();
            String[] configFiles = assetManager.list("EnterConfig/");
//            String[] configFiles = {"cxsolution/src/main/assets/EnterConfig/EnterConfig_default.xml"};
            if (configFiles != null) {
                for (String configFile : configFiles) {
                    InputStream inputStream = assetManager.open(configFile);
//                    InputStream inputStream = new FileInputStream(configFile);
                    HandleEnterConfig(inputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("初始化SDK函数信息列表完成");
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
                        HandleMethodInfo(sdkName, className, beforeMethodNames, afterMethodNames);
                    }
                }
            }
            System.out.format("处理SDK入口完成 SDKName:%s\n", sdkName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理函数信息
     *
     * @param sdkName           SDK名称
     * @param className         类名称
     * @param beforeMethodNames 之前函数名称列表
     * @param afterMethodNames  之后函数名称列表
     */
    private static void HandleMethodInfo(String sdkName, String className, ArrayList<String> beforeMethodNames, ArrayList<String> afterMethodNames) {
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
                    methodInfoList.add(methodInfo);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 触发函数
     *
     * @param methodName 函数名称
     * @param args       参数列表
     */
    private static void Trigger(String methodName, Object... args) {
        if (methodName == null || methodName.length() == 0) {
            return;
        }
        Class[] paramTypes;
        if (args != null && args.length != 0) {
            paramTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i].getClass();
            }
        } else {
            paramTypes = new Class[0];
        }
        for (MethodInfo methodInfo : methodInfoList) {
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
                        CallMethodTool.StaticCall(methodInfo.className, methodName, args);
                    }
                }
            }
        }
    }

    /**
     * 触发函数_之前
     *
     * @param methodName 函数名称
     * @param args       参数列表
     */
    public static void TriggerBefore(String methodName, Object... args) {
        System.out.format("TriggerBefore methodName:%s args:%s\n", methodName, Arrays.toString(args));
        Trigger(methodName + "_Before", args);
    }

    /**
     * 触发函数_之后
     *
     * @param methodName 函数名称
     * @param args       参数列表
     */
    public static void TriggerAfter(String methodName, Object... args) {
        System.out.format("TriggerAfter methodName:%s args:%s\n", methodName, Arrays.toString(args));
        Trigger(methodName + "_After", args);
    }
}
