package com.rvsoftlab.kanoon.model;

/**
 * Created by ravik on 09-02-2018.
 */

public class Comments {
    private String uuid;
    private String postId;
    private String userId;
    private String Comment;
    private String timestamp;

    public Comments(){}

    public Comments(String uuid, String postId, String userId, String comment, String timestamp) {
        this.uuid = uuid;
        this.postId = postId;
        this.userId = userId;
        Comment = comment;
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
