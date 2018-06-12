package com.example.dell.wx;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardView extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler_view2)
    RecyclerView recyclerView2;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.Drawer_Layout2)
    DrawerLayout DrawerLayout2;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private List<Fruit> fruitList = new ArrayList<>();
    private CardviewAdapter cardviewAdapter;
    private Fruit[] fruits = {new Fruit("1111", R.drawable.p1), new Fruit("222222", R.drawable.p2), new Fruit("33333", R.drawable.p3),
            new Fruit("44444", R.drawable.p4), new Fruit("55555", R.drawable.p5), new Fruit("66666", R.drawable.p6)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        ButterKnife.bind(this);
        initFruits();
        setSupportActionBar(toolbar2);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView2.setLayoutManager(layoutManager);
        cardviewAdapter = new CardviewAdapter(fruitList);
        recyclerView2.setAdapter(cardviewAdapter);
        swipeRefresh.setOnRefreshListener(this);
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }


    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        cardviewAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
