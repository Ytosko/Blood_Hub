package com.ytosko.bloodhub;

public class ChatHelper1 {
    private String name;
    private String uid;
    private String time;
    private String lastsender;
    private String lastmsg;
    private String date;

    public ChatHelper1() {
    }


    public ChatHelper1(String name, String uid, String time, String lastsender, String lastmsg, String date) {
        this.name = name;
        this.uid = uid;
        this.time = time;
        this.lastsender = lastsender;
        this.lastmsg = lastmsg;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastsender() {
        return lastsender;
    }

    public void setLastsender(String lastsender) {
        this.lastsender = lastsender;
    }

    public String getTime() {
        return time;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
