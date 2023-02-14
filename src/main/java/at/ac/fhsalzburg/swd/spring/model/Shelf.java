package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Shelf implements Serializable {

    @EmbeddedId
    private ShelfId id;

    @OneToMany(mappedBy = "compartmentId.shelf", fetch = FetchType.LAZY)
    private Collection<Compartment> compartments;

    public Shelf() {
    }

    public Shelf(ShelfId id) {
        this.id = id;
    }
    //TODO change all constructors of Business objects -. there shouldn be ID passed to the constructor _> see in Product, Genre classes

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
