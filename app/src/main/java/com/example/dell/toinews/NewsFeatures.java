package com.example.dell.toinews;

import android.graphics.Bitmap;

import java.net.URL;

public class NewsFeatures {
    private String author;
    private String title;
    private String description;
    private URL articleUrl;
    private Bitmap thumbnail;
    private String dateTime;

    public NewsFeatures(String author, String title, String description, URL articleUrl,
                        Bitmap thumbnail, String dateTime){
        this.author = author;
        this.title = title;
        this.description = description;
        this.articleUrl = articleUrl;
        this.thumbnail = thumbnail;
        this.dateTime = dateTime;
    }
    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public URL getArticleUrl() {
        return articleUrl;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getDateTime() {
        return dateTime;
    }


}
