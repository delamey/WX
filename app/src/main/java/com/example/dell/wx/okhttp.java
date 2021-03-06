package com.example.dell.wx;

import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class okhttp {
    static int cacheSize=10*1024*1024;
    static Cache cache=new Cache(new File(Environment.getExternalStorageDirectory().getAbsolutePath()),cacheSize);
public static Response GetOkhttp(String url){
    Response result = null;
    OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();
    Request request=new Request.Builder()
            .url(url)
            .build();
    try {
        Response response=client.newCall(request).execute();
        result=response;

    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
public static Response PostOkhttp(String url, String param){
    Response result = null;
    OkHttpClient client=new OkHttpClient();
    //FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
    //formBody.add("username","zhangsan");//传递键值对参数
    //MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
    //String jsonStr = "{\"username\":\"lisi\",\"nickname\":\"李四\"}";//json数据.
    //RequestBody body = RequestBody.create(JSON, josnStr);
    RequestBody requestBody=new FormBody.Builder()
            .add("param",param)
            .build();
    Request request=new Request.Builder()
            .url(url)
            .post(requestBody)
            .build();
    try {
        Response response=client.newCall(request).execute();
        result=response;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
public static String[] OkhttpGetAsync(String url){
    final String[] result = new String[1];
    Request request=new Request.Builder()
            .get()
            .url(url)
            .build();
    OkHttpClient okHttpClient=new OkHttpClient();
    okHttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Logger.d("fail");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            result[0] =response.body().string();
        }
    });
    return result;
}

}
