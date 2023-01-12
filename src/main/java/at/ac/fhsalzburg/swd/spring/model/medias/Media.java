package at.ac.fhsalzburg.swd.spring.model.medias;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.medias.visitors.VisitableMedia;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.*;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Media implements VisitableMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer fsk;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePublished;

    @ManyToMany
    private List<Genre> genres;

    @OneToMany
    private Collection<Copy> copies;

    @OneToMany(mappedBy = "reservationId.media")
    private List<Reservation> reservations;

    protected Media() {}

    public Media(String name, String description, Integer fsk) {
        this.name = name;
        this.description = description;
        this.fsk = fsk;
    }

    public Media(String name, String description, Integer fsk, Date datePublished, List<Genre> genres) {
        this.name = name;
        this.description = description;
        this.fsk = fsk;
        this.datePublished = datePublished;
        this.genres = genres;
    }

    public Media(String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        this.name = name;
        this.description = description;
        this.fsk = fsk;
        this.datePublished = datePublished;
        this.genres = genres;
        this.copies = copies;
        this.reservations = reservations;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getFsk() { return fsk; }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFsk(Integer fsk) {  this.fsk = fsk; }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public Collection<Copy> getCopies() {
        return copies;
    }

    public void setCopies(Collection<Copy> copies) {
        this.copies = copies;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
