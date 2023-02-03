package at.ac.fhsalzburg.swd.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Loan {
    public static enum loanState {Waiting_For_PickUp, Borrowed, Returned};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "copy_nr", nullable = false),
        @JoinColumn(name = "media_id", nullable = false) //here is id of an id
    })
    private Copy copy;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    private Date dateBorrowed;
    private loanState state;
}
