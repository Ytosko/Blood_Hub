package com.ytosko.bloodhub;

public class vhathelp {
    private String message;
    private String user;
    private String time;
    private  String date;

    public vhathelp() {
    }

    public vhathelp(String message, String user, String time, String date) {
        this.message = message;
        this.user = user;
        this.time = time;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}
