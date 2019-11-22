package com.example.liangfu.demo.Model;

/**
 * Created by liangfu on 2018-11-07.
 */

public class Mario {
    private int width;//人物宽度
    private int height;//人物高度
    private int life;//人物生命值
    private int level = 0;//人物级别
    private int jumpHeight = 0;//跳跃高度
    private int currentHeight = 0; //现在跳跃的高度
    private boolean isJunpEnd = false;
    private int speech = 0;//人物移动速度
    private boolean isJump = false;
    private int x = 0;
    private int y = 0;
    private int currentX = 0;//人物当前X点坐标
    private int currentY = 0;//人物当前Y点坐标

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public int getSpeech() {
        return speech;
    }

    public void setSpeech(int speech) {
        this.speech = speech;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(int currentHeight) {
        this.currentHeight = currentHeight;
    }

    public boolean isJunpEnd() {
        return isJunpEnd;
    }

    public void setJunpEnd(boolean junpEnd) {
        isJunpEnd = junpEnd;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
}
