package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Loan {
    @EmbeddedId
    private LoanId loanId;
    private Date dateBorrowed;

    public Loan() {}

    public Loan(LoanId loanId, Date dateBorrowed) {
        this.loanId = loanId;
        this.dateBorrowed = dateBorrowed;
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

