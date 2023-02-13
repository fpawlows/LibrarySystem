package at.ac.fhsalzburg.swd.spring.dto;



import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Data
public class UserDTO implements Serializable {

    private String username;
    private String fullname;
    private String eMail;
    private String tel;
    @DateTimeFormat(iso = ISO.DATE)
    private Date birthDate;
    private String password;
    private String jwttoken;
    private String role;

    private Collection<Reservation> reservations;

    private Collection<Loan> loans;

}
