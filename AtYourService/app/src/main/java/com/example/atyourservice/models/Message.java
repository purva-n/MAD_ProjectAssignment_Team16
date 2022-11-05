package com.example.atyourservice.models;

import java.sql.Time;
import java.sql.Timestamp;

public class Message {
    private int stickerId;
    private long timestamp;
public Message(int stickerID, long timestamp){
    this.stickerId = stickerID;
    this.timestamp = timestamp;
}
public Message(){
}

    public int getStickerId() {
        return stickerId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
