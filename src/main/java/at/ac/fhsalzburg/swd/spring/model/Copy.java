package at.ac.fhsalzburg.swd.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Year;
import java.util.Collection;
import java.util.Date;

@Entity
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "compartment_position", nullable = false),
        @JoinColumn(name = "shelf_number_from_top", nullable = false),
        @JoinColumn(name = "location_id", nullable = false)
    })
    private Compartment compartment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;
}
