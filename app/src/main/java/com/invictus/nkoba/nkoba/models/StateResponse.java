
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateResponse {

    @SerializedName("data")
    @Expose
    private LoanData data;

    public LoanData getData() {
        return data;
    }

    public void setData(LoanData data) {
        this.data = data;
    }

}
