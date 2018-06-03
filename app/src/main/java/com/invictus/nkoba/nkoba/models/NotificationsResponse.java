
package com.invictus.nkoba.nkoba.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsResponse {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

}
