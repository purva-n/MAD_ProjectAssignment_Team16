package com.example.atyourservice.api.response.pojo;

public class Dates {
    private DateResponse start;
    private DateResponse end;
    private String timezone;

    public DateResponse getStart() {
        return start;
    }

    public void setStart(DateResponse start) {
        this.start = start;
    }

    public DateResponse getEnd() {
        return end;
    }

    public void setEnd(DateResponse end) {
        this.end = end;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
