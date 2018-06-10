package com.example.dell.wx;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class notification extends AppCompatActivity {

    @BindView(R.id.takephoto)
    Button takephoto;
    @BindView(R.id.show)
    ImageView show;
    @BindView(R.id.choosephoto)
    Button choosephoto;
    @BindView(R.id.Linear1)
    LinearLayout Linear1;
    @BindView(R.id.mediaplay)
    Button mediaplay;
    @BindView(R.id.videoplay)
    Button videoplay;
    @BindView(R.id.videoview)
    VideoView videoview;
    private Uri imageUri;
    private static final int Take_Photo = 1;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(notification.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(notification.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        } else {
            initvideoPath();
        }

    }

    @OnClick({R.id.takephoto, R.id.choosephoto, R.id.mediaplay, R.id.videoplay})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.takephoto:
                File out = new File(getExternalCacheDir(), "out.jpg");
                try {
                    if (out.exists()) {
                        out.delete();
                    }
                    out.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(notification.this, "com.example.cameraalbumtest.fileprovider", out);
                } else {
                    imageUri = Uri.fromFile(out);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, Take_Photo);
                break;
            case R.id.choosephoto:
                if (ContextCompat.checkSelfPermission(notification.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(notification.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.mediaplay:
                if (ContextCompat.checkSelfPermission(notification.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(notification.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                } else {
                    initmediaplayer();
                }
                break;
            case R.id.videoplay:
                    if (!videoview.isPlaying()){
                        videoview.start();
                    }else if (videoview.isPlaying()){
                        videoview.pause();
                    }
                break;
            default:
                break;
        }

    }

    private void initvideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "大屏测试_02.wmv");
        videoview.setVideoPath(file.getPath());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        if (videoview!=null){
            videoview.suspend();
        }
    }

    private void initmediaplayer() {
        File file = new File(Environment.getExternalStorageDirectory(), "timian.mp3");
        try {
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void play() {

        if (!mediaPlayer.isPlaying()) {

            mediaPlayer.start();
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Take_Photo:
                if (resultCode == RESULT_OK) {
                    displayImage(imageUri.getPath());
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;


            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Logger.d("权限申请失败");
                }
                break;
            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initmediaplayer();
                } else {
                    Logger.d("权限申请失败");
                }
                break;
            case 4:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initvideoPath();
                } else {
                    Logger.d("权限申请失败");
                }
                break;

        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getIamgePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getIamgePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getIamgePath(contentUri, null);

            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getIamgePath(uri, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);

    }

    private String getIamgePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            show.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_LONG).show();
        }
    }


}
