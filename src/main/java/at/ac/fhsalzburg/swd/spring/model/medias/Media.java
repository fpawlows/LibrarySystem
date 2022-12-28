package at.ac.fhsalzburg.swd.spring.model.medias;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Queue;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Short fsk;
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePublished;

    @ManyToMany
    private Collection<Genre> genres;

    @OneToMany
    private Collection<Copy> copies;

    @OneToMany
    private Queue<Reservation> reservations;

    protected Media() {}

    public Media(String name, String description, Short fsk, Float price) {
        this.name = name;
        this.description = description;
        this.fsk = fsk;
        this.price = BigDecimal.valueOf(price);
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

    public Short getFsk() { return fsk; }

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

    public void setFsk(Short fsk) {  this.fsk = fsk; }

    public void setGenres(Collection<Genre> genres) {
        this.genres = genres;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Queue<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Queue<Reservation> reservations) {
        this.reservations = reservations;
    }
}
