package com.example.liangfu.demo.Model;

/**
 * Created by liangfu on 2018-11-06.
 */

public class GameCircle {
    private int X;//圆中心x坐标
    private int y;//圆中心y坐标
    private int raduis;//圆半径
    private int life;//圆球血量
    private int speechY;//Y速度
    private int speechX;//X速度
    private int index;//标识

    public GameCircle(int X,int Y,int raduis,int life,int speechX,int speechY,int index) {
        this.X = X;
        this.y = Y;
        this.raduis = raduis;
        this.life = life;
        this.speechX = speechY;
        this.speechY = speechY;
        this.index = index;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getRaduis() {
        return raduis;
    }

    public void setRaduis(int raduis) {
        this.raduis = raduis;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeechY() {
        return speechY;
    }

    public void setSpeechY(int speechY) {
        this.speechY = speechY;
    }

    public int getSpeechX() {
        return speechX;
    }

    public void setSpeechX(int speechX) {
        this.speechX = speechX;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }
}
