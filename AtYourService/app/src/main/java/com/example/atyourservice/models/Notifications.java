package com.example.atyourservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

public class Notifications implements Serializable {
@JsonIgnoreProperties(ignoreUnknown = true)
    private String title;
    private String body;

    public Notifications(){
    }

    public Notifications(String title, String body){
        this.title = title;
        this.body = body;
    }


    public String getTitle() {
        return title;
    }

    public String getBody() {return body;}


    @Override
    public String toString() {
        return this.title + " " + this.body;
    }


}
