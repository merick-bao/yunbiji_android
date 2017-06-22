package bbh.fzu.com.ybg.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bbh.fzu.com.ybg.Adapter.MyRecyclerViewAdapter;
import bbh.fzu.com.ybg.Adapter.NoteAdapter;
import bbh.fzu.com.ybg.R;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;

    private Note[] notes = {new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23 15:22"),new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),
            new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),
            new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),
            new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),
            new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23"),new Note("标题1","内容：4545454545454","摘要：5555","2017-5-23")};

    private List<Note> noteList = new ArrayList<>();

    private NoteAdapter noteAdapter;

    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inits();
    }

    private void initNotes() {//初始化笔记
        noteList.clear();
        for (int i=0; i<20;i++){
            Random rand = new Random();
            int index = rand.nextInt(notes.length);
            noteList.add(notes[index]);
        }
    }

    private void inits() {

        //添加自定义标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //侧滑菜单点击事件
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();//关闭侧滑菜单
                return true;
            }
        });

        initNotes();
        RecyclerView recycle_view = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager  layoutManager = new GridLayoutManager(this,1);
        recycle_view.setLayoutManager(layoutManager);
        //noteAdapter = new NoteAdapter(noteList);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this,noteList);
        recycle_view.setAdapter(myRecyclerViewAdapter);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);//休眠2秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initNotes();
                                myRecyclerViewAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);//关闭刷新提示
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//工具栏添加功能菜单
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//工具栏菜单点击事件处理
        switch (item.getItemId()){
            case R.id.toolbar_open:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
        return true;
    }
}
