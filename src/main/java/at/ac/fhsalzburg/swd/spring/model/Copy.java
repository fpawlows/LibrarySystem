package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Copy {
    //Seriazible because of transfering to flashmap - in fully proper way i would do this with DTO

    @EmbeddedId
    private CopyId copyId;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "compartment_position", nullable = false),
        @JoinColumn(name = "shelf_number_from_top", nullable = false),
        @JoinColumn(name = "location_id", nullable = false)
    })
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

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
