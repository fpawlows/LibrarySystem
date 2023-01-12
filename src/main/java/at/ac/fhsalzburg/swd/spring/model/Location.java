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
    private String name;

    @OneToMany(mappedBy = "id.location", fetch = FetchType.LAZY)
    private Collection<Shelf> shelves;

    public Location() {}

    public Location(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Location(Long id, String name, Collection<Shelf> shelves) {
        this.id = id;
        this.name = name;
        this.shelves = shelves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String roomName) {
        this.name = roomName;
    }

    public Collection<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(Collection<Shelf> shelves) {
        this.shelves = shelves;
    }
}
