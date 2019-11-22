package com.example.liangfu.demo.widget;

import android.content.ContentProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.liangfu.demo.Model.GameCircle;
import com.example.liangfu.demo.Model.Mario;
import com.example.liangfu.demo.Model.enemy;
import com.example.liangfu.demo.Model.floor;
import com.example.liangfu.demo.Model.floors;
import com.example.liangfu.demo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangfu on 2018-11-05.
 */

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder mholder;
    private boolean isDraw = false;
    private Canvas mCanvas = null;
    private Paint mPaint = null;
//    private Path mPath = null;
    private long FRAME_TIME =100;
    private int mHeight = 0;
    private int mWidth = 0;
    private int mWidth2 = 0;
    private boolean isClear = false;
    private List<GameCircle> GameCircles = null;
    private List<Paint> Paints = null;
    private int mSpeech = 4;
    private int moveX = 0;
    private boolean isDestroy = false;
    private Matrix matrix;
    private Matrix matrix2;
    private List<Bitmap> bips;
    private Mario mario;
    private int moveindex = 0;
    private int enemyindex = 0;
    private boolean isMove = false;
    private boolean isDown = false;
    private boolean isJump = false;
    private int sword = 1;//1代表向右，2向左
    private Thread drawTh;
    private int floorW = 0;
    private int floorH = 0;
    private int currentlength = 0;
    private List<floors> floors;
    private List<enemy> enemys = new ArrayList<enemy>();
    private boolean isdead = false;
    public GameSurfaceView(Context context) {
        super(context);
        initView();
        initData();
        isClear = false;
    }
    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initData();
        isClear = false;
    }
    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initData();
        isClear = false;
    }

    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        initView();
        initData();
        isClear = false;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public int getMoveX() {
        return moveX;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public boolean getMove() {
        return isMove;
    }

    public void initView(){
        mholder = getHolder();//设置surfaceholder对象
        mholder.addCallback(this);//注册surfaceholder的回调
    }

    public void initImage(){
        int[] ints =new int[]{R.drawable.mario1,R.drawable.mario2,R.drawable.mario3,R.drawable.mario4,
                                R.drawable.mario5,R.drawable.mario6,R.drawable.mario7,
                                R.drawable.mario8,R.drawable.mario9,R.drawable.mario10,R.drawable.mario11,
                                R.drawable.mario12,R.drawable.mario13,R.drawable.mario14,
        };
        int[] ints2 = new int[]{R.drawable.tile1,R.drawable.tile2,R.drawable.tile3,R.drawable.tile4,R.drawable.tile5,R.drawable.tile6,R.drawable.tile7,R.drawable.tile8,
                R.drawable.tile9,R.drawable.tile10,R.drawable.tile11,R.drawable.tile12,R.drawable.tile13,R.drawable.tile14,R.drawable.tile15,R.drawable.tile16,
                R.drawable.tile17,R.drawable.tile18,R.drawable.tile19,R.drawable.tile20,R.drawable.tile21,R.drawable.tile22,R.drawable.tile23,R.drawable.tile24,
                R.drawable.tile25,R.drawable.tile26,R.drawable.tile27,R.drawable.tile28,R.drawable.tile29,R.drawable.tile30,R.drawable.tile31,R.drawable.tile32,
                R.drawable.tile33,R.drawable.tile34,R.drawable.tile35};
        matrix.postScale(1.5f, 1.5f);//放大图片
        matrix2.setScale(-1,1);  //图片翻转
        for(int i = 0;i<ints.length;i++){
            Bitmap p = BitmapFactory.decodeResource(getResources(), ints[i]);
            Bitmap newBM = Bitmap.createBitmap(p, 0, 0, p.getWidth(), p.getHeight(), matrix, false);
            if(mario.getWidth()<=0){
                mario.setWidth(newBM.getWidth());
            }
            if(mario.getHeight()<=0){
                mario.setHeight(newBM.getHeight());
            }
            bips.add(newBM);
//            matrix.postTranslate(newBM.getWidth()*2,0);
            Bitmap btt2 = Bitmap.createBitmap(newBM, 0,0,newBM.getWidth(), newBM.getHeight(), matrix2, true);
            bips.add(btt2);
//            Marios.add(m);
        }
        for(int j = 0;j<ints2.length;j++){
            Bitmap ps = BitmapFactory.decodeResource(getResources(), ints2[j]);
            Bitmap newBM2 = Bitmap.createBitmap(ps, 0, 0, ps.getWidth(), ps.getHeight(), matrix, false);
            if(floorH<=0)
            floorH = newBM2.getHeight();
            if(floorW<=0)
            floorW = newBM2.getWidth();
            bips.add(newBM2);
        }
        initEnemy();
    }
    //初始化敌人数据
    public void initEnemy(){
        int[] ints3 = new int[]{R.drawable.enemy1,R.drawable.enemy2,R.drawable.enemy3,R.drawable.enemy4,
                R.drawable.enemy5,R.drawable.enemy6,R.drawable.enemy7,R.drawable.enemy8,
                R.drawable.enemy9,R.drawable.enemy10,R.drawable.enemy11,R.drawable.enemy12,
                R.drawable.enemy13,R.drawable.enemy14,};
        for(int k = 0;k<6;k++){
            enemys.add(new enemy(new ArrayList<Bitmap>()));
        }
        for (int z = 0;z<ints3.length;z++){
            Bitmap ps = BitmapFactory.decodeResource(getResources(),ints3[z]);
            Bitmap newBM2 = Bitmap.createBitmap(ps, 0, 0, ps.getWidth(), ps.getHeight(), matrix, false);
            if(z<3){  //蘑菇怪
                enemys.get(0).getBits().add(newBM2);
                enemys.get(0).setHeight(newBM2.getHeight());
                enemys.get(0).setWidth(newBM2.getWidth());
                enemys.get(0).setLevel(1);
                enemys.get(0).setLife(1);
                enemys.get(0).setSpeech(2);
            }else if(z>=3&&z<5){  //背带刺怪
                Bitmap btt2 = Bitmap.createBitmap(newBM2, 0,0,newBM2.getWidth(), newBM2.getHeight(), matrix2, true);
                enemys.get(1).getBits().add(newBM2);
                enemys.get(1).getBits().add(btt2);
                enemys.get(1).setHeight(newBM2.getHeight());
                enemys.get(1).setWidth(newBM2.getWidth());
                enemys.get(1).setLevel(4);
                enemys.get(1).setLife(20000);
                enemys.get(1).setSpeech(2);
                enemys.get(1).setVerso(true);
            }else if(z>=5&&z<7){ //小刺球
                enemys.get(2).getBits().add(newBM2);
                enemys.get(2).setHeight(newBM2.getHeight());
                enemys.get(2).setWidth(newBM2.getWidth());
                enemys.get(2).setLevel(3);
                enemys.get(2).setLife(1);
                enemys.get(2).setSpeech(2);
            }else if(z>=7&&z<10){ //乌龟
                Bitmap btt2 = Bitmap.createBitmap(newBM2, 0,0,newBM2.getWidth(), newBM2.getHeight(), matrix2, true);
                enemys.get(3).getBits().add(newBM2);
                enemys.get(3).getBits().add(btt2);
                enemys.get(3).setHeight(newBM2.getHeight());
                enemys.get(3).setWidth(newBM2.getWidth());
                enemys.get(3).setLevel(2);
                enemys.get(3).setLife(1);
                enemys.get(3).setSpeech(2);
                enemys.get(3).setVerso(true);
            }else if(z>=10&&z<12){  //食人花
                enemys.get(4).getBits().add(newBM2);
                enemys.get(4).setHeight(newBM2.getHeight());
                enemys.get(4).setWidth(newBM2.getWidth());
                enemys.get(4).setLevel(5);
                enemys.get(4).setLife(3);
//                enemys.get(0).setSpeech(1);
            }else{   //云朵
                enemys.get(5).getBits().add(newBM2);
                enemys.get(5).setHeight(newBM2.getHeight());
                enemys.get(5).setWidth(newBM2.getWidth());
                enemys.get(5).setLevel(6);
                enemys.get(5).setLife(1);
                enemys.get(5).setSpeech(2);
            }
        }
    }

    public void initData(){
//        mPath = new Path();
        GameCircles = new ArrayList<GameCircle>();
        Paints = new ArrayList<Paint>();
        matrix = new Matrix();
        matrix2 = new Matrix();
        bips = new ArrayList<Bitmap>();
        mario = new Mario();
        mario.setLevel(0);
        mario.setLife(3);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);
        initImage();
        mario.setJumpHeight(floorH*4);
//        DisplayMetrics dm = new DisplayMetrics();
//        mHeight = dm.heightPixels;
//        mWidth = dm.widthPixels;
    }

    public void initfloors() {
        floors = new ArrayList<floors>();

        //第一关地面绘制
        floors fs = new floors(0);
        for(int i = 0;i<10;i++){
            int j = i%3+2;
            floor f = new floor();
            f.setW(floorW*8);
            f.setH(floorH*(j-2));
            f.setStart(0);
            f.setIndex(28);
            f.setType(0);
//            if(j == 3){
//                f.setSpace(0);
//            }else{
                f.setSpace(0);
//            }
            fs.getFloors().add(f);
            fs.setLevel(0);
        }
        floors.add(fs);

        //第二关地面绘制

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isDraw = true;
        isDestroy = false;
        if(mHeight<=0)
            mHeight = this.getHeight();
//        if(mWidth<=0)

        if(mWidth2<=0){
            mWidth2 = this.getWidth();
            initfloors();
            for(int i = 0;i<floors.get(0).getFloors().size();i++){
                mWidth = 80*floorW;
            }

        }
        drawTh = new Thread(this);
        drawTh.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isDraw = false;
        isDestroy = true;
    }

    /*
    拆解一张图片中的多张图片（局限里面的每一张是等比图片）
    */
    public List<Bitmap> ShowMoreImgsInOnImg(int NumX, int NumY, int id){
        AnimationDrawable animationDrawable = new AnimationDrawable();
        Bitmap bmp= BitmapFactory.decodeResource(getResources(), id);
        List<Bitmap> imgs = new ArrayList<Bitmap>();
        int x = bmp.getWidth()/NumX;
        int y = bmp.getHeight()/NumY;
        for (int j =0;j<NumY;j++){
            for(int i = 0;i<NumX;i++){
                Bitmap b = Bitmap.createBitmap(bmp,i*x,j*y,x,y);
                imgs.add(b);
            }
        }
        return imgs;
    }
    @Override
    public void run() {
        while (isDraw){
            if(isdead){
                return;
            }
            long startTime = System.currentTimeMillis();
            draw();
            long endTime = System.currentTimeMillis();
            long diffTimr = endTime - startTime;
            if(diffTimr<FRAME_TIME){
                try {
                    Thread.sleep(diffTimr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public void setClear(boolean clear) {
        isClear = clear;
//        mPath = new Path();
    }

    public boolean isClear() {
        return isClear;
    }

    /**
     * 判断是否游戏终止
     * @return
     */
    public boolean isDead(){
        if(mario == null || enemys == null){
            return false;
        }
        for (int i = 0;i<enemys.size();i++){
            if(enemys.get(i).getX() <=mario.getX()+mario.getWidth()&&mario.getX() <= enemys.get(i).getX()+enemys.get(i).getWidth()&&enemys.get(i).getY()>=mario.getCurrentY()- mario.getCurrentHeight()-mario.getHeight()&&enemys.get(i).getY()-enemys.get(i).getHeight()<=mario.getCurrentY()- mario.getCurrentHeight()){
                if(mario.getLife()>1){
                    mario.setLife(mario.getLife() - 1);
                    return false;
                }else{
                    mario.setLife(0);
                    return true;
                }
            }
        }

        return false;
    }

//    public boolean isAttact(GameCircle g){
//        boolean flag = false;
//        for(int i = 0;i<GameCircles.size();i++){
//            if(GameCircles.get(i).getIndex() == g.getIndex()){
//                continue;
//            }else{
//                if(Math.abs(g.getX()-GameCircles.get(i).getX())<=g.getRaduis()+GameCircles.get(i).getRaduis()&&Math.abs(g.getY()-GameCircles.get(i).getY())<=g.getRaduis()+GameCircles.get(i).getRaduis()){
//                    if(g.getX()>GameCircles.get(i).getX()){
//                        g.setSpeechX(speech);
//                        GameCircles.get(i).setSpeechX(-speech);
//                    }else{
//                        g.setSpeechX(-speech);
//                        GameCircles.get(i).setSpeechX(speech);
//                    }
//                    if(g.getY()>GameCircles.get(i).getY()){
//                        g.setSpeechY(3);
//                        GameCircles.get(i).setSpeechY(-speech);
//                    }else{
//                        g.setSpeechY(-3);
//                        GameCircles.get(i).setSpeechY(speech);
//                    }
//                    flag = true;
//                }
//            }
//        }
//        if(g.getX()-g.getRaduis()<=0||g.getX()+g.getRaduis()>=mWidth){
//            g.setSpeechX(-g.getSpeechX());
//            flag = false;
//        }
//        if(g.getY()-g.getRaduis()<=0||g.getY()+g.getRaduis()>=mHeight){
//            g.setSpeechY(-g.getSpeechY());
//            flag = false;
//        }
//        return flag;
//    }
    //绘制地面
    public void drawfloor(int w,int h,int start,int index,int end,int type,int space){
            int len = w/floorW;
            for (int i = 0;i<len;i++){
                if(start !=0&&i==0){
                    mCanvas.drawBitmap(bips.get(start),currentlength+i*floorW+moveX+space,mHeight - floorH - h,mPaint);
                }else{
                    if(end!=0&&i==len-1){
                        mCanvas.drawBitmap(bips.get(end),currentlength+i*floorW+moveX+space,mHeight - floorH - h,mPaint);
                    }else{
                        mCanvas.drawBitmap(bips.get(index),currentlength+i*floorW+moveX+space,mHeight - floorH - h,mPaint);
                    }
                }
            }
            if(type == 0)
            currentlength = currentlength+len*floorW+space;
    }

    //绘制敌人
    public void drawenemy(int Height,int Witdh,int index,int len){
        if(index>5){
            return;
        }
        if(enemys.get(index).getLife()>0){
            int enemyX = enemys.get(index).getMoveX();
            if(len*floorW<=enemyX){
                enemys.get(index).setSword(2);
            }
            if(enemyX<=0){
                enemys.get(index).setSword(1);
            }
            enemyX = currentlength+Witdh+moveX+enemys.get(index).getMoveX();
            int moveindex2 = enemyindex/5;
            int index3 = moveindex2%2;
            int index1 = (enemys.get(index).getIndex()+index3)%2;
            int enemyY = mHeight - floorH - Height-enemys.get(index).getHeight();
            if(enemys.get(index).getSword() == 1){
                enemys.get(index).setMoveX(enemys.get(index).getMoveX()+enemys.get(index).getSpeech());
                if(enemys.get(index).isVerso()){
                    mCanvas.drawBitmap(enemys.get(index).getBits().get(enemys.get(index).getIndex()*2),enemyX,enemyY,mPaint);
                }else{
                    mCanvas.drawBitmap(enemys.get(index).getBits().get(enemys.get(index).getIndex()),enemyX,enemyY,mPaint);
                }
            }else{
                enemys.get(index).setMoveX(enemys.get(index).getMoveX()-enemys.get(index).getSpeech());
                if(enemys.get(index).isVerso()){
                    mCanvas.drawBitmap(enemys.get(index).getBits().get(enemys.get(index).getIndex()*2+1),enemyX,enemyY,mPaint);
                }else{
                    mCanvas.drawBitmap(enemys.get(index).getBits().get(enemys.get(index).getIndex()),enemyX,enemyY,mPaint);
                }
            }
            enemys.get(index).setX(enemyX);
            enemys.get(index).setY(enemyY);
            enemys.get(index).setIndex(index1);
        }
    }


    //绘制自己
    public void drawself(Canvas canvas){
        moveindex++;
        int h = mHeight-mario.getY()-mario.getHeight()-floorH;
        mario.setCurrentY(h);
        if(isMove){
            int moveindex2 = moveindex/5;
            int i = moveindex2%2;
            if(sword == 1){
                moveToRight();
                if(isJump){
                    Jump();
                    canvas.drawBitmap(bips.get(mario.getLevel()+4),mario.getX(),mario.getCurrentY()- mario.getCurrentHeight(),mPaint);
                }else{
                    if(isDown){
                        canvas.drawBitmap(bips.get(mario.getLevel()+6),mario.getX(),mario.getCurrentY(),mPaint);
                    }else{
                        canvas.drawBitmap(bips.get(mario.getLevel()+i*2),mario.getX(),mario.getCurrentY(),mPaint);
                    }
                }

            }else{
                moveToLeft();
                if(isJump){
                    Jump();
                    canvas.drawBitmap(bips.get(mario.getLevel()+5),mario.getX(),mario.getCurrentY() -mario.getCurrentHeight(),mPaint);
                }else{
                    if(isDown){
                        canvas.drawBitmap(bips.get(mario.getLevel()+7),mario.getX(),mario.getCurrentY(),mPaint);
                    }else{
                        canvas.drawBitmap(bips.get(mario.getLevel()+i*2+1),mario.getX(),mario.getCurrentY(),mPaint);
                    }
                }
            }
        }else{
            moveindex = 0;
            if(sword == 1){
                if(isJump){
                    Jump();
                    canvas.drawBitmap(bips.get(mario.getLevel()+4),mario.getX(),mario.getCurrentY() -mario.getCurrentHeight(),mPaint);
                }else{
                    if(isDown){
                        canvas.drawBitmap(bips.get(mario.getLevel()+6),mario.getX(),mario.getCurrentY(),mPaint);
                    }else{
                        canvas.drawBitmap(bips.get(mario.getLevel()),mario.getX(),mario.getCurrentY(),mPaint);
                    }
                }
            }else{
                if(isJump){
                    Jump();
                    canvas.drawBitmap(bips.get(mario.getLevel()+5),mario.getX(),mario.getCurrentY() -mario.getCurrentHeight(),mPaint);
                }else{
                    if(isDown){
                        canvas.drawBitmap(bips.get(mario.getLevel()+7),mario.getX(),mario.getCurrentY(),mPaint);
                    }else{
                        canvas.drawBitmap(bips.get(mario.getLevel()+1),mario.getX(),mario.getCurrentY(),mPaint);
                    }
                }
            }
        }
    }

    //向右行走
    public void moveToRight(){
        int w = Math.abs(moveX)+mario.getX()+mario.getWidth();
        int i = w/(8*floorW);
        if(mario.getY()+mario.getCurrentHeight()<floors.get(0).getFloors().get(i).getH()){
            moveX = moveX;
        }else{
//            if(mario.isJunpEnd()&&mario.getCurrentHeight()>0){
//                int h = mario.getY()-floors.get(0).getFloors().get(i).getH();
//                if(mario.getCurrentHeight()+h<=0){
//                    mario.setCurrentHeight(0);
//                    mario.setY(mario.getY()-h);
//                }else{
//                    mario.setCurrentHeight(mario.getCurrentHeight()+h);
//                    mario.setY(mario.getY()-h);
//                }
//            }
            if(!isJump){
                int h = floors.get(0).getFloors().get(i).getH();
                if(mario.getY()>h){
                    if(w -(8*floorW)*i >(3*mario.getWidth())/4){
                        mario.setCurrentHeight(mario.getY() - h);
                        mario.setJunpEnd(true);
                        isJump = true;
                        mario.setY(h);
//                        mario.setY(floors.get(0).getFloors().get(i).getH());
//                        int x = (8*floorW)*i- Math.abs(moveX);
//                        mario.setX(x);
                    }
                }else{
                    mario.setY(floors.get(0).getFloors().get(i).getH());
                }
//                mario.setY(floors.get(0).getFloors().get(i).getH());
            }
            if(moveX >=0&&mario.getX()<=mWidth2/2){
                mario.setX(mario.getX()+mSpeech);
            }else if(moveX<=-mWidth+mWidth2){
                if(moveX<=-mWidth+mWidth2){
                    moveX = -mWidth+mWidth2;
                }
                if(mario.getX()+mSpeech>=mWidth2-mario.getWidth()){
                    mario.setX(mWidth2-mario.getWidth());
                }else{
                    mario.setX(mario.getX()+mSpeech);
                }
            }else{
                moveX = moveX - mSpeech;
            }
        }
    }

    //向左行走
    public void moveToLeft(){
        int i = 0;
        int w = Math.abs(moveX)+mario.getX()+mario.getWidth();
        int i11 = w/(8*floorW);
        int w22 = Math.abs(moveX)+mario.getX();
        int i22 = w22/(8*floorW);
        if(floors.get(0).getFloors().get(i).getH()<floors.get(0).getFloors().get(i22).getH()){
            i = i22;
        }else{
            i = i11;
        }
        boolean isAdd = true;
        if(mario.getY()+mario.getCurrentHeight()<floors.get(0).getFloors().get(i).getH()){
            moveX = moveX;
        }else{
            if(!isJump){
                int h = floors.get(0).getFloors().get(i11).getH();
                int w2 = Math.abs(moveX)+mario.getX()+mario.getWidth()-mSpeech;
                int j = w2/(8*floorW);
                int h2 = floors.get(0).getFloors().get(j).getH();
                if(mario.getY()<h2){
                    if(w2 - (8*floorW)*j -1<0){
//                        mario.setY(h);
//                        mario.setY(floors.get(0).getFloors().get(i).getH());
                        moveX = moveX;
                        isAdd = false;
//                        mario.setX(mario.getX()+mario.getWidth()/2);
                    }
                }else if(mario.getY()>h2){
                    mario.setCurrentHeight(mario.getY() - h);
                    mario.setJunpEnd(true);
                    isJump = true;
                    mario.setY(h2);
//                    mario.setY(floors.get(0).getFloors().get(i).getH());
                }
            }
            if(moveX>=0){
                moveX = 0;
                if(mario.getX()-mSpeech<=0){
                    mario.setX(0);
                }else{
                    mario.setX(mario.getX()-mSpeech);
                }
                }else if(mario.getX()<=mWidth2-mario.getWidth()&&mario.getX()>=mWidth2/2){
                mario.setX(mario.getX()-mSpeech);
            }else{
                if(isAdd)
                moveX = moveX +mSpeech;
            }
        }
    }

    //跳跃判断
    public void Jump(){
        int w = 0;
        if(sword == 1){
            w = Math.abs(moveX)+mario.getX()+mario.getWidth();
        }else{
            w = Math.abs(moveX)+mario.getX();
        }
        int i = w/(8*floorW);
        int h = floors.get(0).getFloors().get(i).getH() - mario.getY();

        if(mario.getCurrentHeight()<=mario.getJumpHeight()&&!mario.isJunpEnd()){
            mario.setCurrentHeight(mario.getCurrentHeight()+mario.getJumpHeight()/20);
            if(mario.getCurrentHeight()>=mario.getJumpHeight()){
                mario.setCurrentHeight(mario.getJumpHeight());
                mario.setJunpEnd(true);
            }
        }
        if(mario.getCurrentHeight()-h>=0&&mario.isJunpEnd()){
            mario.setCurrentHeight(mario.getCurrentHeight() - mario.getJumpHeight()/20);
            if(mario.getCurrentHeight()-h<=0){
                mario.setCurrentHeight(0);
                mario.setY(floors.get(0).getFloors().get(i).getH());
                mario.setCurrentY(mHeight-mario.getY()-mario.getHeight()-floorH);
                mario.setJunpEnd(false);
                isJump = false;
            }
        }
    }



    //绘制游戏页面
    public void draw() {
        try {
            mCanvas = mholder.lockCanvas();
            if(mCanvas!=null){
                mCanvas.drawColor(Color.BLUE);
//                mCanvas.drawPath(mPath,mPaint);
                for(int i = 0;i<floors.get(0).getFloors().size();i++){
                    drawenemy(floors.get(0).getFloors().get(i).getH(),(i%2+2)*floorW,i,7 - (i%2+2));
                    drawfloor(floors.get(0).getFloors().get(i).getW(),floors.get(0).getFloors().get(i).getH(),floors.get(0).getFloors().get(i).getStart(),floors.get(0).getFloors().get(i).getIndex(),floors.get(0).getFloors().get(i).getEnd(),floors.get(0).getFloors().get(i).getType(),floors.get(0).getFloors().get(i).getSpace());
                }
                enemyindex++;
//                drawfloor(mWidth2,0,0,28,0,0,0);
//                drawfloor(mWidth2,0,0,29,0,1,0);
//                drawfloor(mWidth2,floorH,49,29,0,1,0);
//                drawfloor(mWidth2,2*floorH,50,28,0,0,0);
                if(isDead()){
                    mCanvas.drawBitmap(bips.get(25),mario.getX(),mario.getCurrentY()-120,mPaint);
                    isdead = true;
                }else{
                    drawself(mCanvas);
                }

                currentlength = 0;
//                if(isClear){
//                    mCanvas.drawColor(Color.WHITE);
//                    isClear = !isClear;
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mCanvas!=null&&!isDestroy)
            mholder.unlockCanvasAndPost(mCanvas);//保证每次绘制的内容都能提交
        }
    }

    public int getSword() {
        return sword;
    }

    public void setSword(int sword) {
        this.sword = sword;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public int getFloorH() {
        return floorH;
    }

    public void setFloorH(int floorH) {
        this.floorH = floorH;
    }

    public int getFloorW() {
        return floorW;
    }

    public void setFloorW(int floorW) {
        this.floorW = floorW;
    }
}
