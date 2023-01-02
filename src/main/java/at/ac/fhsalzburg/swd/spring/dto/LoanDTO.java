package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Date;

import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;

enum loanState {Waiting_For_PickUp, Borrowed, Returned};

public class LoanDTO {

     private LoanId loanId;
    private Date dateBorrowed;
    private loanState state;

    
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
    public loanState getState() {
        return state;
    }
    public void setState(loanState state) {
        this.state = state;
    }

    
}
