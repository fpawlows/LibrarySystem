package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
import at.ac.fhsalzburg.swd.spring.model.*;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MediaServiceInterface {

    boolean addMedia(Integer ISBN, String author, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations);

    boolean addPaper(Integer edition, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations);

    boolean addMovie(Integer duration, String format, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations);

    boolean addAudio(Integer duration, String codec, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations);

    public abstract boolean addMedia(Media media);
    public abstract boolean addGenre(Genre genre);
    public abstract boolean addGenre(String name);
    public abstract boolean addCopy(Media media);

    public abstract boolean addLocation(Location location);
    public abstract boolean addLocation(String name);
    public abstract boolean addShelf(Shelf shelf, Long locationId);
    public abstract boolean addShelf(Integer shelfNumber, Long locationId);
    public abstract boolean addCompartment(Compartment compartment, ShelfId shelfId);

    public abstract boolean addCompartment(Integer numberOfCompartmentPlaces, Integer compartmentPosition, ShelfId shelfId);

    public abstract Collection<Media> getAll();

    public abstract void setAvailibility(CopyId copyId, Boolean isAvailable);

    public abstract Collection<Media> getByAllOptional(String name, Integer fsk, List<Genre> genres);

    public abstract Media getMediaById(Long id);
    public abstract Copy getCopyById(CopyId copyId);
//    public abstract Media getByName(String name);

    public abstract void deleteById(Long id);

    public abstract List<Genre> getAllGenres();
    public abstract List<Shelf> getAllShelves();
    public abstract List<Location> getAllLocations();
    public abstract List<Compartment> getAllCompartments();

    public abstract List<Integer> getPossibleFskValues();

    public abstract Collection<Location> getLocationByName(String name);
    public abstract Location getLocationById(Long id);
    public abstract Shelf getShelfById(ShelfId shelfId);
    public abstract Compartment getCompartmentById(CompartmentId compartmentId);
    public abstract Map<String, MediaService.PairBusiness_DTO> getMediaClasses();

}
