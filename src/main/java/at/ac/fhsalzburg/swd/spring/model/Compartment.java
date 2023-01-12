package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;

import javax.persistence.*;
import java.util.List;


@Entity
public class Compartment {
    @EmbeddedId
    private CompartmentId compartmentId;
    private Integer numberOfPlaces;

    @OneToMany(mappedBy="compartment")
    private List<Copy> copies;

    public Compartment() {
    }

    public Compartment(CompartmentId compartmentId, Integer numberOfPlaces) {
        this.compartmentId = compartmentId;
        this.numberOfPlaces = numberOfPlaces;
    }

    public Integer getNumberOfFreePlaces() {return numberOfPlaces - copies.size();}
    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }

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

    @Override
    public String toString() {
        return "Location: " + compartmentId.getShelf().getId().getLocation().getName() + ", Shelf number: " + compartmentId.getShelf().getId().getShelfNumber() +
            ", Compartment position: " + compartmentId.getPosition();
    }
}
