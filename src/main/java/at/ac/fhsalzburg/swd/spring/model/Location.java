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

    public Location() {}

    public Location(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public Location(Long id, String roomName, Collection<Shelf> shelves) {
        this.id = id;
        this.roomName = roomName;
        this.shelves = shelves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Collection<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(Collection<Shelf> shelves) {
        this.shelves = shelves;
    }
}
