package com.example.atyourservice.ToGather;

public class ModelGroupChatList {
    String groupid,groupname,groupdescription,groupicon,grouptimestamp;

    public ModelGroupChatList() {
    }

    public ModelGroupChatList(String groupid, String groupname, String groupdescription, String groupicon, String grouptimestamp) {
        this.groupid = groupid;
        this.groupname = groupname;
        this.groupdescription = groupdescription;
        this.groupicon = groupicon;
        this.grouptimestamp = grouptimestamp;
    }

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

    public String getGroupdescription() {
        return groupdescription;
    }

    public void setGroupdescription(String groupdescription) {
        this.groupdescription = groupdescription;
    }

    public String getGroupicon() {
        return groupicon;
    }

    public void setGroupicon(String groupicon) {
        this.groupicon = groupicon;
    }

    public String getGrouptimestamp() {
        return grouptimestamp;
    }

    public void setGrouptimestamp(String grouptimestamp) {
        this.grouptimestamp = grouptimestamp;
    }
}

