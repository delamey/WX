package com.example.dell.wx;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class NetworkManager {
    private ImageLoader mImageLoader;
    private  static  NetworkManager sNetworkManager;
    private RequestQueue mQueue;
    private int DEFAULT_IMAGE_CACHE_SIZE=2*1024*1024;

    public static NetworkManager getInstance(){
        if (sNetworkManager==null){
            synchronized (NetworkManager.class){
                if (sNetworkManager==null){
                    sNetworkManager=new NetworkManager();
                }
            }
        }
        return sNetworkManager;
    }
    public void  init(Context context){
        mQueue= Volley.newRequestQueue(context);
        mImageLoader=new ImageLoader(mQueue,new ImageLruCache(DEFAULT_IMAGE_CACHE_SIZE));
    }

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    private static class ImageLruCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache{

        public ImageLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url,bitmap);
        }
    }
    public void sendRequest(Request request){
        mQueue.add(request);
    }
}
