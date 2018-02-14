package com.rvsoftlab.kanoon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravik on 09-02-2018.
 */

public class Posts {
    private String uuid;
    private User user;
    private int likes;
    private List<Comments> comments;
    private String imgUrl;

    public Posts(){
        comments = new ArrayList<>();
    }

    public Posts(String uuid, User user, int likes, List<Comments> comments, String imgUrl) {
        this.uuid = uuid;
        comments = new ArrayList<>();
        this.user = user;
        this.likes = likes;
        this.comments = comments;
        this.imgUrl = imgUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
