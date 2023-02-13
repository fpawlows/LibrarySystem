package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Date;

import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {

    private MediaDTO media;

    private UserDTO user;
    private Long reservationId;
    private Integer numberInQueue;
    private Date reservationDate;
    //TODO Change to ZonedDate

}
