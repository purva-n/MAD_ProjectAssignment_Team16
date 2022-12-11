package com.example.atyourservice.models;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

public class Notification implements Serializable {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String groupid;
    private String message;

    public Notification(){
        this.groupid = "groupid";
        this.message="message";
    }

    public Notification(String groupid, String message){
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