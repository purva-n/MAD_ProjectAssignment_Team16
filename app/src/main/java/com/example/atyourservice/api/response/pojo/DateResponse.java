package com.example.atyourservice.api.response.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DateResponse implements Serializable {
    private String localDate;
    private String localTime;
    private boolean dateTBD;
    private boolean dateTBA;
    private boolean timeTBA;
    private boolean noSpecificTime;

    public String getLocalDate() {
        String formattedDate = localDate;
        try {
            if(localDate != null) {
                Date format1 = new SimpleDateFormat("yyyy-MM-dd").parse(localDate);
                formattedDate = format1.toString();
            } else {
                formattedDate = "TBD";
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return formattedDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getLocalTime() {
        String formattedTime = localTime;
        try {
            if(localTime != null) {
                Date dateFormat = new SimpleDateFormat("hh:mm:ss").parse(localTime);
                formattedTime = new SimpleDateFormat("hh:mm a").format(dateFormat);
            } else {
                formattedTime = "TBD";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return localTime;
        }

        return formattedTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
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
