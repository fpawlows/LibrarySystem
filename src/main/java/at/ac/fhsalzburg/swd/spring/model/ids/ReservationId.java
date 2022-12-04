package at.ac.fhsalzburg.swd.spring.model.ids;

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
@Data //Getters, setters, constructors
public class ReservationId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationId)) return false;
        ReservationId that = (ReservationId) o;
        return Objects.equals(getMedia(), that.getMedia()) && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMedia(), getUser());
    }
}
