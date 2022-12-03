package at.ac.fhsalzburg.swd.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CompartmentId.class)
public class Compartment {
    @Id
    private Integer shelfNumber;

    @Id
    private Integer position;

    public Compartment() {

    }

    public Compartment(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
