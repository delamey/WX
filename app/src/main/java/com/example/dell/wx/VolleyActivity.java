package com.example.dell.wx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dell.wx.MyApplication.getContext;

public class VolleyActivity extends AppCompatActivity {

    @BindView(R.id.volleyShow)
    TextView volleyShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        ButterKnife.bind(this);
        onStartJsonObjectRequest();

    }

    public  void onStartStringRequset(){
        String url="http://gank.io/api/data/Android/10/1";
        StringRequest stringRequest=new StringRequest(url,mStringListener,mErrorListener);
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    private com.android.volley.Response.Listener<String> mStringListener=new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            volleyShow.setText(response);
            Logger.d(response);
        }
    };
    private com.android.volley.Response.ErrorListener mErrorListener=new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Logger.d(error);
        }
    };
    public void  onStartJsonObjectRequest(){
        String url="http://gank.io/api/data/Android/10/1";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url,null,mJSONObjectListener,mErrorListener);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private Response.Listener<JSONObject> mJSONObjectListener=new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                String desc=response.getJSONArray("results")
                        .getJSONObject(0)
                        .getString("publishedAt");
                volleyShow.setText(desc);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
