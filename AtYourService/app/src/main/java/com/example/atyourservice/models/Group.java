package com.example.atyourservice.models;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Group {
    private String Name;
    private Date Date;
    private Time Time;
    private Integer MemberCount;
    private List<User> Users;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public java.sql.Time getTime() {
        return Time;
    }

    public void setTime(java.sql.Time time) {
        Time = time;
    }

    public Integer getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(Integer memberCount) {
        MemberCount = memberCount;
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }
}
