package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import java.util.Date;

enum loanState {Waiting_For_PickUp, Borrowed, Returned};

@Entity
public class Loan {
    @EmbeddedId
    private LoanId loanId;
    private Date dateBorrowed;
    private loanState state;

    public Loan() {}

    public Loan(LoanId loanId, Date dateBorrowed) {
        this.loanId = loanId;
        this.dateBorrowed = dateBorrowed;
        this.state = loanState.Waiting_For_PickUp;
    }

    public Loan(LoanId loanId, Date dateBorrowed, loanState state) {
        this.loanId = loanId;
        this.dateBorrowed = dateBorrowed;
        this.state = state;
    }

    public loanState getState() {
        return state;
    }

    public void setState(loanState state) {
        this.state = state;
    }

    public LoanId getLoanId() {
        return loanId;
    }

    public void setLoanId(LoanId loanId) {
        this.loanId = loanId;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }
}

