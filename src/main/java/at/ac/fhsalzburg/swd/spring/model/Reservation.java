package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.medias.Media;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reservation {

    @EmbeddedId
    private ReservationId reservationId;
    private Integer numberInQueue;

    public Reservation () {}

    public Reservation(ReservationId reservationId, Integer numberInQueue) {
        this.reservationId = reservationId;
        this.numberInQueue = numberInQueue;
    }

    public ReservationId getReservationId() {
        return reservationId;
    }

    public void setReservationId(ReservationId reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getNumberInQueue() {
        return numberInQueue;
    }

    public void setNumberInQueue(Integer numberInQueue) {
        this.numberInQueue = numberInQueue;
    }
}
