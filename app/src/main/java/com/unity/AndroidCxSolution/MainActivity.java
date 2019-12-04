package com.unity.AndroidCxSolution;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.unity.cxsolution.Enter.CustomActivity;
import com.unity.cxsolution.Tools.Permission.PermissionInterface;
import com.unity.cxsolution.Tools.Permission.PermissionResultInfo;
import com.unity.cxsolution.Tools.SystemTool;

import java.util.ArrayList;

public class MainActivity extends CustomActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetOnClickListener(R.id.btn_storage_info);
        SetOnClickListener(R.id.btn_request_permissions);
        SetOnClickListener(R.id.btn_open_url);
    }

    /**
     * 设置点击监听器
     *
     * @param id View组件ID
     */
    private void SetOnClickListener(int id) {
        View view = findViewById(id);
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d("onClick", "点击了按钮" + view.getId());
        switch (view.getId()) {
            case R.id.btn_storage_info:
                Log.d("onClick", SystemTool.GetAllStorageInfo());
                break;
            case R.id.btn_request_permissions:
                SystemTool.RequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionInterface.PermissionsCallBack() {
                    @Override
                    public void onRequestPermissionsResult(ArrayList<PermissionResultInfo> permissionResultInfoList) {
                        Log.d("onClick", permissionResultInfoList.toString());
                    }

                    @Override
                    public void onError(String error) {
                        Log.d("onClick", error);
                    }
                });
                break;
            case R.id.btn_open_url:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("cxsolution://open/com.dasd.ll/weq/123?a=2&b=3"));
                startActivity(i);
                break;
        }
    }
}
