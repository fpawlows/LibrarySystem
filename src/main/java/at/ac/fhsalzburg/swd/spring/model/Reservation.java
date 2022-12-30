package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.ReservationId;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import java.util.Date;

@Entity
public class Reservation {

    @EmbeddedId
    private ReservationId reservationId;
    private Integer numberInQueue;
    private Date reservationDate;
//TODO Change to ZonedDate

    public Reservation () {}

    public Reservation(ReservationId reservationId, Integer numberInQueue) {
        this.reservationId = reservationId;
        this.numberInQueue = numberInQueue;
        this.reservationDate = new Date();
    }

    public Reservation(ReservationId reservationId, Integer numberInQueue, Date reservationDate) {
        this.reservationId = reservationId;
        this.numberInQueue = numberInQueue;
        this.reservationDate = reservationDate;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
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
