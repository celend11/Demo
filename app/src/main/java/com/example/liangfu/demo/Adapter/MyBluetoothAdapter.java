package com.example.liangfu.demo.Adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liangfu.demo.R;

import java.util.List;

/**
 * Created by liangfu on 2018-12-18.
 */

public class MyBluetoothAdapter extends BaseAdapter{
    private List<BluetoothDevice> devices;
    private Context context;
    public MyBluetoothAdapter(List<BluetoothDevice> devices, Context context){
        this.devices = devices;
        this.context = context;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public BluetoothDevice getItem(int i) {
        return devices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.bluetooth_itemadapter, null);

        }
        TextView t = (TextView) (view.findViewById(R.id.name));
        TextView t2 = (TextView) (view.findViewById(R.id.mac));
        if(devices.get(i).getName()!=null)
        t.setText(devices.get(i).getName());
        if(devices.get(i).getAddress()!=null)
        t2.setText(devices.get(i).getAddress());
        return view;
    }
}