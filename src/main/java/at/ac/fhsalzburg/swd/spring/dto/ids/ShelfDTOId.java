package at.ac.fhsalzburg.swd.spring.dto.ids;

import at.ac.fhsalzburg.swd.spring.dto.LocationDTO;
import at.ac.fhsalzburg.swd.spring.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class ShelfDTOId implements Serializable {
    private Integer shelfNumber;

    private LocationDTO location;

    public ShelfDTOId(Integer shelfNumber, LocationDTO location) {
        this.shelfNumber = shelfNumber;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShelfDTOId)) return false;
        ShelfDTOId that = (ShelfDTOId) o;
        return Objects.equals(shelfNumber, that.shelfNumber) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfNumber, location);
    }
}
