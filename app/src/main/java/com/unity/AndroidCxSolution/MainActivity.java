package com.unity.AndroidCxSolution;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.unity.cxsolution.Enter.CustomActivity;

public class MainActivity extends CustomActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetOnClickListener(R.id.btn_request_permission);
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
        //noinspection SwitchStatementWithTooFewBranches
        switch (view.getId()) {
            case R.id.btn_request_permission:
                break;
        }
    }
}
