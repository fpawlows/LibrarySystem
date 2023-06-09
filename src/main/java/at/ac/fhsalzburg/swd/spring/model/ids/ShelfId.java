package at.ac.fhsalzburg.swd.spring.model.ids;

import at.ac.fhsalzburg.swd.spring.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Data //Getters, setters, constructors
public class ShelfId implements Serializable {

    //TOCHECK @Column(name = "LOCATION_ID_FK", nullable = false)
    @Column(nullable = false)
    private Integer shelfNumber;

//    @Column(name = "SHELF_NUMBER_FROM_TOP", nullable = false)
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public ShelfId(Integer shelfNumber, Location location) {
        this.shelfNumber = shelfNumber;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShelfId)) return false;
        ShelfId shelfId = (ShelfId) o;
        return Objects.equals(this.getShelfNumber(), shelfId.getShelfNumber()) && Objects.equals(getLocation(), shelfId.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getShelfNumber(), getLocation());
    }
}
