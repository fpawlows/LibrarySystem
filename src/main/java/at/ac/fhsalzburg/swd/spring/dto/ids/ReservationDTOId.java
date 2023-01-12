package at.ac.fhsalzburg.swd.spring.dto.ids;

import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTOId implements Serializable {

    private MediaDTO media;

    private UserDTO user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTOId)) return false;
        ReservationDTOId that = (ReservationDTOId) o;
        return Objects.equals(media, that.media) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(media, user);
    }
}
