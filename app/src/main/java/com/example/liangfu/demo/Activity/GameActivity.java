package com.example.liangfu.demo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.liangfu.demo.R;
import com.example.liangfu.demo.widget.GameSurfaceView;

/**
 * Created by liangfu on 2018-11-05.
 */

public class GameActivity extends Activity implements View.OnClickListener{
    private Button clear;
    private ImageView left,right,down,jump;
    private GameSurfaceView gv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initElement();
        initlistener();
    }

    public void initlistener(){
        clear.setOnClickListener(this);
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        gv.setSword(2);
                        gv.setMove(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        gv.setSword(2);
                        gv.setMove(false);
                        break;
                }
                return true;
            }
        });
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        gv.setSword(1);
                        gv.setMove(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        gv.setSword(1);
                        gv.setMove(false);
                        break;
                }
                return true;
            }
        });

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
//                        if(!gv.isJump()){
                            gv.setDown(true);
//                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        gv.setDown(false);
                        break;

                    default:


                }
                return true;
            }
        });
        jump.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(!gv.isJump()){
                            gv.setJump(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        break;


                }

                return true;
            }
        });

    }

    public void initElement(){
        clear = (Button) findViewById(R.id.clear);
        left = (ImageView) findViewById(R.id.left);
        right = (ImageView) findViewById(R.id.right);
        down = (ImageView) findViewById(R.id.down);
        jump = (ImageView) findViewById(R.id.jump);
        gv = (GameSurfaceView) findViewById(R.id.surface);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear:
                gv.setClear(true);
                break;
            case R.id.left:
                gv.setMoveX(gv.getMoveX() - 3);
                break;
            case R.id.right:
                gv.setMoveX(gv.getMoveX() + 3);
                break;
            case R.id.down:

                break;
            case R.id.jump:

                break;

             default:
        }
    }
}
