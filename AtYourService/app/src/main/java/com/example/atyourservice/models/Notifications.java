package com.example.atyourservice.models;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

public class Notifications implements Serializable {
@JsonIgnoreProperties(ignoreUnknown = true)
    private String groupid;
    private String message;

    public Notifications(){
        this.groupid = "groupid";
        this.message="message";
    }

    public Notifications(String groupid, String message){
        this.groupid = groupid;
        this.message = message;
    }


    public String getGroupid() {
        return groupid;
    }

    public String getMessage() {return message;}


    @NonNull
    @Override
    public String toString() {
        return this.groupid + this.message;
    }


}
