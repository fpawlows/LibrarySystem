package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Date;

import at.ac.fhsalzburg.swd.spring.model.ids.ReservationId;

public class ReservationDTO {

    private ReservationId reservationId;
    private Integer numberInQueue;
    private Date reservationDate;
    //TODO Change to ZonedDate

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
    public Date getReservationDate() {
        return reservationDate;
    }
    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

}
