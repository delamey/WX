package com.example.dell.wx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class detail_item_activity extends AppCompatActivity {

    @BindView(R.id.fruit_image_view)
    ImageView fruitImageView;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.fruit_content_text)
    TextView fruitContentText;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra("name");
        int fruitImage = intent.getIntExtra("image",0);
        setSupportActionBar(toolbar2);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsing.setTitle(fruitName);
        Glide.with(this).load(fruitImage).into(fruitImageView);
        String FriutContent=generateFruitContent(fruitName);
        fruitContentText.setText(FriutContent);
        Intent intent1=new Intent(this,AlarmService.class);
        startService(intent1);
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i =0;i<200;i++){
            stringBuilder.append(fruitName);
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
