package com.example.atyourservice.models;

import java.sql.Time;
import java.sql.Timestamp;

public class Message {
    private int stickerID;
    private Timestamp timestamp;
public Message(int stickerID, Timestamp timestamp){
    this.stickerID = stickerID;
    this.timestamp = timestamp;
}
}
