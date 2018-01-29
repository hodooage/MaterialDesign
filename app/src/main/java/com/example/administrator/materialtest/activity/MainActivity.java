package com.example.administrator.materialtest.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.materialtest.R;
import com.example.administrator.materialtest.adapter.FruitAdapter;
import com.example.administrator.materialtest.entity.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            //设置显示左上角的默认键
            actionBar.setDisplayHomeAsUpEnabled(true);
            //更改这个默认键的图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        NavigationView navView= (NavigationView) findViewById(R.id.nav_view);
        //设置选中项
        navView.setCheckedItem(R.id.nav_call);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //关闭侧边栏
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "你点我干嘛？谁让你点的？", Toast.LENGTH_SHORT).show();
                Snackbar.make(v,"DATA DELETED",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "DATA RESTORED", Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
            }
        });

        //填充内容
        initFruits();
        RecyclerView recycleView= (RecyclerView) findViewById(R.id.recycler_view);

        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager layoutManager=new GridLayoutManager(this,10);
        recycleView.setLayoutManager(layoutManager);
        adapter=new FruitAdapter(fruitList);
        recycleView.setAdapter(adapter);

        //下拉刷新
        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "clicked Setting", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private Fruit[] fruits={new Fruit(randomName("Apple"),R.drawable.apple),
            new Fruit(randomName("Orange"),R.drawable.orange),
            new Fruit(randomName("Banana"),R.drawable.banana),
            new Fruit(randomName("Pear"),R.drawable.pear),
            new Fruit(randomName("Strawberry"),R.drawable.strawberry),
            new Fruit(randomName("Watermelon"),R.drawable.watermelon),
            new Fruit(randomName("Grape"),R.drawable.grape),
            new Fruit(randomName("Pineapple"),R.drawable.pineapple),
            new Fruit(randomName("Mango"),R.drawable.mango),
            new Fruit(randomName("Cherry"),R.drawable.cherry),
    };

    private String randomName(String name){
        StringBuilder sb=new StringBuilder();
        Random r=new Random();

        for(int i=0;i<r.nextInt(20)+1;i++){
            sb.append(name);
        }
        return sb.toString();
    }

    private List<Fruit> fruitList=new ArrayList<>();

    private FruitAdapter adapter;

    private void initFruits(){
        fruitList.clear();
        for(int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private void refreshFruit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将线程切回主线程，否则无法进行更新UI操作
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        //通知FruitAdapter数据发生了变化，刷新页面,initFruit只是更新了fruitList中的数据
                        adapter.notifyDataSetChanged();
                        //刷新时间结束，隐藏进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
