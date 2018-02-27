
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionResponse {

    @SerializedName("user_details_provided")
    @Expose
    private Boolean userDetailsProvided;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Boolean getUserDetailsProvided() {
        return userDetailsProvided;
    }

    public void setUserDetailsProvided(Boolean userDetailsProvided) {
        this.userDetailsProvided = userDetailsProvided;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
