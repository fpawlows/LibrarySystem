package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {

    private Long reservationId;
    private Integer numberInQueue;
    private Date reservationDate;
    //TODO Change to ZonedDate

}
