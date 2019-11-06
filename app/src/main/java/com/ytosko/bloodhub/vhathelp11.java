package com.ytosko.bloodhub;

public class vhathelp11 {
    private String message,user,time,date;

    public vhathelp11() {
    }

    public vhathelp11(String message, String user, String time, String date) {
        this.message = message;
        this.user = user;
        this.time = time;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
