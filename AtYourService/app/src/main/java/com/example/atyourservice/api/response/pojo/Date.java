package com.example.atyourservice.api.response.pojo;

public class Date {
    private String localDate;
    private boolean dateTBD;
    private boolean dateTBA;
    private boolean timeTBA;
    private boolean noSpecificTime;

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public boolean isDateTBD() {
        return dateTBD;
    }

    public void setDateTBD(boolean dateTBD) {
        this.dateTBD = dateTBD;
    }

    public boolean isDateTBA() {
        return dateTBA;
    }

    public void setDateTBA(boolean dateTBA) {
        this.dateTBA = dateTBA;
    }

    public boolean isTimeTBA() {
        return timeTBA;
    }

    public void setTimeTBA(boolean timeTBA) {
        this.timeTBA = timeTBA;
    }

    public boolean isNoSpecificTime() {
        return noSpecificTime;
    }

    public void setNoSpecificTime(boolean noSpecificTime) {
        this.noSpecificTime = noSpecificTime;
    }
}
