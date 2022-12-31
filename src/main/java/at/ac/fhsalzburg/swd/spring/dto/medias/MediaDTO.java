package at.ac.fhsalzburg.swd.spring.dto.medias;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MediaDTO {

    private Long id;
    private String name;
    private String description;
    private Integer fsk;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePublished;

    private Collection<Genre> genres;

    private Collection<Copy> copies;

    private List<Reservation> reservations;

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

    public Collection<Genre> getGenres() {
        return genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String Name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFsk(Integer fsk) {  this.fsk = fsk; }

    public void setGenres(Collection<Genre> genres) {
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
