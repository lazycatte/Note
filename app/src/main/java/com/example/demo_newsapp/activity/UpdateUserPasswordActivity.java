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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateUserPasswordActivity extends AppCompatActivity {

    private String user_objectId;

    private Toolbar toolbar;

    private TextView textview_user_objectId, textview_username;

    private EditText edit_old_password, edit_new_password;

    private Button btn_update_password;

    private void init() {
        user_objectId = getIntent().getStringExtra("user_objectId1");

    }


    private void initview() {
        textview_user_objectId = findViewById(R.id.user_objectId);

        edit_old_password = findViewById(R.id.edit_old_password);
        edit_new_password = findViewById(R.id.edit_new_password);
        btn_update_password = findViewById(R.id.btn_update_password);

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
        actionBar.setTitle("Change Password");

        textview_user_objectId.setText(user_objectId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_user_password);
        init();
        initview();



        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old_password = edit_old_password.getText().toString();
                String new_password = edit_new_password.getText().toString();
                BmobUser.updateCurrentUserPassword(old_password, new_password, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Intent intent = new Intent(UpdateUserPasswordActivity.this,LoginAndRegistActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(UpdateUserPasswordActivity.this, "Failed：" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(UpdateUserPasswordActivity.this, MainActivity.class);
                intent.putExtra("user_objectId", user_objectId);
                intent.putExtra("loginSuccess", "Login successfully");
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }


}
