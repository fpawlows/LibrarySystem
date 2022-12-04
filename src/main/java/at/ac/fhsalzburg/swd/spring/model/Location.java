package at.ac.fhsalzburg.swd.spring.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "LOCATION")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private Long id;
    private String roomName;

    @OneToMany(mappedBy = "id.location")
    private Collection<Shelf> shelves;
}
