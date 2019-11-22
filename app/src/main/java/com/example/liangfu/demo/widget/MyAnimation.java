package com.example.liangfu.demo.widget;

import android.graphics.Camera;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyAnimation extends Animation {
    private float x;
    private float y;
    private int duration;
    private boolean fillafter;
    private Camera camera = new Camera();

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width,height,parentWidth,parentHeight);
        //设置动画的持续时间
        setDuration(duration);
        //设置动画结束后保留效果
        setFillAfter(fillafter);
    }
    public MyAnimation(float x,float y,int duration,boolean fillafter){
            this.x = x;
            this.y = y;
            this.duration = duration;
            this.fillafter = fillafter;
    }
    @Override
    public void setDuration(long durationMillis) {
        super.setDuration(durationMillis);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        camera.save();
        camera.translate(100.0f - 100.0f*interpolatedTime,150.0f*interpolatedTime -150.0f,80.0f - 80.0f*interpolatedTime);
        camera.rotateX(360*interpolatedTime);
        camera.rotateY(360*interpolatedTime);
        camera.rotateZ(360*interpolatedTime);


        camera.restore();
    }
}
