package com.example.atyourservice.togather;


import java.util.ArrayList;

public class ModelGroupChatList {
    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupicon() {
        return groupicon;
    }

    public void setGroupicon(String groupicon) {
        this.groupicon = groupicon;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenderPref() {
        return genderPref;
    }

    public void setGenderPref(String genderPref) {
        this.genderPref = genderPref;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    String groupid,groupname,groupicon,eventdate,activity, ageRange,category, date, description, genderPref;
    ArrayList<String> users, groups;

    public ModelGroupChatList() {
    }

    public ModelGroupChatList(String groupid, String activity, String ageRange, String category, String date, String description, String genderPref, String groupname, String groupicon, String eventdate, ArrayList<String> users,ArrayList<String> groups) {
        this.groupid = groupid;
        this.activity = activity;
        this.category = category;
        this.groupname = groupname;
        this.groupicon = groupicon;
        this.eventdate = eventdate;
        this.ageRange = ageRange;
        this.date = date;
        this.description = description;
        this.genderPref = genderPref;
        this.users = users;
        this.groups = groups;
    }

}


