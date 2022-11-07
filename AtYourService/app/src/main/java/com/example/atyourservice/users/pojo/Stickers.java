package com.example.atyourservice.users.pojo;

import java.sql.Time;
import java.sql.Timestamp;

public class Stickers {
    private String stickerId;
    private long timestamp;

    public Stickers(String stickerId, long timestamp) {
        this.stickerId = stickerId;
        this.timestamp = timestamp;
    }

    public Stickers() {
        this.stickerId = "";
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
