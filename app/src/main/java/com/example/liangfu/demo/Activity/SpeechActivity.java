package com.example.liangfu.demo.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.liangfu.demo.R;

import java.util.ArrayList;

public class SpeechActivity extends Activity {

    EventManager asr;
    int REQUST_RECORD_AUDIO =1;
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(yourListener);
        /**
         * 有2个listner，一个是用户自己的业务逻辑，如MessageStatusRecogListener。另一个是UI对话框的。
         * 使用这个ChainRecogListener把两个listener和并在一起
         */

        // DigitalDialogInput 输入 ，MessageStatusRecogListener可替换为用户自己业务逻辑的listener
//        chainRecogListener.addListener(new MessageStatusRecogListener(handler));
//        myRecognizer.setEventListener(chainRecogListener); // 替换掉原来的listener


    }

    private boolean checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .RECORD_AUDIO)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
                return false;
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUST_RECORD_AUDIO);
            return false;

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public void startspeech(View view){
        if(checkPermission()){
            String json ="{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":15373}";
            asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUST_RECORD_AUDIO) {
            Toast.makeText(getApplicationContext(),"授权成功",Toast.LENGTH_SHORT).show();
            String json ="{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":15373}";
            asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
        }else{
            Toast.makeText(getApplicationContext(),"授权拒绝",Toast.LENGTH_SHORT).show();
        }
    }
    EventListener yourListener = new EventListener() {
        @Override
        public void onEvent(String name, String params, byte [] data, int offset, int length) {
            if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)){
                Toast.makeText(SpeechActivity.this,"用户可以说话了",Toast.LENGTH_LONG).show();
// 引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了
            }
            if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)){
                AlertDialog.Builder dialog = new AlertDialog.Builder(SpeechActivity.this)
                        .setMessage(result)
                        .setTitle("百度语音")
                        .setPositiveButton("确定",null);
                dialog.show();

// 识别结束
            }
            if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)){
                result = result+params;
//                Toast.makeText(SpeechActivity.this,name+params,Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder dialog = new AlertDialog.Builder(SpeechActivity.this)
//                        .setMessage(params)
//                        .setTitle("百度语音")
//                        .setPositiveButton("确定",null);
//                dialog.show();
            }
//            Toast.makeText(SpeechActivity.this,name+params,Toast.LENGTH_SHORT).show();
// ... 支持的输出事件和事件支持的事件参数见“输入和输出参数”一节
        }
    };
    /**
     * 开始录音，点击“开始”按钮后调用。
     */
//    @Override
    protected void start() {
        // 此处params可以打印出来，直接写到你的代码里去，最终的json一致即可。
//        final Map<String, Object> params = fetchParams();

        // BaiduASRDigitalDialog的输入参数
//        input = new DigitalDialogInput(myRecognizer, chainRecogListener, params);
//        BaiduASRDigitalDialog.setInput(input); // 传递input信息，在BaiduASRDialog中读取,
//        Intent intent = new Intent(this, BaiduASRDigitalDialog.class);

        // 修改对话框样式
        // intent.putExtra(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_ORANGE_DEEPBG);

//        running = true;
//        startActivityForResult(intent, 2);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.unregisterListener(yourListener);
    }
}
