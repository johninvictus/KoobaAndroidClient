
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credentials {

    @SerializedName("national_card")
    @Expose
    private Integer nationalCard;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;

    public Integer getNationalCard() {
        return nationalCard;
    }

    public void setNationalCard(Integer nationalCard) {
        this.nationalCard = nationalCard;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

}
