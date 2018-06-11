
package com.invictus.nkoba.nkoba.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanHistoryResponse {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("loans_taken")
    @Expose
    private List<LoansTaken> loansTaken = null;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<LoansTaken> getLoansTaken() {
        return loansTaken;
    }

    public void setLoansTaken(List<LoansTaken> loansTaken) {
        this.loansTaken = loansTaken;
    }

}
