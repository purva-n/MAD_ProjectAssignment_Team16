package com.example.atyourservice.api.response.pojo;

import java.util.List;

public class Embedded {
    private List<Events> events;

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }
}
