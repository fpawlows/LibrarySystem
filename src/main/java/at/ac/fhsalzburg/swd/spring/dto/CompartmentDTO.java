package at.ac.fhsalzburg.swd.spring.dto;



import at.ac.fhsalzburg.swd.spring.dto.ids.CompartmentDTOId;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;

public class CompartmentDTO {

    private CompartmentDTOId compartmentId;
    private Integer numberOfPlaces;

    public CompartmentDTOId getCompartmentId() {
        return compartmentId;
    }
    public void setCompartmentId(CompartmentDTOId compartmentId) {
        this.compartmentId = compartmentId;
    }
    public Integer getNumberOfPlaces() {
        return numberOfPlaces;
    }
    public void setNumberOfPlaces(Integer numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

}
