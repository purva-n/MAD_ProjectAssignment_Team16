package com.example.atyourservice.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {
    private String stickerId;
    private long timestamp;
    private String from;

    public Message(){
    }

    public Message(String stickerId, long timestamp){
        this.stickerId = stickerId;
        this.timestamp = timestamp;
    }


    public String getStickerId() {
        return stickerId;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return this.stickerId + " " + this.timestamp;
    }
}
