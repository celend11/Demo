package com.example.liangfu.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liangfu on 2018-10-29.
 */

public class ProgressRoundView extends View {

    private Paint mPaint; //画笔

    private float MaxProgress = 360;//最大进度

    private float currentProgress;//当前进度值

    private int mHeight,mWidth;//进度条高度与宽度

    private String name ="暂无";

    private float point = -90;

    private int rWidth = 10;

    private int textSize = 15;
    /**
     * 设置为最大项
     **/
    private boolean isMax;
//    private

    public ProgressRoundView(Context context) {
        super(context);
    }
    public ProgressRoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ProgressRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.rgb(220, 220, 220));
        mPaint.setStrokeWidth(rWidth);
        canvas.drawCircle(mWidth/2,mHeight/2,mHeight/2 -rWidth/2,mPaint);
        mPaint.setColor(Color.RED);
        RectF oval = new RectF( mWidth/2 -mHeight/2 +rWidth/2, rWidth/2,
                mWidth/2+mHeight/2 -rWidth/2, mHeight-rWidth/2);
        canvas.drawArc(oval,point,currentProgress,false,mPaint);
        Paint textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int ty = (int)((fontMetrics.top+fontMetrics.bottom)/2);
        canvas.drawText(name,oval.centerX(),oval.centerY()-ty,textPaint);
//        mPaint.setStrokeWidth(10);
//        canvas.drawCircle(mWidth/2,mHeight/2,mHeight/2 -15,mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //MeasureSpec.EXACTLY，精确尺寸
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        //MeasureSpec.AT_MOST，最大尺寸，只要不超过父控件允许的最大尺寸即可，MeasureSpec.UNSPECIFIED未指定尺寸
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(20);
        } else {
            mHeight = heightSpecSize;
        }
        //设置控件实际大小
        setMeasuredDimension(mWidth, mHeight);


    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }


    public float getMaxProgress() {
        return MaxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        MaxProgress = maxProgress;
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(float currentProgress) {

        this.currentProgress = currentProgress> MaxProgress?MaxProgress:currentProgress;
        invalidate();
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setMax(boolean max) {
        isMax = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        invalidate();
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public int getrWidth() {
        return rWidth;
    }

    public void setrWidth(int rWidth) {
        this.rWidth = rWidth;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
