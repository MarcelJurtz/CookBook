package com.jurtz.marcel.blog_viciousdino.Objects;

public class Tag {

    private String title;
    private int id;
    private String url;

    public Tag() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Tag(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public int getID() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }
}
