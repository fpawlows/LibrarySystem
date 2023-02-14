package at.ac.fhsalzburg.swd.spring.dto.ids;

import at.ac.fhsalzburg.swd.spring.dto.CompartmentDTO;
import at.ac.fhsalzburg.swd.spring.dto.ShelfDTO;
import at.ac.fhsalzburg.swd.spring.model.Shelf;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class CompartmentDTOId implements Serializable {

    private Integer position;

    private ShelfDTO shelf;

    public CompartmentDTOId(Integer position, ShelfDTO shelf) {
        this.position = position;
        this.shelf = shelf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompartmentDTOId)) return false;
        CompartmentDTOId that = (CompartmentDTOId) o;
        return Objects.equals(position, that.position) && Objects.equals(shelf, that.shelf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, shelf);
    }
}
