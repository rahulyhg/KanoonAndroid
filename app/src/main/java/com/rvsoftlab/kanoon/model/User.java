package com.rvsoftlab.kanoon.model;

/**
 * Created by ravik on 09-02-2018.
 */

public class User {
    private String name;
    private String username;
    private String imgUrl;
    private String posts;
    private String followers;
    private String following;
    private String mobile;

    public User(){}

    public User(String name, String username, String imgUrl, String posts, String followers, String following, String mobile) {
        this.name = name;
        this.username = username;
        this.imgUrl = imgUrl;
        this.posts = posts;
        this.followers = followers;
        this.following = following;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
