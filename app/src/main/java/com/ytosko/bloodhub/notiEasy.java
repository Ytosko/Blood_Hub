package com.ytosko.bloodhub;

public class notiEasy {
    private String Name,time,message;

    public notiEasy() {
    }

    public notiEasy(String name, String time, String message) {
        Name = name;
        this.time = time;
        this.message = message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
