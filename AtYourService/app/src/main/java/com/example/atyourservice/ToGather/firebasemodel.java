package com.example.atyourservice.ToGather;

public class firebasemodel {
    String groupname;
    String image;
    String groupid;
    String chatid;

    public firebasemodel(String name, String image, String uid, String chatid) {
        this.groupname = name;
        this.image = image;
        this.groupid = uid;
        this.chatid = chatid;
    }

    public firebasemodel() {
    }

    public String getName() {
        return groupname;
    }

    public void setName(String name) {
        this.groupname = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return groupid;
    }

    public void setUid(String id) {
        this.groupid = id;
    }

    public String getChatId() {return chatid;
    }
}
