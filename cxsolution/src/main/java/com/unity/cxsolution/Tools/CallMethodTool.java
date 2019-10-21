package com.unity.cxsolution.Tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 调用函数工具
 */
public class CallMethodTool {
    /**
     * 调用静态函数
     *
     * @param className  类名
     * @param methodName 函数名
     * @param args       参数列表
     */
    public static void StaticCall(String className, String methodName, Object... args) {
        if (className == null || className.length() == 0 || methodName == null || methodName.length() == 0) {
            return;
        }
        try {
            Class<?> classObj = Class.forName(className);
            Method methodObj;
            if (args != null && args.length != 0) {
                Class[] paramTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    paramTypes[i] = args[i].getClass();
                }
                methodObj = classObj.getMethod(methodName, paramTypes);
            } else {
                methodObj = classObj.getMethod(methodName);
            }
            methodObj.invoke(classObj, args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
