package com.example.liangfu.demo.Model;

/**
 * Created by liangfu on 2018-11-09.
 */

public class floor {
    private int w;//地面宽度
    private int h;//地面高度
    private int start;//地面起始图片，0为默认图片
    private int index;//默认哪张图片
    private int end;//地面结尾图片
    private int type;// 0 在原有的宽度上，继续加宽 1不加宽
    private int space;//间隙距离

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }
}
