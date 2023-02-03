package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDTO {
    public static enum loanState {Waiting_For_PickUp, Borrowed, Returned};

     private Long id;
    private Date dateBorrowed;
    private loanState state;

}
