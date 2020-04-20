package com.unity.cxsolution.URLScheme.android;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.unity.cxsolution.Log.LogTool;
import com.unity.cxsolution.R;
import com.unity.cxsolution.System.ActivityTool;
import com.unity.cxsolution.System.SystemTool;

import java.util.List;
import java.util.Set;

/**
 * 处理超链接的Activity
 */
public class URLSchemeActivity extends Activity {
    /**
     * 当前Uri对象
     */
    public static Uri CurrentUri;
    /**
     * 打开APP host
     */
    private static final String OpenAppHost = "open";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            CurrentUri = getIntent().getData();
            HandleCurrentUri();
        } catch (Exception e) {
            LogTool.e(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CurrentUri = null;
    }

    /**
     * 处理URl scheme
     */
    private void HandleCurrentUri() {
        if (CurrentUri == null) {
            return;
        }
        try {
            String scheme = CurrentUri.getScheme();
            String host = CurrentUri.getHost();
            if (scheme == null || host == null) {
                return;
            }
            if (scheme.equals(SystemTool.GetCurrentActivity().getResources().getString(R.string.url_scheme_name_android))) {
                if (host.equals(OpenAppHost)) {
                    List<String> pathSplits = CurrentUri.getPathSegments();
                    if (pathSplits.size() == 1) {
                        String packageName = pathSplits.get(0);
                        Set<String> queryParameterNames = CurrentUri.getQueryParameterNames();
                        Bundle bundle = new Bundle();
                        for (String queryParameterName : queryParameterNames) {
                            bundle.putString(queryParameterName, CurrentUri.getQueryParameter(queryParameterName));
                        }
                        ActivityTool.StartAppWithBundle(this, packageName, bundle);
                    }
                }
            }
        } catch (Exception e) {
            LogTool.e(e);
        }
    }
}
