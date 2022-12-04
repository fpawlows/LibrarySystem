package at.ac.fhsalzburg.swd.spring.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Shelf {

    @EmbeddedId
    private ShelfId id;

    @OneToMany(mappedBy = "compartmentId.shelf")
    private Collection<Compartment> compartments;

    public Shelf() {
    }

    public Shelf(ShelfId id) {
        this.id = id;
    }

    public Shelf(ShelfId id, Collection<Compartment> compartments) {
        this.id = id;
        this.compartments = compartments;
    }

    public ShelfId getId() {
        return id;
    }

    public void setId(ShelfId id) {
        this.id = id;
    }

    public Collection<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(Collection<Compartment> compartments) {
        this.compartments = compartments;
    }
}
