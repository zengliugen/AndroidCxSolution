package com.unity.AndroidCxSolution;

import com.alibaba.fastjson.JSONObject;
import com.unity.cxsolution.Tools.LogTool;

public class Main {
    public static void main(String[] args) {
        System.out.println("xxxxxxxxxxx");
        String json = "{permissions:['123','456']}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        String[] permissions = new String[0];
        //noinspection SuspiciousToArrayCall
        permissions = jsonObject.getJSONArray("permissions").toArray(permissions);
        for (String p : permissions) {
            System.out.println(p);
        }
    }
}
