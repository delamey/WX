package com.example.dell.wx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
        //helloWorldSimple();
        hello3();

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
    private static void hello2(){
        Observer<String> observable=new Observer<String>(){
//observer调用时
            @Override
            public void onSubscribe(Disposable d) {
                //Logger.e("onsubscribe");
                System.out.println("onsubscribe");

            }
//onsubscribe之后
            @Override
            public void onNext(String s) {
                //Logger.e("onerror");
                System.out.println("onerror");
            }

            @Override
            public void onError(Throwable e) {

            }
//onnext后
            @Override
            public void onComplete() {
                //Logger.e("oncomplete");
                System.out.println("oncomplete");
            }
        };
        Observable.just("world").subscribe(observable);

    }
    private  static void hello3(){
        Observer<String> observer=new Observer<String>() {
            Disposable mDisposable;
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable=d;
            }

            @Override
            public void onNext(String s) {
                System.out.println("onnext:"+s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onerror:");
            }

            @Override
            public void onComplete() {
                System.out.println("oncomplete:");
            }
        };
        Observable.just("hello","!","!").subscribe(observer);
    }
}
