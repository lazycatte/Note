package com.example.demo_newsapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_newsapp.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


public class UserInfosActivity extends AppCompatActivity {

    private String user_objectId;

    private String username;

    private TextView textview_user_objectId;
    private EditText edit_username, edit_mobilePhoneNumber, edit_email;
    private Button btn_updateinfo;
    private Toolbar toolbar;

    private void init() {
        user_objectId = getIntent().getStringExtra("user_objectId");
    }

    private void initview() {
        textview_user_objectId = findViewById(R.id.textview_user_objectId);
        edit_username = findViewById(R.id.edit_username);
        edit_mobilePhoneNumber = findViewById(R.id.edit_mobilePhoneNumber);
        edit_email = findViewById(R.id.edit_email);
        btn_updateinfo = findViewById(R.id.btn_updateinfo);

        toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Search & Edit user information");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infos);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        initview();

        BmobQuery<BmobUser> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(user_objectId, new QueryListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(UserInfosActivity.this, "Successfully：" + bmobUser.toString(), Toast.LENGTH_SHORT).show();
                    textview_user_objectId.setText(bmobUser.getObjectId());
                    edit_username.setText(bmobUser.getUsername());
                    edit_mobilePhoneNumber.setText(bmobUser.getMobilePhoneNumber());
                    edit_email.setText(bmobUser.getEmail());
                } else {
                    Toast.makeText(UserInfosActivity.this, "Failed：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_updateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_objectId = textview_user_objectId.getText().toString();
                String username = edit_username.getText().toString();
                String mobilePhoneNumber = edit_mobilePhoneNumber.getText().toString();
                String email = edit_email.getText().toString();

                BmobUser bmobUser = new BmobUser();
                bmobUser.setUsername(username);
                bmobUser.setMobilePhoneNumber(mobilePhoneNumber);
                bmobUser.setEmail(email);
                bmobUser.update(user_objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(UserInfosActivity.this, "Edit Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserInfosActivity.this, MainActivity.class);
                            intent.putExtra("user_objectId", user_objectId);
                            intent.putExtra("username", username);
                            intent.putExtra("loginSuccess", "Login successfully");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UserInfosActivity.this, "失Failed：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    /**
     * Manage the return button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(UserInfosActivity.this, MainActivity.class);
                intent.putExtra("user_objectId", user_objectId);
                intent.putExtra("username", username);
                intent.putExtra("loginSuccess", "Login successfully");
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }


}
