
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanInterest {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("cents")
    @Expose
    private Integer cents;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCents() {
        return cents;
    }

    public void setCents(Integer cents) {
        this.cents = cents;
    }

}
