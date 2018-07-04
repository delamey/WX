package com.example.dell.wx;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dell.wx.MyApplication.getContext;

public class VolleyActivity extends AppCompatActivity {;

    @BindView(R.id.volleyShow)
    TextView volleyShow;
    @BindView(R.id.net_work_image_view)
    NetworkImageView netWorkImageView;
    private String mCookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        ButterKnife.bind(this);
//        onStartJsonArrayRequest();
//        netWorkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
//        String url="https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg";
//        netWorkImageView.setImageUrl(url,NetworkManager.getInstance().getmImageLoader());
        //onStartJsonArrayRequest();
        onStartJsonObjectRequest();
    }

    public void onStartStringRequset() {
        String url = "http://gank.io/api/data/Android/10/1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, mStringListener, mErrorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    public void onStartString2Requset(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://foo.cloudant.com/_session",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                    volleyShow.setText(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {  //设置头信息
                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/x-www-form-urldecoded");
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {  //设置参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", "foo");
                map.put("password", "bar");
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                for (String s : response.headers.keySet()) {
                    if (s.contains("Set-Cookie")) {
                        mCookie = response.headers.get(s);
                        break;
                    }
                }
                    return super.parseNetworkResponse(response);
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
    private Response.Listener<String> mStringListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            volleyShow.setText(response);
            Logger.d(response);
        }
    };
    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Logger.d(error);
        }
    };

    public void onStartJsonObjectRequest() {
        String url = "http://gank.io/api/data/Android/10/1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, mJSONObjectListener, mErrorListener);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private Response.Listener<JSONObject> mJSONObjectListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                String desc = response.getJSONArray("results")
                        .getJSONObject(0)
                        .getString("url");
                volleyShow.setText(desc);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void onStartJsonArrayRequest() {
        //String url = "https://api.github.com/users/octocat/repos";
        String url="https://192.168.3.11:80/json.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, mJSONArrayListener, mErrorListener);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private Response.Listener<JSONArray> mJSONArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                String name = response.getJSONObject(0).getString("name");
                volleyShow.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void onStartImageRequest(String url) {
        ImageRequest imageRequest = new ImageRequest(url, mBitmapListener, 0, 0, Bitmap.Config.RGB_565, mErrorListener);
        Volley.newRequestQueue(this).add(imageRequest);

    }

    private Response.Listener<Bitmap> mBitmapListener = new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(Bitmap response) {

        }
    };

    public void onStartGsonRequest() {
        String url = "http://gank.io/api/data/Android/10/1";
        GsonRequest<GankBean> request = new GsonRequest<GankBean>(GankBean.class, url, mGankBeanNetworkListener);
        NetworkManager.getInstance().sendRequest(request);
    }

    private NetworkListener<GankBean> mGankBeanNetworkListener = new NetworkListener<GankBean>() {
        @Override
        public void onResponse(GankBean response) {
            String desc = response.getResults().get(0).get_id();
            volleyShow.setText(desc);
        }
    };
}
