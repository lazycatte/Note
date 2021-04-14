package com.example.demo_newsapp.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsList {
    public Integer code;

    public String msg;

    @SerializedName("newslist")
    public List<News> newsList ;
}
