package com.example.atyourservice.api.response.pojo;

import com.example.atyourservice.models.Chat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Chats implements Serializable {
    private List<Chat> Chats;

    public List<Chat> getChats() {
        return Chats;
    }

    public void setChats(List<Chat> Chats) {
        this.Chats = Chats;
    }

    public Chats() {
        Chats = new ArrayList<>();
    }
}
