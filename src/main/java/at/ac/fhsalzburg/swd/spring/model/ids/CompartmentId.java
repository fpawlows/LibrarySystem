package at.ac.fhsalzburg.swd.spring.model.ids;

import at.ac.fhsalzburg.swd.spring.model.Shelf;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Data //Getters, setters, constructors
public class CompartmentId implements Serializable {

    @Column(nullable = false)
    private Integer position;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "shelf_number_from_top", nullable = false),
        @JoinColumn(name = "location_id", nullable = false)
    })
    //TOCHECK i think they should be nullable actually
    private Shelf shelf;

    public CompartmentId(Integer position, Shelf shelf) {
        this.position = position;
        this.shelf = shelf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompartmentId)) return false;
        CompartmentId that = (CompartmentId) o;
        return Objects.equals(getPosition(), that.getPosition()) && Objects.equals(getShelf(), that.getShelf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getShelf());
    }
}
