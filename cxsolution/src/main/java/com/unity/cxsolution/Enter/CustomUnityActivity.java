package com.unity.cxsolution.Enter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.unity3d.player.UnityPlayerActivity;

@SuppressWarnings("NullableProblems")
public class CustomUnityActivity extends UnityPlayerActivity {
    /**
     * 当前Activity对象
     */
    @SuppressLint("StaticFieldLeak")
    public static Activity CurrentActivity;

    /**
     * @param methodName 函数名称
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    private static void TriggerBefore(String methodName, Object[] params, Class[] paramTypes) {
        EnterTool.TriggerBefore(EnterTool.ClassType_Activity, methodName, params, paramTypes);
    }

    /**
     * @param methodName 函数名称
     * @param params     参数列表
     * @param paramTypes 参数类型列表
     */
    private static void TriggerAfter(String methodName, Object[] params, Class[] paramTypes) {
        EnterTool.TriggerAfter(EnterTool.ClassType_Activity, methodName, params, paramTypes);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        CurrentActivity = this;
        TriggerBefore("onCreate", new Object[]{bundle}, new Class[]{Bundle.class});

        super.onCreate(bundle);

        TriggerAfter("onCreate", new Object[]{bundle}, new Class[]{Bundle.class});
    }

    @Override
    protected void onNewIntent(Intent intent) {
        TriggerBefore("onNewIntent", new Object[]{intent}, new Class[]{Intent.class});

        super.onNewIntent(intent);

        TriggerAfter("onNewIntent", new Object[]{intent}, new Class[]{Intent.class});
    }

    @Override
    protected void onStart() {
        TriggerBefore("onStart", null, null);

        super.onStart();

        TriggerAfter("onStart", null, null);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        TriggerBefore("onRestoreInstanceState", new Object[]{savedInstanceState}, new Class[]{Bundle.class});

        super.onRestoreInstanceState(savedInstanceState);

        TriggerAfter("onRestoreInstanceState", new Object[]{savedInstanceState}, new Class[]{Bundle.class});
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        TriggerBefore("onRestoreInstanceState", new Object[]{savedInstanceState, persistentState}, new Class[]{Bundle.class, PersistableBundle.class});

        super.onRestoreInstanceState(savedInstanceState, persistentState);

        TriggerAfter("onRestoreInstanceState", new Object[]{savedInstanceState, persistentState}, new Class[]{Bundle.class, PersistableBundle.class});
    }

    @Override
    protected void onResume() {
        TriggerBefore("onResume", null, null);

        super.onResume();

        TriggerAfter("onResume", null, null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TriggerBefore("onSaveInstanceState", new Object[]{outState}, new Class[]{Bundle.class});

        super.onSaveInstanceState(outState);

        TriggerAfter("onSaveInstanceState", new Object[]{outState}, new Class[]{Bundle.class});
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        TriggerBefore("onSaveInstanceState", new Object[]{outState, outPersistentState}, new Class[]{Bundle.class, PersistableBundle.class});

        super.onSaveInstanceState(outState, outPersistentState);

        TriggerAfter("onSaveInstanceState", new Object[]{outState, outPersistentState}, new Class[]{Bundle.class, PersistableBundle.class});
    }

    @Override
    protected void onPause() {
        TriggerBefore("onPause", null, null);

        super.onPause();

        TriggerAfter("onPause", null, null);
    }

    @Override
    protected void onStop() {
        TriggerBefore("onStop", null, null);

        super.onStop();

        TriggerAfter("onStop", null, null);
    }

    @Override
    protected void onRestart() {
        TriggerBefore("onRestart", null, null);

        super.onRestart();

        TriggerAfter("onRestart", null, null);
    }

    @Override
    protected void onDestroy() {
        TriggerBefore("onDestroy", null, null);

        super.onDestroy();

        TriggerAfter("onDestroy", null, null);
        CurrentActivity = null;
    }

    @Override
    public void onLowMemory() {
        TriggerBefore("onLowMemory", null, null);

        super.onLowMemory();

        TriggerAfter("onLowMemory", null, null);
    }

    @Override
    public void onTrimMemory(int level) {
        TriggerBefore("onTrimMemory", new Object[]{level}, new Class[]{int.class});

        super.onTrimMemory(level);

        TriggerAfter("onTrimMemory", new Object[]{level}, new Class[]{int.class});
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        TriggerBefore("Configuration", new Object[]{newConfig}, new Class[]{Configuration.class});

        super.onConfigurationChanged(newConfig);

        TriggerAfter("Configuration", new Object[]{newConfig}, new Class[]{Configuration.class});
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        TriggerBefore("onWindowFocusChanged", new Object[]{hasFocus}, new Class[]{boolean.class});

        super.onWindowFocusChanged(hasFocus);

        TriggerAfter("onWindowFocusChanged", new Object[]{hasFocus}, new Class[]{boolean.class});
    }
}
