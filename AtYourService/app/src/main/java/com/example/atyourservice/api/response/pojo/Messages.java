package com.example.atyourservice.api.response.pojo;

import com.example.atyourservice.models.Message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Messages implements Serializable {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Messages() {
        messages = new ArrayList<>();
    }
}
