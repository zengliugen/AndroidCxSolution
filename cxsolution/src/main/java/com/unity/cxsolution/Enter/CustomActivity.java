package com.unity.cxsolution.Enter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomActivity extends AppCompatActivity {
    /**
     * 当前Activity对象
     */
    @SuppressLint("StaticFieldLeak")
    public static Activity CurrentActivity;

    /**
     * @param methodName 函数名称
     * @param args       参数列表
     */
    private static void TriggerBefore(String methodName, Object... args) {
        Tool.TriggerBefore(Tool.ClassType_Activity, methodName, args);
    }

    /**
     * @param methodName 函数名称
     * @param args       参数列表
     */
    private static void TriggerAfter(String methodName, Object... args) {
        Tool.TriggerAfter(Tool.ClassType_Activity, methodName, args);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        CurrentActivity = this;
        TriggerBefore("onCreate", bundle);

        super.onCreate(bundle);

        TriggerAfter("onCreate", bundle);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        TriggerBefore("onNewIntent", intent);

        super.onNewIntent(intent);

        TriggerAfter("onNewIntent", intent);
    }

    @Override
    protected void onStart() {
        TriggerBefore("onStart");

        super.onStart();

        TriggerAfter("onStart");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        TriggerBefore("onRestoreInstanceState", savedInstanceState);

        super.onRestoreInstanceState(savedInstanceState);

        TriggerAfter("onRestoreInstanceState", savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        TriggerBefore("onRestoreInstanceState", savedInstanceState, persistentState);

        super.onRestoreInstanceState(savedInstanceState, persistentState);

        TriggerAfter("onRestoreInstanceState", savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        TriggerBefore("onResume");

        super.onResume();

        TriggerAfter("onResume");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        TriggerBefore("onSaveInstanceState", outState);

        super.onSaveInstanceState(outState);

        TriggerAfter("onSaveInstanceState", outState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        TriggerBefore("onSaveInstanceState", outState, outPersistentState);

        super.onSaveInstanceState(outState, outPersistentState);

        TriggerAfter("onSaveInstanceState", outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        TriggerBefore("onPause");

        super.onPause();

        TriggerAfter("onPause");
    }

    @Override
    protected void onStop() {
        TriggerBefore("onStop");

        super.onStop();

        TriggerAfter("onStop");
    }

    @Override
    protected void onRestart() {
        TriggerBefore("onRestart");

        super.onRestart();

        TriggerAfter("onRestart");
    }

    @Override
    protected void onDestroy() {
        TriggerBefore("onDestroy");

        super.onDestroy();

        TriggerAfter("onDestroy");
        CurrentActivity = null;
    }

    @Override
    public void onLowMemory() {
        TriggerBefore("onLowMemory");

        super.onLowMemory();

        TriggerAfter("onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        TriggerBefore("onTrimMemory", level);

        super.onTrimMemory(level);

        TriggerAfter("onTrimMemory", level);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        TriggerBefore("Configuration", newConfig);

        super.onConfigurationChanged(newConfig);

        TriggerAfter("Configuration", newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        TriggerBefore("onWindowFocusChanged", hasFocus);

        super.onWindowFocusChanged(hasFocus);

        TriggerBefore("onWindowFocusChanged", hasFocus);
    }
}
