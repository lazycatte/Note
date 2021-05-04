package com.example.demo_newsapp.activity;

import org.junit.Test;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Note extends BmobObject {
    private String UserName;
    private String Title;
    private String Content;
    private Date createdAt;

    public void setUserName(String UserName){
        this.UserName = UserName;
    }

    public String getUserName(){
        return UserName;
    }

    public void setTitle(String Title){
        this.Title = Title;
    }

    public String getTitle(){
        return Title;
    }

    public void setContent(String Content){
        this.Content = Content;
    }

    public String getContent(){
        return Content;
    }

    public Date getDate(){
        return createdAt;
    }
}
