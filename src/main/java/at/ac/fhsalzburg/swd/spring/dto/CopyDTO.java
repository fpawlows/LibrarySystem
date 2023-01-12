package at.ac.fhsalzburg.swd.spring.dto;



import at.ac.fhsalzburg.swd.spring.model.Compartment;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;

import java.io.Serializable;

public class CopyDTO implements Serializable {

    private CopyId copyId;
    private Compartment compartment;
    private Boolean isAvailable;

    public CopyId getCopyId() {
        return copyId;
    }
    public void setCopyId(CopyId copyId) {
        this.copyId = copyId;
    }
    public Compartment getCompartment() {
        return compartment;
    }
    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
