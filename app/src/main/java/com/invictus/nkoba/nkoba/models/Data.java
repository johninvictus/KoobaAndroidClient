
package com.invictus.nkoba.nkoba.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("loan_taken")
    @Expose
    private LoanTaken loanTaken;
    @SerializedName("loan_setting")
    @Expose
    private LoanSetting loanSetting;
    @SerializedName("loan_payments")
    @Expose
    private List<LoanPayment> loanPayments = null;

    public LoanTaken getLoanTaken() {
        return loanTaken;
    }

    public void setLoanTaken(LoanTaken loanTaken) {
        this.loanTaken = loanTaken;
    }

    public LoanSetting getLoanSetting() {
        return loanSetting;
    }

    public void setLoanSetting(LoanSetting loanSetting) {
        this.loanSetting = loanSetting;
    }

    public List<LoanPayment> getLoanPayments() {
        return loanPayments;
    }

    public void setLoanPayments(List<LoanPayment> loanPayments) {
        this.loanPayments = loanPayments;
    }

}
