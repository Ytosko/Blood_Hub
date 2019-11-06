package com.ytosko.bloodhub;

public class NotiHelper {

    private String notification, time;

    public NotiHelper() {
    }

    public NotiHelper(String notification, String time) {
        this.notification = notification;
        this.time = time;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
