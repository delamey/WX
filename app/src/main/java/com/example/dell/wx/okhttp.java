package com.example.dell.wx;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class okhttp {
public String GetOkhttp(String url){
    String result="";
    OkHttpClient client = new OkHttpClient();
    Request request=new Request.Builder()
            .url(url)
            .build();
    try {
        Response response=client.newCall(request).execute();
        result=response.body().string();

    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
public String PostOkhttp(String url,String param){
    String result="";
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
        result=response.body().string();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
}
