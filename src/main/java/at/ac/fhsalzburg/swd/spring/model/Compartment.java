package at.ac.fhsalzburg.swd.spring.model;

import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;

@Entity
public class Compartment {
    @EmbeddedId
    private CompartmentId compartmentId;
    private Integer numberOfPlaces;

    public Compartment() {
    }

    public Compartment(CompartmentId compartmentId, Integer numberOfPlaces) {
        this.compartmentId = compartmentId;
        this.numberOfPlaces = numberOfPlaces;
    }

    //TODO
//  public Integer getFreeSpaces() {}

    public CompartmentId getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(CompartmentId compartmentId) {
        this.compartmentId = compartmentId;
    }

    public Integer getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(Integer numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }
}
