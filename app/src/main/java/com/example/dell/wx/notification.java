package com.example.dell.wx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class notification extends AppCompatActivity {

    @BindView(R.id.takephoto)
    Button takephoto;
    @BindView(R.id.show)
    ImageView show;
    private Uri imageUri;
    private static final int Take_Photo=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.takephoto)
    public void onViewClicked() {
        File out=new File(getExternalCacheDir(),"out.jpg");
        try {
            if (out.exists()) {
                out.delete();
            }
            out.createNewFile();
        }catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(notification.this,"com.example.cameraalbumtest.fileprovider",out);
        }else {
            imageUri=Uri.fromFile(out);
            }
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,Take_Photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Take_Photo:
                if (resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        show.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    break;
        }
    }
}
