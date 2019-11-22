package com.example.liangfu.demo.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.LinearLayout;

public class Panel {
    private final static int HANDLE_WIDTH = 30;
    private final static int MOVE_WIDTH = 20;
    private Button btnHandle;
    private LinearLayout panelContainer;
    private int mRightMargin = 0;
    private Context context;
    private int i = 0;


    public Panel(){

    }

    class AsyMove extends AsyncTask<Integer,Integer,Void>{


        @Override
        protected Void doInBackground(Integer... integers) {

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
