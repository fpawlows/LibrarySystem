package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;

@Entity
public class Copy {

    @EmbeddedId
    private CopyId copyId;


    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "compartment_position", nullable = false),
        @JoinColumn(name = "shelf_number_from_top", nullable = false),
        @JoinColumn(name = "location_id", nullable = false)
    })
    private Compartment compartment;
}
