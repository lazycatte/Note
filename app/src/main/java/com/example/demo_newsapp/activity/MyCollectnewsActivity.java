package com.example.demo_newsapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo_newsapp.R;
import com.example.demo_newsapp.adapter.TitleAdapter;
import com.example.demo_newsapp.entity.Title;
import com.example.demo_newsapp.utils.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyCollectnewsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout refreshLayout;
    private ListView findtitle_listview;
    private TitleAdapter adapter;

    private List<Title> findTitleList;

    String imageUrl;
    String url;

    String objectId;
    String user_objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollectnews);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        findTitleList = (List<Title>) intent.getSerializableExtra("findTitleList");

        if (findTitleList.size() != 0) {
            user_objectId = findTitleList.get(0).getUser_objectId();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("My favorite");

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        findtitle_listview = findViewById(R.id.findtitle_listview);
        adapter = new TitleAdapter(this, R.layout.list_view_item, findTitleList);
        findtitle_listview.setAdapter(adapter);
        findtitle_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(MyCollectnewsActivity.this, CollectNewsContentActivity.class);

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MyCollectnewsActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                Title title = findTitleList.get(position);
                intent.putExtra("objectId", title.getObjectId());
                intent.putExtra("user_objectId", user_objectId);
                intent.putExtra("descr", title.getDescr());
                intent.putExtra("title", title.getTitle());
                intent.putExtra("imageUrl", title.getImageUrl());
                intent.putExtra("uri", title.getUri());
                intent.putExtra("href",title.getHref());
                intent.putExtra("actionBar_title", "My Collection");
                startActivity(intent);
                finish();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                for (int i = 0; i < findTitleList.size(); i++) {
                    imageUrl = findTitleList.get(i).getImageUrl();
                    url = findTitleList.get(i).getUri();

                    HttpUtil.sendOkHttpRequest(imageUrl, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyCollectnewsActivity.this, "Cannot Load the page", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    findtitle_listview.setSelection(0);
                                    refreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
