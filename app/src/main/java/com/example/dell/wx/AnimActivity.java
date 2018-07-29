package com.example.dell.wx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimActivity extends AppCompatActivity {

    @BindView(R.id.image_view2)
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.alpha);
        imageView2.setAnimation(animation);
        Log.e("thread",Thread.currentThread().getName());
        try {
            Mythread mythread=new Mythread();
            mythread.start();
            mythread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("thread",Thread.currentThread().getName());

    }
    public class Mythread extends Thread{
        @Override
        public void run() {
            Log.e("thread",Thread.currentThread().getName());
            for (int i = 0; i < 200; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            Log.e("thread",Thread.currentThread().getName());

        }
    }
}
