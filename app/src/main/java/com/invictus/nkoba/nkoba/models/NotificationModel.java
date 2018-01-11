package com.invictus.nkoba.nkoba.models;

/**
 * Created by invictus on 1/10/18.
 */

public class NotificationModel {

    private String date;
    private String description;
    private boolean active;

    public NotificationModel(String date, String description, boolean active) {
        this.date = date;
        this.description = description;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
