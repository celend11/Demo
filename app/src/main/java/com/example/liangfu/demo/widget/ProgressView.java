package com.example.liangfu.demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.liangfu.demo.R;

/**
 * Created by liangfu on 2018-10-29.
 */

public class ProgressView extends View {

    private Paint mPaint; //画笔

    private float MaxProgress = 100;//最大进度

    private float currentProgress;//当前进度值

    private int mHeight,mWidth,mHeight2,mHeight3 = 10,mHeight4 =3;//进度条高度与宽度

    private int textSize = dipToPx(10);
    private String name;
    private Bitmap bip;

    /**
     * 设置为最大项
     **/
    private boolean isMax;
//    private

    public ProgressView(Context context) {
        super(context);
    }
    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mHeight2 = mHeight-mHeight3;
        int round = mHeight2 / 2;
        int round2 = mHeight/2;
        RectF rf = new RectF(0, 5, mWidth, mHeight2);
        mPaint.setColor(Color.rgb(220, 220, 220));
        canvas.drawRoundRect(rf, round, round, mPaint);
        /*设置progress内部是灰色*/
        mPaint.setColor(Color.rgb(242, 241, 246));
        RectF rectBlackBg = new RectF(mHeight4, mHeight3/2+mHeight4, mWidth - mHeight4, mHeight2 - mHeight4);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);
        //设置进度条进度及颜色
        float section = currentProgress / MaxProgress;
        RectF rectProgressBg = new RectF(0, mHeight3/2, mWidth * section, mHeight2);
        if (section != 0.0f) {
            if (isMax){
                mPaint.setColor(Color.rgb(75, 199, 247));
                canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
            }else {
                RectF s1 = new RectF(0, mHeight3/2, mWidth * section, mHeight2);
                mPaint.setColor(Color.rgb(83, 202, 247));
                canvas.drawRoundRect(s1, round, round, mPaint);
            }
        } else {
            mPaint.setColor(Color.TRANSPARENT);
            canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
        }
        //画进度显示的文字
        Paint textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int ty = (int)((fontMetrics.top+fontMetrics.bottom)/2);
        canvas.drawText(name,rectBlackBg.centerX(),rectBlackBg.centerY()-ty,textPaint);
        //画进度标尺图片
//        if(bip!=null){
//        bip = BitmapFactory.decodeResource(getResources(),R.drawable.pk);
//        int x = bip.getWidth()/4;
//        int y = bip.getHeight()/2;
//        bip = Bitmap.createBitmap(bip,((int)currentProgress%4)*x,((int)currentProgress%2)*y,x,y);
//        RectF rectF=new RectF(mWidth * section-2*round2,-mHeight3/2,mWidth * section+round/2,mHeight);
//        canvas.drawBitmap(bip,null,rectF,mPaint);
//        }
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

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = dipToPx(textSize);
    }

    public Bitmap getBip() {
        return bip;
    }

    public void setBip(int id) {
        this.bip= BitmapFactory.decodeResource(getResources(), id);
        invalidate();
    }
}
