
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanSetting {

    @SerializedName("term_measure")
    @Expose
    private String termMeasure;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("min_amount")
    @Expose
    private MinAmount minAmount;
    @SerializedName("interest")
    @Expose
    private Integer interest;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getTermMeasure() {
        return termMeasure;
    }

    public void setTermMeasure(String termMeasure) {
        this.termMeasure = termMeasure;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MinAmount getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(MinAmount minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
