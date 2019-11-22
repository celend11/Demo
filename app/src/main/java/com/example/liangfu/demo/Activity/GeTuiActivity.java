package com.example.liangfu.demo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.liangfu.demo.R;
import com.example.liangfu.demo.Service.DemoPushService;
import com.igexin.sdk.PushManager;

public class GeTuiActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getui);
        PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
    }
    public void unBindAlias(View view){
        PushManager.getInstance().unBindAlias(GeTuiActivity.this, "123456", false);
    }

    public void bindAlias(View view){
        PushManager.getInstance().bindAlias(GeTuiActivity.this, "123456");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
