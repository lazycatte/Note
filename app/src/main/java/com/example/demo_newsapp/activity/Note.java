package com.example.demo_newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private TextView tv_register;
    private EditText et_username,et_pwd;
    private CheckBox save_pwd;
    private String userName,passWord,spPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        et_username = findViewById(R.id.username);
        et_pwd = findViewById(R.id.pwd);
        save_pwd = findViewById(R.id.save_pwd);
        login = findViewById(R.id.loginBtn);
        tv_register = findViewById(R.id.register);

        getUserInfo();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditString();

                String md5Psw= MD5Utils.md5(passWord);

                spPsw = readPsw(userName);

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(MainActivity.this, "Please enter your account!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(passWord)){
                    Toast.makeText(MainActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();

                }else if(md5Psw.equals(spPsw)){

                    Toast.makeText(MainActivity.this, "welcome！"+ userName, Toast.LENGTH_SHORT).show();

                    saveLoginInfo(userName,passWord);

                    saveLoginStatus(true, userName);

                    Intent data = new Intent();

                    data.putExtra("isLogin",true);

                    setResult(RESULT_OK,data);

                    MainActivity.this.finish();

                    startActivity(new Intent(MainActivity.this, NoteInformationActivity.class));
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
                    Toast.makeText(MainActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "The account does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }
    private void getEditString(){
        userName = et_username.getText().toString().trim();
        passWord = et_pwd.getText().toString().trim();
    }
    public void saveLoginInfo(String userName, String passWord){

        boolean CheckBoxLogin = save_pwd.isChecked();
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        if (CheckBoxLogin){

            editor.putString("username", userName);
            editor.putString("password", passWord);

            editor.commit();
        }else {
            editor.putString("username", null);
            editor.putString("password", null);
            editor.putBoolean("checkboxBoolean", false);
            editor.commit();
        }
    }

    private String readPsw(String userName){

        SharedPreferences sp  = getSharedPreferences("loginInfo", MODE_PRIVATE);

        return sp.getString(userName , "");
    }

    private void saveLoginStatus(boolean status,String userName){

        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean("isLogin", status);

        editor.putString("loginUserName", userName);

        editor.commit();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){

            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){

                et_username.setText(userName);

                et_username.setSelection(userName.length());
            }
        }
    }
    public  void getUserInfo(){
        SharedPreferences sp = null;
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("checkboxBoolean", false))
        {
            et_username.setText(sp.getString("username", null));
            et_pwd.setText(sp.getString("password", null));
            save_pwd.setChecked(true);
        }else{
            et_username.setText(sp.getString("username", userName));
            et_pwd.setText(sp.getString("password", passWord));
            save_pwd.setChecked(false);
        }
    }
    public void onBackPressed() {
        MainActivity.this.finish();
    }
}

