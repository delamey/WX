package com.example.dell.wx;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    private com.example.dell.wx.DownloadListener listener;
    public static final int TYPE_SUCCESS=0;
    public static final int TYPE_FAILED=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELED=3;
    private boolean isCanceled=false;
    private boolean isPaused=false;
    private int lastProgress;
    public DownloadTask(com.example.dell.wx.DownloadListener listener) {
        this.listener = listener;
    }

    @Override

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
       switch (integer){
           case TYPE_SUCCESS:
               listener.onSuccess();
               break;
           case TYPE_FAILED:
               listener.onFailed();
               break;
           case TYPE_PAUSED:
               listener.onPaused();
               break;
           case TYPE_CANCELED:
               listener.onCanceled();
               break;
               default:
                   break;
       }
    }
public  void pauseDownload(){
        isPaused=true;
}
public void cancelDownload(){
        isCanceled=true;
}
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress=values[0];
        if (progress>lastProgress){
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is =null;
        RandomAccessFile savedFile=null;
        File file=null;
        long downloadedLength=0;
        String downloadurl=strings[0];
        String fileName=downloadurl.substring(downloadurl.lastIndexOf("/"));
        String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        file=new File(directory,fileName);
        if (file.exists()){
            downloadedLength=file.length();
        }
        try {
            long contentLength=getContentLength(downloadurl);
            if (contentLength==0){
                return TYPE_FAILED;
            }else if (contentLength==downloadedLength){
                return TYPE_SUCCESS;
            }
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .addHeader("RANGE","bytes="+downloadedLength+"-")
                    .url(downloadurl)
                    .build();
            Response response=client.newCall(request).execute();
            if (response!=null){
                is=response.body().byteStream();
                savedFile= new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);
                byte [] b=new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(b))!=-1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total+=len;
                        savedFile.write(b,0,len);
                        int progress= (int) ((total+downloadedLength)*100/contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {

                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled&&file!=null){
                    file.delete();
                }
            }catch(IOException e){
                    e.printStackTrace();
                }


        }
        return TYPE_FAILED;
    }

    private long getContentLength(String downloadurl) throws IOException {
      Response response=okhttp.GetOkhttp(downloadurl);
            if (response!=null&&response.isSuccessful()){
                long contentLength=response.body().contentLength();
                response.body().close();
                return contentLength;
            }
            return 0;


    }
}