package at.ac.fhsalzburg.swd.spring.dto;



import at.ac.fhsalzburg.swd.spring.dto.ids.CompartmentDTOId;
import at.ac.fhsalzburg.swd.spring.dto.ids.CopyDTOId;
import at.ac.fhsalzburg.swd.spring.model.Compartment;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;

import java.io.Serializable;

public class CopyDTO implements Serializable {

    private CopyDTOId copyId;
    private CompartmentDTO compartment;
    private Boolean isAvailable;

    public CopyDTOId getCopyId() {
        return copyId;
    }
    public void setCopyId(CopyDTOId copyId) {
        this.copyId = copyId;
    }
    public CompartmentDTO getCompartment() {
        return compartment;
    }
    public void setCompartment(CompartmentDTO compartment) {
        this.compartment = compartment;
    }
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
