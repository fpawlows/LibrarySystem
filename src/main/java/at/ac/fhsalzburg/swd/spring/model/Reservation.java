package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import lombok.*;

import javax.persistence.*;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation  implements Serializable {
    public static enum reservationState {inQueue, loanAllowed, loanCreated, canceled};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;
    private Integer numberInQueue;
    private Timestamp reservationTimestamp;
    private Timestamp priorityStartTimestamp;
    private Reservation.reservationState state = reservationState.inQueue;




    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;
    }
