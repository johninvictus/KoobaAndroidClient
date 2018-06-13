
package com.invictus.nkoba.nkoba.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("loans_taken")
    @Expose
    private List<LoansTaken> loansTaken = null;
    @SerializedName("credentials")
    @Expose
    private Credentials credentials;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LoansTaken> getLoansTaken() {
        return loansTaken;
    }

    public void setLoansTaken(List<LoansTaken> loansTaken) {
        this.loansTaken = loansTaken;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

}
