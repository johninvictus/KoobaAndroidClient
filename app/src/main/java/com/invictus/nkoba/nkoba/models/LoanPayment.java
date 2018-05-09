
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanPayment {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_schedue")
    @Expose
    private String paymentSchedue;
    @SerializedName("payment_remaining")
    @Expose
    private PaymentRemaining paymentRemaining;
    @SerializedName("notified_count")
    @Expose
    private Integer notifiedCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private Amount amount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentSchedue() {
        return paymentSchedue;
    }

    public void setPaymentSchedue(String paymentSchedue) {
        this.paymentSchedue = paymentSchedue;
    }

    public PaymentRemaining getPaymentRemaining() {
        return paymentRemaining;
    }

    public void setPaymentRemaining(PaymentRemaining paymentRemaining) {
        this.paymentRemaining = paymentRemaining;
    }

    public Integer getNotifiedCount() {
        return notifiedCount;
    }

    public void setNotifiedCount(Integer notifiedCount) {
        this.notifiedCount = notifiedCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

}
