package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Collection;

import at.ac.fhsalzburg.swd.spring.model.Compartment;
import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;

public class ShelfDTO {

    private ShelfId id;
    private Collection<Compartment> compartments;

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
