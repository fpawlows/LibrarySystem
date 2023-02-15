package at.ac.fhsalzburg.swd.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Loan {
    public static enum loanState {waitingForPickUp, Borrowed, Returned, Cancelled};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumns({
        @JoinColumn(name = "copy_nr", nullable = false),
        @JoinColumn(name = "media_id", nullable = false) //here is id of an id
    })
    private Copy copy;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    //TODO timestamp
    private Timestamp timestampBorrowed;
    private loanState state = loanState.waitingForPickUp;
}
