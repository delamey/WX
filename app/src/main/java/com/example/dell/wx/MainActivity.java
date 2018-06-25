package com.example.dell.wx;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.greendao.DaoMaster;
import com.example.dell.greendao.DaoSession;
import com.example.dell.greendao.UserDao;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
//    @Inject
//    Fruit fruit;
    @BindView(R.id.xianshi)
    Button Bt;
    @BindView(R.id.seebar)
    Button seebar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.AlterDialog)
    Button AlterDialog;
    @BindView(R.id.progressDialog)
    Button progressDialog;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.Fragment)
    Button Fragment;
    @BindView(R.id.right_fragment)
    FrameLayout rightFragment;

    @BindView(R.id.phone)
    Button phone;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.Drawer_Layout)
    android.support.v4.widget.DrawerLayout DrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.floatButton)
    FloatingActionButton floatButton;
    @BindView(R.id.EventBus2)
    TextView EventBus2;

    private Toolbar toolbar;
    private List<String> contactList = new ArrayList<>();
    private List<Fruit> fruitList = new ArrayList<>();
    private IntentFilter intentFilter;
    private NetworkReceiver networkReceiver;
    private DaoSession daoSession;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerMainComponent.builder().textViewModule(new TextViewModule(this)).build().inject(this);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.n6);
        }

        EventBus.getDefault().register(this);
        initFruits();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(layoutManager);
        FruitAdater fruitAdater = new FruitAdater(fruitList);
        recycler.setAdapter(fruitAdater);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "yonghu", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
        listView.setAdapter(arrayAdapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
        } else {
            readContacts();
        }

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否打印线程号,默认true
                .methodCount(5)         // 展示几个方法数,默认2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) //是否更换打印输出,默认为logcat
                .tag("meee")   // 全局的tag
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkReceiver = new NetworkReceiver();
        registerReceiver(networkReceiver, intentFilter);
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                DrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactList.add(name);
                    contactList.add(number);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void initFruits() {
        int i;
        for (i = 1; i <= 6; i++) {
            Fruit fruit = new Fruit("1", R.drawable.p1);
            fruitList.add(fruit);
            Fruit fruit2 = new Fruit("2", R.drawable.p2);
            fruitList.add(fruit2);
            Fruit fruit3 = new Fruit("3", R.drawable.p3);
            fruitList.add(fruit3);
            Fruit fruit4 = new Fruit("4", R.drawable.p4);
            fruitList.add(fruit4);
            Fruit fruit5 = new Fruit("5", R.drawable.p5);
            fruitList.add(fruit5);
            Fruit fruit6 = new Fruit("6", R.drawable.p6);
            fruitList.add(fruit6);
        }
    }

    @OnClick({R.id.xianshi, R.id.seebar, R.id.AlterDialog, R.id.progressDialog, R.id.Fragment, R.id.phone, R.id.floatButton})
    public void set1(View v) {
        switch (v.getId()) {
            case R.id.xianshi:
                final Intent intent = new Intent(MainActivity.this, Second.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.seebar:
                int progress = progressbar.getProgress();
                progress += 5;
                progressbar.setProgress(progress);
                if (progress == 100) {
                    progress = 0;
                    progressbar.setProgress(progress);
                }
                UserDao userDao = daoSession.getUserDao();
                userDao.insert(new User(null, "delamey", "123456"));
                break;
            case R.id.AlterDialog:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("this");
                dialog.setMessage("important");
                dialog.setCancelable(false);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                break;
            case R.id.progressDialog:
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("loading....");
                progressDialog.setCancelable(true);
                progressDialog.show();
                UserDao userDao2 = daoSession.getUserDao();
                User user = userDao2.queryBuilder().where(UserDao.Properties.Id.eq(1)).build().unique();
                if (user == null) {
                    Toast.makeText(MainActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                } else {
                    userDao2.deleteByKey(user.getId());
                }

                break;
            case R.id.Fragment:
                replaceFragment(new Another());
            case R.id.phone:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }
                break;
            case R.id.floatButton:
                Snackbar.make(v, "Data delete", Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(MainActivity.this, CardView.class);
                        startActivity(intent1);
                    }
                }).show();
                //Toast.makeText(this,"floatButtom",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Logger.d("拒绝");
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Logger.d("通讯录拒绝");
                }
            default:
                break;

        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.right_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String ss = data.getStringExtra("return");
                    Logger.d(ss);
                    break;
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.delete:
                Toast.makeText(this, "1111", Toast.LENGTH_LONG).show();
                break;
            case R.id.backup:
                Toast.makeText(this, "2222", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1)
    public void onMessageEvent(MyEvent event) {
        Logger.d("message is " + event.getMsg());
        // 更新界面
        EventBus2.setText(event.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
