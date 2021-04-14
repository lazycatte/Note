package com.example.demo_newsapp.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;


public class Title extends BmobObject implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String title;
    private String descr;
    private String imageUrl;
    private String uri;
    private String user_objectId;
    private String href;

    public Title(String title, String descr, String imageUrl, String uri, String user_objectId, String href) {
        this.title = title;
        this.descr = descr;
        this.imageUrl = imageUrl;
        this.uri = uri;
        this.user_objectId = user_objectId;
        this.href = href;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUser_objectId() {
        return user_objectId;
    }

    public void setUser_objectId(String user_objectId) {
        this.user_objectId = user_objectId;
    }


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
