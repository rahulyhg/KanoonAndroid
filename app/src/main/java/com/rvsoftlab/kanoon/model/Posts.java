package com.rvsoftlab.kanoon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravik on 09-02-2018.
 */

public class Posts {
    private String uuid;
    private String postType;
    private String url;
    private String postVisibility;
    private String caption;
    private int likes;
    private int comments;
    private User user;

    public Posts(){}

    public Posts(String uuid, String postType, String url, String postVisibility, String caption, int likes, int comments, User user) {
        this.uuid = uuid;
        this.postType = postType;
        this.url = url;
        this.postVisibility = postVisibility;
        this.caption = caption;
        this.likes = likes;
        this.comments = comments;
        this.user = user;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostVisibility() {
        return postVisibility;
    }

    public void setPostVisibility(String postVisibility) {
        this.postVisibility = postVisibility;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
