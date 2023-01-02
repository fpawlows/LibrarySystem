package at.ac.fhsalzburg.swd.spring.dto;



import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;

public class CompartmentDTO {

    private CompartmentId compartmentId;
    private Integer numberOfPlaces;

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
