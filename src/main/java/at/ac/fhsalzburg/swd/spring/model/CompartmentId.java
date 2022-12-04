package at.ac.fhsalzburg.swd.spring.model;

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

    //    @Column(name = "SHELF_NUMBER_FROM_TOP", nullable = false)
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
}
