package at.ac.fhsalzburg.swd.spring.model;

import java.io.Serializable;
import java.util.Objects;

public class CompartmentId implements Serializable {
    private Integer shelfRowNumber;
    private Integer compartmentPosition;

    public CompartmentId() {
    }

    public CompartmentId(Integer compartmentPosition, Integer shelfRowNumber) {
        this.compartmentPosition = compartmentPosition;
        this.shelfRowNumber = shelfRowNumber;
    }

    public Integer getCompartmentPosition() {
        return compartmentPosition;
    }

    public void setCompartmentPosition(Integer compartmentPosition) {
        this.compartmentPosition = compartmentPosition;
    }

    public Integer getShelfRowNumber() {
        return shelfRowNumber;
    }

    public void setShelfRowNumber(Integer shelfRowNumber) {
        this.shelfRowNumber = shelfRowNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompartmentId)) return false;
        CompartmentId compartmentId = (CompartmentId) o;
        return Objects.equals(getCompartmentPosition(), compartmentId.getCompartmentPosition()) && Objects.equals(getShelfRowNumber(), compartmentId.getShelfRowNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompartmentPosition(), getShelfRowNumber());
    }
}
