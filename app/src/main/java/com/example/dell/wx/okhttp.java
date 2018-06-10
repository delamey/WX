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
        result=response.toString();

    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
public String PostOkhttp(String url,String param){
    String result="";
    OkHttpClient client=new OkHttpClient();
    RequestBody requestBody=new FormBody.Builder()
            .add("param",param)
            .build();
    Request request=new Request.Builder()
            .url(url)
            .post(requestBody)
            .build();
    try {
        Response response=client.newCall(request).execute();
        result=response.toString();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
}
