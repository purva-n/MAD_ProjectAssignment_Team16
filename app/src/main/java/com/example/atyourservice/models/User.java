
package com.example.atyourservice.models;

public class User {
    public String username;
    public String uriImage;

    public String getUserName(){
        return username;
    }
    public String getUriImage(){return uriImage;}

    public void setUserName(String username){
        this.username = username;
    }
}
