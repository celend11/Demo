package com.example.liangfu.demo.Model;

import android.graphics.Bitmap;

import java.util.List;

public class enemy {
    private int width;//敌人宽度
    private int height;//敌人高度
    private int life;//敌人生命值
    private int level = 0;//敌人级别   1：蘑菇怪 2：乌龟  3：小刺球 4：带刺乌龟 5：食人花 6：云朵
    private int jumpHeight = 0;//跳跃高度
    private int currentHeight = 0; //现在跳跃的高度
    private boolean isJunpEnd = false;
    private int speech = 0;//敌人移动速度
    private boolean isJump = false;
    private int x = 0;
    private int y = 0;
    private int currentX = 0;//敌人当前X点坐标
    private int currentY = 0;//敌人当前Y点坐标
    private List<Bitmap> bits;//敌人图片
    private int moveX = 0;//敌人移动距离
    private int sword = 1; //1表示向右  2表示向左 3表示向上 4表示向下
    private int index=0;
    private boolean  verso = false;

    public enemy(List<Bitmap> bits){
        this.bits = bits;
    }

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

    public List<Bitmap> getBits() {
        return bits;
    }

    public void setBits(List<Bitmap> bits) {
        this.bits = bits;
    }

    public int getMoveX() {
        return moveX;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public int getSword() {
        return sword;
    }

    public void setSword(int sword) {
        this.sword = sword;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVerso() {
        return verso;
    }

    public void setVerso(boolean verso) {
        this.verso = verso;
    }
}
