package com.example.atyourservice.users.pojo;

import java.sql.Time;
import java.sql.Timestamp;

public class Stickers {
    private int stickerId;
    private long timestamp;

    public Stickers(int stickerId, long timestamp) {
        this.stickerId = stickerId;
        this.timestamp = timestamp;
    }

    public Stickers() {
        this.stickerId = -1;
        this.timestamp = Long.parseLong(new Timestamp(System.currentTimeMillis()).toString());
    }

    public int getStickerId() {
        return stickerId;
    }

    public void setStickerId(int stickerId) {
        this.stickerId = stickerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
