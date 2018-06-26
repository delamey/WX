package com.example.dell.wx;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;



public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        long t1=System.nanoTime();
        com.orhanobut.logger.Logger.d(String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));
        Response response=chain.proceed(request);
        CacheControl.Builder builder=new CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES);
        long t2=System.nanoTime();
        com.orhanobut.logger.Logger.d(String.format(String.valueOf(response.request().url()),(t2-t1)/1e6d,response.headers()));
        return response.newBuilder()
                .header("Cache-Control",builder.build().toString())
                .build();
    }
}
