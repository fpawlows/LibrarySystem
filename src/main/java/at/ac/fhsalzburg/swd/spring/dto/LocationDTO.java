package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Collection;

import at.ac.fhsalzburg.swd.spring.model.Shelf;

public class LocationDTO {

    private Long id;
    private String roomName;

    private Collection<Shelf> shelves;

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
