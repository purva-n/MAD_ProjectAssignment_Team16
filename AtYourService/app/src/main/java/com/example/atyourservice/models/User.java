package com.example.atyourservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private String userId;
    private String name;
    private Date DOB;
    private Integer SocialiteScore;
    private String aboutMe;
    private Integer Zipcode;
    private String token;

    public User() {
        this.userId = "";
    }

    public User(String username, String token) {
        this.userId = username;
        this.token = token;
        this.name = null;
        this.DOB = null;
        this.SocialiteScore = 0;
        this.aboutMe = "Hi! I am here to have fun";
        this.Zipcode = 00000;
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

    public String getName() {
        return name;
    }


}
