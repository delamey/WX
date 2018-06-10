package com.example.dell.wx;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Second extends AppCompatActivity {
    public List<String> stringList = new ArrayList<>();

    @BindView(R.id.openBd)
    Button openBd;

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
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        Logger.d("Second");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        secondlistview.setAdapter(adapter);



    }


    @OnClick({R.id.tiaozhuan,R.id.openBd})
    public void Fan(View view) {
        switch (view.getId()) {
            case R.id.tiaozhuan:
            Intent intent = new Intent();
            intent.putExtra("return", "12300");
            setResult(RESULT_OK, intent);
            finish();
            break;
            case R.id.openBd:
                Intent intent1=new Intent(Second.this,webactivity.class);
                startActivity(intent1);
                break;
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
