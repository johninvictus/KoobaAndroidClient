
package com.invictus.nkoba.nkoba.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanData {

    @SerializedName("suspension_info")
    @Expose
    private SuspensionInfo suspensionInfo;
    @SerializedName("loan_settings")
    @Expose
    private List<LoanSetting> loanSettings = null;
    @SerializedName("loan_limit")
    @Expose
    private LoanLimit loanLimit;
    @SerializedName("has_loan")
    @Expose
    private Boolean hasLoan;

    public SuspensionInfo getSuspensionInfo() {
        return suspensionInfo;
    }

    public void setSuspensionInfo(SuspensionInfo suspensionInfo) {
        this.suspensionInfo = suspensionInfo;
    }

    public List<LoanSetting> getLoanSettings() {
        return loanSettings;
    }

    public void setLoanSettings(List<LoanSetting> loanSettings) {
        this.loanSettings = loanSettings;
    }

    public LoanLimit getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(LoanLimit loanLimit) {
        this.loanLimit = loanLimit;
    }

    public Boolean getHasLoan() {
        return hasLoan;
    }

    public void setHasLoan(Boolean hasLoan) {
        this.hasLoan = hasLoan;
    }

}
