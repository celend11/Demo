package com.example.liangfu.demo.Service;

import android.content.Intent;

import com.igexin.sdk.PushService;

public class DemoPushService extends PushService {
    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        return super.onStartCommand(intent, i, i1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
