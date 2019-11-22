package com.example.liangfu.demo.Activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangfu.demo.R;

import java.util.List;

public class SensorActivity extends Activity {


//    private TextView axisX;
//    private TextView axisY;
//    private TextView axisZ;
    private TextView text1,text2,text3;
    private ImageView yuan;
    private String TAG = "sensor";
    private SensorManager sensorManager;
    private SensorEventListener gyroscopeSensorListener;
    private Sensor defaultSensor;
    private float X;
    private float Y;
    private float Z;
    private float X2;
    private float Y2;
    private float Z2;
    private int width,height;
    private int m1 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        init();
        //开始检测

//        starDetection();
        // starRotatio();
    }

    public void init(){
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text2.post(new Runnable(){
            @Override
            public void run(){
                X2 = text2.getX();
                Y2 = text2.getY();
                Z2 = text2.getWidth();
            }
        });
        yuan = (ImageView) findViewById(R.id.image1);
        text3 = (TextView) findViewById(R.id.text3);
    }

    @Override
    protected void onResume() {
        starDetection();
        super.onResume();
    }


    @Override
    protected void onStop() {
        sensorManager.unregisterListener(gyroscopeSensorListener,defaultSensor);

        super.onStop();

    }

    private void starDetection() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        defaultSensor = null;
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_GRAVITY);
            for (int i = 0; i < sensorList.size(); i++) {
                if ((sensorList.get(i).getVendor().contains("Google LLC")) &&
                        (sensorList.get(i).getVersion() == 3)){
                    defaultSensor =  sensorList.get(i);
                }
            }
            Log.i(TAG,"TYPE_GRAVITY");
        }

        if (defaultSensor == null){
            if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }else {
                Log.i(TAG,"can not do task ");
            }
            Log.i(TAG,"TYPE_ACCELEROMETER");
        }
        //判断是否有传感器存在 null 为不存在
        //  defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // float resolution = defaultSensor.getResolution();
        //  Log.i(TAG,"resolution :"+resolution);

        X = 0;
        Y = 0;
        Z = 0;

        gyroscopeSensorListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Log.d(TAG, "onSensorChanged ：X ＝" + sensorEvent.values[0] + ",Y＝ " + sensorEvent.values[1]+",Z= " + sensorEvent.values[2]);
                text1.setText(sensorEvent.values[0] + ",Y＝ " + sensorEvent.values[1]+",Z= " + sensorEvent.values[2]+"X2="+X2+"Y2="+Y2+"Z2="+Z2+"width="+width+"height="+height);
                if (Z != 0){
                    if (Math.abs(sensorEvent.values[0] - X)>1 || Math.abs(sensorEvent.values[1] -Y) >1|| Math.abs(sensorEvent.values[2] -Z)>1){
                        Log.i(TAG,"================================");
                    }
                }else {

                }
                X = sensorEvent.values[0];
                Y = sensorEvent.values[1];
                Z = sensorEvent.values[2];

                if(Y>0.3){
                    text2.setText("向下");
                    if(yuan.getY()+yuan.getHeight()<height){
                        yuan.setY(yuan.getY()+Y*m1);
                    }else{
                        yuan.setY(height-yuan.getHeight());
                        text2.setText("到底了");
                    }
                }else if(Y<-0.3){
                    text2.setText("向上");
                    if(yuan.getY()>0){
                        yuan.setY(yuan.getY()+Y*m1);
                    }else{
                        yuan.setY(0);
                        text2.setText("到顶了");
                    }
                }else{
                    text2.setText("不动");
                }
                if(X>0.3){
                    text3.setText("向左");
                    if(yuan.getX()>0){
                        yuan.setX(yuan.getX()-X*m1);
                    }else{
                        yuan.setX(0);
                        text3.setText("最左边了");
                    }
                }else if(X<-0.3){
                    text3.setText("向右");
                    if(yuan.getX()+yuan.getWidth()<width){
                        yuan.setX(yuan.getX()-X*m1);
                    }else{
                        yuan.setX(width-yuan.getWidth());
                        text3.setText("最右边了");
                    }
                }else{
                    text3.setText("不动");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d(TAG, "sensor: " + sensor + ", accuracy" + accuracy);

            }
        };

        sensorManager.registerListener(gyroscopeSensorListener,
                defaultSensor, SensorManager.SENSOR_DELAY_UI);//增加延时时间

    }
}