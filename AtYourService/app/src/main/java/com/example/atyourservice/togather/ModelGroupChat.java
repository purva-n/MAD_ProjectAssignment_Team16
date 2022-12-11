package com.example.atyourservice.togather;

public class ModelGroupChat {
    String message, sender,sendtime;

    public ModelGroupChat() {
    }

    public ModelGroupChat(String message, String sender, String timestamp, String type) {
        this.message = message;
        this.sender = sender;
        this.sendtime = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return sendtime;
    }

    public void setTimestamp(String timestamp) {
        this.sendtime = timestamp;
    }
}

