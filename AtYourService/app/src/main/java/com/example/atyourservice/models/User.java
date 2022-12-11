package com.example.atyourservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private String userId;
    private String name;
    private int age;
    private int socialitescore;
    private String about;
    private String token;

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSocialitescore() {
        return socialitescore;
    }

    public void setSocialitescore(int socialitescore) {
        this.socialitescore = socialitescore;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public User() {
        this.userId = "";
    }

    public User(String username, String token) {
        this.userId = username;
        this.token = token;
        this.name = null;
        this.age = 18;
        this.socialitescore = 0;
        this.about = "Hi! I am here to have fun";
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
