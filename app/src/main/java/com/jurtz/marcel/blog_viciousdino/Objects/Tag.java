package com.jurtz.marcel.blog_viciousdino.Objects;

public class Tag {

    private String title;
    private int id;
    private int count;

    public Tag() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Tag(int id, String title, int count) {
        this.id = id;
        this.title = title;
        this.count = count;
    }

    public Tag(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public int getID() {
        return this.id;
    }

    public int getCount() {
        return this.count;
    }
}
