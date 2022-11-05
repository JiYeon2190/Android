package com.example.app2;

import android.widget.ImageView;

public class Board {
    private String id;
    private String title;
    private String content;
    private String date;
    private String imgUrl;

    Board() {}

    public Board(String id, String title, String content, String date, String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
