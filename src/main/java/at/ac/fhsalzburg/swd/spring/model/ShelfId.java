package at.ac.fhsalzburg.swd.spring.model;

import java.io.Serializable;
import java.util.Objects;

public class ShelfId implements Serializable {
    private Long locationId;
    private Integer shelfRowNumber;

    public ShelfId() {
    }

    public ShelfId(Long locationId, Integer shelfRowNumber) {
        this.locationId = locationId;
        this.shelfRowNumber = shelfRowNumber;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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
        CompartmentId shelfId = (CompartmentId) o;
        return Objects.equals(getLocationId(), shelfId.getCompartmentPosition()) && Objects.equals(getShelfRowNumber(), shelfId.getShelfRowNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocationId(), getShelfRowNumber());
    }
}
