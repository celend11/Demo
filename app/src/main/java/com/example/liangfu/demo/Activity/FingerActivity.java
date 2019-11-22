package com.example.liangfu.demo.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
//import android.os.CancellationSignal;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
//import android.support.v4.os.CancellationSignal;

import com.example.liangfu.demo.R;

import javax.crypto.Cipher;

public class FingerActivity extends Activity {
     private FingerprintManager   mFingerprintManger;
     private CancellationSignal cancellationSignal;
    private Cipher mCipher;
    private final String SONY = "sony";
    private final String OPPO = "oppo";
    private final String HUAWEI = "huawei";
    private final String HONOR = "honor";
    private final String KNT = "knt";
    private String brand = android.os.Build.BRAND;;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        Alert(brand,"温馨提示");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerprintManger = getSystemService(FingerprintManager.class);
//        mFingerprintManger= FingerprintManagerCompat.from(this);
            if(isHardwareDected()){
               if(isFingerOpen()){

                    cancellationSignal=new CancellationSignal();//取消的对象
                   /**
                    * 这个方法有5个参数：
                    * 第1个参数是指密钥，可以为null；
                    * 第2个参数是flag，一个标记，一般写成0；
                    * 第3个参数是指纹识别取消的对象，用于手动取消指纹识别，不需要手动取消的时候可直接写null；
                    * 第4个参数是指纹识别回调对象，一个类继承FingerprintManager.AuthenticationCallback，下面会有详细解释；
                    * 第5个参数是一个handler对象，默认是在程序主线程的handler中。
                    * CancellationSignal：手动取消识别对象
                    *
                    * 作者：code猿
                    * 链接：https://www.jianshu.com/p/dd8bcc224f24
                    * 来源：简书
                    * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
                    */
                    mFingerprintManger.authenticate(null, cancellationSignal,0, new MyFingerDiscentListener(), null);
                }else{
                       String pcgName = null;
                       String clsName = null;

                       if (compareTextSame(SONY)){
                           pcgName = "com.android.settings";
                           clsName = "com.android.settings.Settings$FingerprintEnrollSuggestionActivity";
                       } else if (compareTextSame(OPPO)) {
                           pcgName = "com.coloros.fingerprint";
                           clsName = "com.coloros.fingerprint.FingerLockActivity";
                       } else if (compareTextSame(HUAWEI)) {
                           pcgName = "com.android.settings";
                           clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity";
                       } else if (compareTextSame(HONOR)) {
                           pcgName = "com.android.settings";
                           clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity";
                       }
                       Intent intent = new Intent();
                       ComponentName componentName = new ComponentName(pcgName, clsName);
                       intent.setAction(Intent.ACTION_VIEW);
                       startActivity(intent);
//                   Alert("手机没有开启指纹识别功能","温馨提示");
               }
            }else{
                Alert("手机版本太低不支持指纹识别","温馨提示");
            }
//            FingerprintManager mFingerprintManager=(FingerprintManager)this.getSystemService(Context.FINGERPRINT_SERVICE);


//FingerprintManagerCompat  mFingerprintManger= FingerprintManagerCompat.from(mActivity);
//            FingerprintManagerCompat  mFingerprintManger= FingerprintManagerCompat.from(this);
        } else {
            Alert("手机版本太低不支持指纹识别","温馨提示");

        }
//        Intent intent = this.getIntent();
//        intent.getStringExtra("username");
//        Bundle bundle = new Bundle();
//        bundle.putString("username","XXXXXX");
//        intent.putExtras(bundle);

    }
    /**
     * 获得当前手机品牌
     * @return 例如：HONOR
     */
    private String getBrand() {
        return this.brand;
    }
    /**
     * 对比两个字符串，并且比较字符串是否包含在其中的，并且忽略大小写
     * @param value
     * @return
     */
    private boolean compareTextSame(String value) {
        return value.toUpperCase().indexOf(getBrand().toUpperCase()) >= 0 ;
    }
    private void Alert(String msg,String title){
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alert.show();
    }

    //手机硬件是否支持指纹
    private Boolean isHardwareDected() {
        try {

            return mFingerprintManger.isHardwareDetected();
        } catch (Exception e) {
            return false;
        }
    }

    //是否录入指纹，有些设备即使录入指纹，但是没有开启锁屏密码的话此方法还是返回false
    private Boolean isFingerOpen() {
        try {
            return mFingerprintManger.hasEnrolledFingerprints();
        } catch (Exception e) {
            return false;
        }
    }

    //是否开始密码锁屏
    private Boolean isOpenCloseScreen() {
        try {
            KeyguardManager mKeyguardManager= (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
            return mKeyguardManager.isKeyguardSecure();
        } catch (Exception e) {
            return false;
        }
    }
    private class MyFingerDiscentListener extends FingerprintManager.AuthenticationCallback {

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            if (errMsgId == 5) {//取消识别

            } else if (errMsgId == 7) {
                Alert("操作过于频繁，请稍后重试", "错误提示");
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    cancellationSignal = null;
                }
            }

        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Alert("指纹识别失败","温馨提示");
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Alert("指纹识别成功","温馨提示");
        }

//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
    }
}
