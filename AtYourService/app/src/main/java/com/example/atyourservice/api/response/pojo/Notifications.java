package com.example.atyourservice.api.response.pojo;

import com.example.atyourservice.models.Message;
import com.example.atyourservice.models.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Notifications implements Serializable {
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Notifications() {
        notifications = new ArrayList<>();
    }
}
