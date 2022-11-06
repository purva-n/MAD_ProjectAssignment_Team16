package com.example.atyourservice.models;

import java.io.Serializable;

public class User implements Serializable {
    public String userId;

    public User() {
        this.userId = "";
    }

    public User(String username) {
        this.userId = username;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String username){
        this.userId = username;
    }
}
