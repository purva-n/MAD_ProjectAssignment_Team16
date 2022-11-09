package com.example.atyourservice.models;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String token;

    public User() {
        this.userId = "";
    }

    public User(String username, String token) {
        this.userId = username;
        this.token = token;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String username){
        this.userId = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
