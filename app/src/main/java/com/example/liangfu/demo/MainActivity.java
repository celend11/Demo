package com.example.liangfu.demo;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.liangfu.demo.Activity.BaiduMapActivity;
import com.example.liangfu.demo.Activity.FingerActivity;
import com.example.liangfu.demo.Activity.GameActivity;
import com.example.liangfu.demo.Activity.GeTuiActivity;
import com.example.liangfu.demo.Activity.OcrActivity;
import com.example.liangfu.demo.Activity.SensorActivity;
import com.example.liangfu.demo.Activity.SpeechActivity;
import com.example.liangfu.demo.Adapter.MyBluetoothAdapter;
import com.example.liangfu.demo.DBHelper.DatabaseHelper;
import com.example.liangfu.demo.widget.ProgressRoundView;
import com.example.liangfu.demo.widget.ProgressView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.net.ssl.HttpsURLConnection;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;
import static java.util.concurrent.locks.ReentrantReadWriteLock.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IWXAPIEventHandler, AdapterView.OnItemClickListener {
    private ProgressView pv;
    private ProgressRoundView pr;
    private ImageView img;
    private ListView listView;
    private int i = 0;
    private Thread th = null;
    private boolean isStop = false;
    private BluetoothReceiver receiver;
    private BluetoothAdapter bluetoothAdapter;
    private List<String> devices;
    private List<BluetoothDevice> deviceList;
//    private Bluetooth client;
    private final String lockName = "BOLUTEK";
    private String message = "000001";
    private IWXAPI api;
    private static final String APP_ID = "wxaec1ab71930622a0";
    private SQLiteDatabase mbase;
    private DatabaseHelper mhelper = new DatabaseHelper(MainActivity.this,"liangfu",null,1);


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("温馨提示");
                    alert.setMessage((msg.obj).toString());
                    alert.show();
                    break;
            }
        }
    };

    @Override
    public void onReq(BaseReq baseReq) {

    }
    @Override
    public void onResp(BaseResp resp) {
        String result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//        finish();
    }
    //处理刷新页面
    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DecimalFormat decimalFormat =new DecimalFormat("0.00");//保留几位i
            String name = decimalFormat.format((i*100/pr.getMaxProgress()))+"%";
            pr.setName(name);
            pr.setCurrentProgress(msg.what);
            pv.setCurrentProgress(msg.what);
//            switch (msg.what) {
//                case Bluetooth.CONNECT_FAILED:
//                    Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_LONG).show();
//                    try {
//                        client.connect(message);
//                    } catch (Exception e) {
//                        Log.e("TAG", e.toString());
//                    }
//                    break;
//                case Bluetooth.CONNECT_SUCCESS:
//                    Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_LONG).show();
//                    break;
//                case Bluetooth.READ_FAILED:
//                    Toast.makeText(MainActivity.this, "读取失败", Toast.LENGTH_LONG).show();
//                    break;
//                case Bluetooth.WRITE_FAILED:
//                    Toast.makeText(MainActivity.this, "写入失败", Toast.LENGTH_LONG).show();
//                    break;
//                case Bluetooth.DATA:
//                    Toast.makeText(MainActivity.this, msg.arg1 + "", Toast.LENGTH_LONG).show();
//                    break;
//            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElment();
        initListener();
        ShowProGressThread();
        ShowMoreImgsInOnImg(4,2,R.drawable.pk,img,100);
        regToWx();
        init();
        mbase = mhelper.getWritableDatabase();
//        EventBus bus = EventBus.getDafualt();
//        long index = mbase.query()

//        ObjectAnimator an = ObjectAnimator.o
    }
    public void init(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BluetoothReceiver();
        registerReceiver(receiver, filter);
//        Thread th = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },"xxx");
//        th.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> maps = new HashMap<String,String>();
                maps.put("PlatformType","IOS");
                maps.put("MKey","com.telsafe.jdjcmcstl");
                maps.put("AppVersion","1.0.0");
                maps.put("WgtVersion","0.0");
                HttpURLConnection conn = null;

                try {
                    conn = Post("http://192.168.90.29:8016/MUpdate/AppCheckUpdate","POST",maps);

                    InputStream in = conn.getInputStream();
                    BufferedReader read = new BufferedReader(new InputStreamReader(in,  "UTF-8"));
                    String valueString = null;
//        valueString.getBytes("UTF-8");
                    StringBuffer bufferRes = new StringBuffer();
                    while ((valueString = read.readLine())!=null){
                        bufferRes.append(valueString);
                    }
                    in.close();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = bufferRes.toString();
                    handler.sendMessage(msg);
//                    Looper.prepare();
//                    Toast.makeText(MainActivity.this,bufferRes.toString(),Toast.LENGTH_LONG).show();
//                    Looper.loop();
//                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//                    alert.setTitle("温馨提示");
//                    alert.setMessage(bufferRes.toString());
//                    alert.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(conn!=null)
                        conn.disconnect();
                }
            }
        }).start();


    }

    private HttpURLConnection Post(String url, String method, Map<String,String> Parms) throws MalformedURLException,IOException{
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
//        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();
        boolean flag = true;
        StringBuffer str = new StringBuffer();
        if(Parms!=null){
            for(Map.Entry<String,String> entry : Parms.entrySet()){
                if(flag){
                    flag = false;
                }else{
                    str.append("&");
                }
                str.append(entry.getKey()+"=");
                if(!TextUtils.isEmpty(entry.getValue())){
                    str.append(entry.getValue());
                }
            }

        }
        OutputStream out = conn.getOutputStream();
        out.write(str.toString().getBytes("UTF-8"));
        out.flush();
        out.close();
//        conn.setRequestProperty();


        return conn;
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class BluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                if (isLock(device)) {
//                    devices.add(device.getName());
//                }else{
//                    if(device.getName()!=null){
//                        devices.add(device.getName());
//                    }
//                }
                if(device!=null){
                    boolean isAdd = true;
                    for(int i = 0;i<deviceList.size();i++){
                        if(deviceList.get(i).getAddress().equals(device.getAddress())){
                            isAdd = false;
                        }
                    }
                    if(isAdd){
                        deviceList.add(device);
                    }
                }
            }
            showDevices();
        }
    }
    private boolean   isLock(BluetoothDevice device) {
        if(device.getName()==null){
            return false;
        }
        boolean isLockName = (device.getName()).equals(lockName);
        boolean isSingleDevice = devices.indexOf(device.getName()) == -1;
        return isLockName && isSingleDevice;
    }

    private void showDevices() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                devices);
        MyBluetoothAdapter adapter = new MyBluetoothAdapter(deviceList,MainActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {

                    if(deviceList.get(i).getBondState() == BluetoothDevice.BOND_NONE){
                        Method createBond = BluetoothDevice.class.getMethod("createBond");
                        createBond.invoke(deviceList.get(i));
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        });
    }

    /**
     * 微信分享
     * @param view
     */
    public void toshare(View view){
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "http://www.baidu.com/";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = "Hi,Tips";
        msg.description = "这是一个校园应用";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("Req");
        req.message = msg;
//        SHARE_TYPE type = Sha;
//        switch (type) {
//            case Type_WXSceneSession:
                req.scene = WXSceneSession;//发送到聊天界面WXSceneSession发送到朋友圈WXSceneTimeline添加到微信收藏WXSceneFavorite
//                break;
//            case Type_WXSceneTimeline:
//                req.scene = WXSceneTimeline;
//                break;
//        }
//        req.userOpenId = APP_ID;
        api.sendReq(req);
//        finish();
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(Bitmap bitmap, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > 32 && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }
    /**
     * 跳转到游戏页面
     * @param view
     */
    public void toGame(View view){
        Intent intent = new Intent(MainActivity.this,GameActivity.class);
//
        startActivity(intent);
        overridePendingTransition(R.anim.entry,R.anim.exit);
    }

    /**
     * 跳转到重力页面
     * @param view
     */
    public void toSensor(View view){
        Intent intent = new Intent(MainActivity.this, SensorActivity.class);
//
        startActivity(intent);
        overridePendingTransition(R.anim.entry,R.anim.exit);
    }
    public void toBaiduSpeech(View view){
        Intent intent = new Intent(MainActivity.this, SpeechActivity.class);
//
        startActivity(intent);
        overridePendingTransition(R.anim.entry,R.anim.exit);
    }
    public void toGetui(View view){
        Intent intent = new Intent(this, GeTuiActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.entry,R.anim.exit);
    }

    public void toOrc(View view){
        Intent intent = new Intent(this, OcrActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.entry,R.anim.exit);
    }

    public void tofinger(View view){
        Intent intent = new Intent(this, FingerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.entry,R.anim.exit);
    }
    //搜索蓝牙
    public void SearchBluetooth(View view){
        deviceList = new ArrayList<BluetoothDevice>();
        devices = new ArrayList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.startDiscovery();
        }else{
            AlertDialog dialog = new AlertDialog.Builder(this)
                                    .setTitle("温馨提示")
                                    .setMessage("是否开启蓝牙")
                                    .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                bluetoothAdapter.enable();
//                                                bluetoothAdapter.startDiscovery();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).create();
            dialog.show();
        }
    }
    //跳转到百度地图
    public void Tobaidu(View view){
//                        .setAction("Action", null).show();
        Intent intent = new Intent(MainActivity.this,BaiduMapActivity.class);
//
        startActivity(intent);
        overridePendingTransition(R.anim.entry,R.anim.exit);
    }
    //数据传输：

    private class connectedThread extends Thread{

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public connectedThread(BluetoothSocket socket){
            InputStream mmIn = null;
            OutputStream mmOut = null;

            mmSocket = socket;

            try {
                mmIn = socket.getInputStream();
                mmOut = socket.getOutputStream();
            }catch (Exception e){
                e.printStackTrace();
            }
//            mState = STATE_CONNECTED;
            mmInStream = mmIn;
            mmOutStream = mmOut;

        }

        public void run(){
            byte[] readBuffer = new  byte[1024];
            int bytes;

            while(mmSocket.isConnected()){
                try {
                    Thread.sleep(20);
                    bytes = mmInStream.read(readBuffer);

//                    mHandlerMessage.obtainMessage(InterfaceCommon.MESSAGE_READ_SOCKET, bytes, -1, readBuffer).sendToTarget();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }///run()

        public void write(byte[] buffer){
            try {
                mmOutStream.write(buffer);
//                mHandlerMessage.obtainMessage(InterfaceCommon.MESSAGE_WRITE_SOCKET, buffer.length, -1, buffer).sendToTarget();


            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void cancel(){
            try {
                mmSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }///connectedThread
    /*
    初始化控件
     */
    public void initElment(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        pv = (ProgressView) findViewById(R.id.progress);
        pr = (ProgressRoundView)findViewById(R.id.progress2);
        img = (ImageView) findViewById(R.id.img);
        listView = (ListView) this.findViewById(R.id.listview);
//        setSupportActionBar(toolbar);
        pv.setName("欢迎来到王者的世界");
    }
    /*
    注册监听
     */
    public void initListener(){
        pr.setOnClickListener(this);
        pv.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /*
            显示进度条
             */
    public void ShowProGressThread(){
        th = new Thread(){
            @Override
            public void run() {
                super.run();
                while (pr.getCurrentProgress() <pr.getMaxProgress()){
                    try {
                        if(isStop){
                            break;
                        }
                        ++i;
                        Message msg = new Message();
                        msg.what = i;
                        handler2.sendMessage(msg);
                        sleep(20);
//                        pv.setCurrentProgress(i);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        th.start();
    }
    /*
      拆解一张图片中的多张图片（局限里面的每一张是等比图片）
     */
    public List<Drawable> ShowMoreImgsInOnImg(int NumX,int NumY,int id,View view,int times){
        AnimationDrawable animationDrawable = new AnimationDrawable();
        Bitmap bmp= BitmapFactory.decodeResource(getResources(), id);
        List<Drawable> imgs = new ArrayList<Drawable>();
        int x = bmp.getWidth()/NumX;
        int y = bmp.getHeight()/NumY;
        for (int j =0;j<NumY;j++){
            for(int i = 0;i<NumX;i++){
                Bitmap b = Bitmap.createBitmap(bmp,i*x,j*y,x,y);
                Drawable drawable = new BitmapDrawable(b);
                animationDrawable.addFrame(drawable,times);
                imgs.add(drawable);
            }
        }
        if(view!=null){
            animationDrawable.setOneShot(false);
            view.setBackground(animationDrawable);
            animationDrawable.start();
        }
        return imgs;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.progress:

                break;
            case R.id.progress2:
                if(th==null){
                    ShowProGressThread();
                }else{
                    if(!th.isAlive()){
                        if(!isStop){
                            i = 0;
                        }
                        isStop = false;
                        pr.setCurrentProgress(i);
                        if(th!=null){
                            th = null;
                        }
                        ShowProGressThread();
                    }else{
                        isStop = !isStop;
                    }
                }
                break;
            default:
                break;
        }
    }
}
