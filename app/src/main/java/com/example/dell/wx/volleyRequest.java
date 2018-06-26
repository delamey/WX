package com.example.dell.wx;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.Logger;

import static com.example.dell.wx.MyApplication.getContext;

public class volleyRequest {
    public  void onStartStringRequset(){
        String url="http://http://gank.io/api/data/Android/10/1";
        StringRequest stringRequest=new StringRequest(url,mStringListener,mErrorListener);
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    private com.android.volley.Response.Listener<String> mStringListener=new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Logger.d(response);
        }
    };
    private com.android.volley.Response.ErrorListener mErrorListener=new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Logger.d(error);
        }
    };
}
