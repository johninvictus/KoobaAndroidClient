
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanTaken {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("notified_count")
    @Expose
    private Integer notifiedCount;
    @SerializedName("next_payment_id")
    @Expose
    private Integer nextPaymentId;
    @SerializedName("loan_total")
    @Expose
    private LoanTotal loanTotal;
    @SerializedName("loan_interest")
    @Expose
    private LoanInterest loanInterest;
    @SerializedName("loan_amount")
    @Expose
    private LoanAmount loanAmount;
    @SerializedName("late_fee")
    @Expose
    private LateFee lateFee;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNotifiedCount() {
        return notifiedCount;
    }

    public void setNotifiedCount(Integer notifiedCount) {
        this.notifiedCount = notifiedCount;
    }

    public Integer getNextPaymentId() {
        return nextPaymentId;
    }

    public void setNextPaymentId(Integer nextPaymentId) {
        this.nextPaymentId = nextPaymentId;
    }

    public LoanTotal getLoanTotal() {
        return loanTotal;
    }

    public void setLoanTotal(LoanTotal loanTotal) {
        this.loanTotal = loanTotal;
    }

    public LoanInterest getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(LoanInterest loanInterest) {
        this.loanInterest = loanInterest;
    }

    public LoanAmount getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(LoanAmount loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LateFee getLateFee() {
        return lateFee;
    }

    public void setLateFee(LateFee lateFee) {
        this.lateFee = lateFee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
