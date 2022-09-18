package com.abfresh.in.Abfresh.model;

import java.util.ArrayList;

public class NotificationModel {
    String success;
    String message;
    String desc;

    ArrayList<NotificationModel> notifications_list;
    String id;
    String title;
    String decription;
    String image;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<NotificationModel> getNotifications_list() {
        return notifications_list;
    }

    public void setNotifications_list(ArrayList<NotificationModel> notifications_list) {
        this.notifications_list = notifications_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
