package com.rvsoftlab.kanoon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravik on 09-02-2018.
 */

public class Posts {
    private User user;
    private int likes;
    private List<Comments> comments;
    private String imgUrl;

    public Posts(){
        comments = new ArrayList<>();
    }

    public Posts(User user, int likes, List<Comments> comments, String imgUrl) {
        comments = new ArrayList<>();
        this.user = user;
        this.likes = likes;
        this.comments = comments;
        this.imgUrl = imgUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
