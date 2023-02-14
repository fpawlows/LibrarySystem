package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Collection;

import at.ac.fhsalzburg.swd.spring.model.Shelf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO {

    private Long id;
    private String roomName;

    private Collection<ShelfDTO> shelves;
}
