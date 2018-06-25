package com.example.dell.wx;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Build.VERSION_CODES.M;

public class Second extends AppCompatActivity {
    public List<String> stringList = new ArrayList<>();
    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
           downloadBinder=(DownloadService.DownloadBinder)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @BindView(R.id.openBd)
    Button openBd;
    @BindView(R.id.jiexiJson)
    Button jiexiJson;
    @BindView(R.id.showJson)
    TextView showJson;
    @BindView(R.id.startdownload)
    Button startdownload;
    @BindView(R.id.pausedownload)
    Button pausedownload;
    @BindView(R.id.canceldownload)
    Button canceldownload;

    private ArrayAdapter<String> adapter;
    @BindView(R.id.zhishi)
    TextView zhishi;
    @BindView(R.id.tiaozhuan)
    Button tiaozhuan;
    @BindView(R.id.notification)
    Button notification;
    @BindView(R.id.secondlistview)
    ListView secondlistview;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String ss = (String) msg.obj;
                stringList.add(ss);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @TargetApi(M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        Logger.d("Second");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        secondlistview.setAdapter(adapter);
        Intent intent=new Intent(this,DownloadService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(Second.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Second.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }


    }


    @OnClick({R.id.tiaozhuan, R.id.openBd, R.id.jiexiJson,R.id.startdownload,R.id.pausedownload,R.id.canceldownload})
    public void Fan(View view) {
        switch (view.getId()) {
            case R.id.tiaozhuan:
                EventBus.getDefault().post(new MyEvent("EventBus 传递"));
                Intent intent = new Intent();
                intent.putExtra("return", "12300");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.openBd:
                Intent intent1 = new Intent(Second.this, webactivity.class);
                startActivity(intent1);
                break;
            case R.id.jiexiJson:
                File file = new File(Environment.getExternalStorageDirectory(), "data.json");
                try {
                    String chs;
                    char[] c = new char[1024];
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    while ((reader.read(c)) != -1) {
                        chs = new String(c);
                        parseJson(chs);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.startdownload:
                String url="https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                downloadBinder.startDownload(url);
                break;
            case R.id.pausedownload:
                downloadBinder.pauseDownload();
                break;
            case R.id.canceldownload:
                downloadBinder.cancelDownload();
                break;
                default:
                    break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    finish();
                }
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    private void parseJson(String shoudao) {
        Gson gson = new Gson();
        List<person> personlist = gson.fromJson(shoudao, new TypeToken<List<person>>() {
        }.getType());//对于不是类的情况，用这个参数给出)
        for (person p : personlist) {
            showJson.append(p.getId());
            showJson.append(p.getName());
            showJson.append(p.getVersion());
        }
    }

    @OnClick(R.id.notification)
    public void onViewClicked() {
        Intent intent = new Intent(this, notification.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is notification")
                .setContentText("新消息")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("sssdvdvdvawqqdzxcveeeeeeeeeeeeeeeeeeeeeeeffffffffffsxcldfkborrrffwjsdmvvzxceeeeeeeeeffffffffffffffskkkkkkkkkkkkkkkkkkkkdddddddwqq"))
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.n6)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.n6))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.timg)))
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setLights(Color.WHITE, 1000, 1000)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        manager.notify(1, notification);

    }


}
