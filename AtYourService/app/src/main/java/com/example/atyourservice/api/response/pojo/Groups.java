package com.example.atyourservice.api.response.pojo;

import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.Message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Groups implements Serializable {
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public void setMessages(List<Group> groups) {
        this.groups = groups;
    }

    public Groups() {
        groups = new ArrayList<>();
    }
}
