package com.softserve.academy.models;

import java.util.Date;
import java.util.Objects;

public class Post {
    private int id;
    private String text;
    private Date date;
    private int userId;

    public Post(int id) {
        this.id = id;
    }

    public Post(int id, String text, Date date, int userId) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                userId == post.userId &&
                Objects.equals(text, post.text) &&
                Objects.equals(date, post.date);
    }
}
