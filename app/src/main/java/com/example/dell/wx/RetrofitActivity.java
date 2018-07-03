package com.example.dell.wx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.RetrofitResult)
    TextView RetrofitResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gitHubService=retrofit.create(GitHubService.class);
        Call<List<Repo>> octocat=gitHubService.listRepos("octocat");
        octocat.enqueue(mCallback);
        helloWorldSimple();

    }
    private Callback<List<Repo>> mCallback=new Callback<List<Repo>>() {
        @Override
        public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
            RetrofitResult.setText(response.body().get(0).getName());
        }

        @Override
        public void onFailure(Call<List<Repo>> call, Throwable t) {

        }
    };
    private static void helloWorldSimple(){
        Consumer<String> consumer=new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        };
        Observable.just("hello").subscribe(consumer);
    }
}
