package com.example.demo_newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo_newsapp.R;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name_signup, password_signup, password_signup_con;
    private Button signup_btn, toLogin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    public void init(){
//        Bomb Initialization
        Bmob.initialize(this,"988ae71f79851ac817431bee093c1279");
        name_signup = findViewById(R.id.name_signup);
        password_signup = findViewById(R.id.password_signup);
        password_signup_con = findViewById(R.id.password_signup_con);
        signup_btn = findViewById(R.id.signup_btn);
        toLogin_btn = findViewById(R.id.toLogin_btn);
        signup_btn.setOnClickListener(this);
        toLogin_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            Jump to login page
            case R.id.toLogin_btn:
                Intent intent_to_in = new Intent(this, LoginAndRegistActivity.class);
                startActivity(intent_to_in);
                finish();
                break;
//                verify the information typed in before sign up
            case R.id.signup_btn:
                String username_up = name_signup.getText().toString().trim();
                String password_1 = password_signup.getText().toString().trim();
                String password_2 = password_signup_con.getText().toString().trim();
//                check if form has empty value
                if (!username_up.isEmpty() && !password_1.isEmpty()&& !password_2.isEmpty()){
                    if (password_1.equals(password_2)){
                        BmobUser user = new BmobUser();
                        user.setUsername(username_up);
                        user.setPassword(password_2);
                        user.signUp(new SaveListener<BmobUser>() {
                            @Override
                            public void done(BmobUser bmobUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(SignUpActivity.this,"Sign up successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent_to_main = new Intent();
                                    intent_to_main.putExtra("username",username_up);
                                    intent_to_main.putExtra("loginSuccess","Login successfully");
                                    intent_to_main.setClass(SignUpActivity.this,MainActivity.class);
                                    startActivity(intent_to_main);
                                    finish();
                                }else{
                                    Toast.makeText(SignUpActivity.this,"Sign up failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this,"password you confirmed is not the same.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"Please fill in all forms.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

//    private void signupUser(String nameSignUp, String passwordSignUp){
//        BmobUser user = new BmobUser();
//        user.setUsername(nameSignUp);
//        user.setPassword(passwordSignUp);
//        user.signUp(new SaveListener<BmobUser>() {
//
//            @Override
//            public void done(BmobUser bmobUser, BmobException e) {
//                if(e==null){
//                    Toast.makeText(SignUpActivity.this,bmobUser.getUsername()+"Sign up successfully",Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(SignUpActivity.this, "Sign up failed：" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void toLoginUser(String nameSignUp, String passwordSignUp){
//        BmobUser user = new BmobUser();
//        user.setUsername(nameSignUp);
//        user.setPassword(passwordSignUp);
//        user.signUp(new SaveListener<BmobUser>() {
//
//            @Override
//            public void done(BmobUser bmobUser, BmobException e) {
//                if(e==null){
//                    Toast.makeText(SignUpActivity.this,bmobUser.getUsername()+"Sign up successfully",Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(SignUpActivity.this, "Sign up failed：" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}
