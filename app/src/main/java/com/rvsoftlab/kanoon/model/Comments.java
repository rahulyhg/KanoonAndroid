package com.rvsoftlab.kanoon.model;

/**
 * Created by ravik on 09-02-2018.
 */

public class Comments {
    private User user;
    private String commnets;

    public Comments(){

    }

    public Comments(User user, String commnets) {
        this.user = user;
        this.commnets = commnets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommnets() {
        return commnets;
    }

    public void setCommnets(String commnets) {
        this.commnets = commnets;
    }
}
