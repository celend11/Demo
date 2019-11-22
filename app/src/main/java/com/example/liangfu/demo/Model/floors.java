package com.example.liangfu.demo.Model;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangfu on 2018-11-09.
 */

public class floors {
    private int level = 0;

    private List<floor> fs;

    public floors(int level){
        if(fs == null){
            fs = new ArrayList<floor>();
        }
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<floor> getFloors() {
        return fs;
    }

    public void setFloors(List<floor> fs) {
        this.fs = fs;
    }
}
