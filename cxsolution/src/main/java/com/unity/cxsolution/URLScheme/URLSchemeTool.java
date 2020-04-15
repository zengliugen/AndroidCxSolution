package com.unity.cxsolution.URLScheme;

import android.net.Uri;

@SuppressWarnings("unused")
public class URLSchemeTool {

    /**
     * 获取当前SchemeUri对象
     *
     * @return Uri对象
     */
    public static Uri GetCurrentSchemeUri() {
        return URLSchemeActivity.CurrentUri;
    }

}
