package com.rvsoftlab.kanoon.model;

/**
 * Created by ravik on 09-02-2018.
 */

public class User {
    private String uuid;
    private String userName;
    private String userMobile;
    private String userImg;

    public User(){};

    public User(String uuid, String userName, String userMobile, String userImg) {
        this.uuid = uuid;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userImg = userImg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
