
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("national_number")
    @Expose
    private Integer nationalNumber;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("country_prefix")
    @Expose
    private Integer countryPrefix;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(Integer nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryPrefix() {
        return countryPrefix;
    }

    public void setCountryPrefix(Integer countryPrefix) {
        this.countryPrefix = countryPrefix;
    }

}
