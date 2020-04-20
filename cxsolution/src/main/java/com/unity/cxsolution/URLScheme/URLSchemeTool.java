package com.unity.cxsolution.URLScheme;

import android.net.Uri;


@SuppressWarnings({"unused", "WeakerAccess"})
public class URLSchemeTool {


    public static Uri GetCurrentSchemeUriAndroid() {
        return com.unity.cxsolution.URLScheme.android.URLSchemeActivity.CurrentUri;
    }

    /**
     * 获取当前SchemeUri对象(Androidx)
     *
     * @return Uri对象
     */
    public static Uri GetCurrentSchemeUriAndroidx() {
        return com.unity.cxsolution.URLScheme.androidx.URLSchemeActivity.CurrentUri;
    }

    /**
     * 获取当前SchemeUri对象
     *
     * @return Uri对象
     */
    public static Uri GetCurrentSchemeUri() {
        Uri uri = null;
        try {
            uri = GetCurrentSchemeUriAndroidx();
        } catch (NoClassDefFoundError ignored) {
            // 可能有项目并未使用androidX
        }
        if (uri == null) {
            uri = GetCurrentSchemeUriAndroid();
        }
        return uri;
    }
}
