package com.syncode.bacber.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Berita {


    @SerializedName("author")
    private
    String author;
    @SerializedName("title")
    private
    String title;
    @SerializedName("description")
    private
    String description;

    @SerializedName("url")
    private
    String url;

    @SerializedName("urlToImage")
    private
    String urlimage;

    @SerializedName("publishedAt")
    private
    String dateandtime;

    @SerializedName("content")
    private
    String content;

    @SerializedName("sources")
    private
    List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }

    public Berita() {
    }

    public Berita(String author, String title, String description, String url, String urlimage, String dateandtime, String content, List<String> list) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlimage = urlimage;
        this.dateandtime = dateandtime;
        this.content = content;
        this.list = list;
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

    public String getUrl() {
        return url;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public String getContent() {
        return content;
    }


}
