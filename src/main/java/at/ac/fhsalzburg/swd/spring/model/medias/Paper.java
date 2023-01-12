package at.ac.fhsalzburg.swd.spring.model.medias;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.medias.visitors.MediaVisitor;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Paper extends Media {

    private Integer edition;

    public Paper(){}

    public Paper(Integer edition) {
        this.edition = edition;
    }

    public Paper(String name, String description, Integer fsk, Integer edition) {
        super(name, description, fsk);
        this.edition = edition;
    }


    public Paper(Integer edition, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        super(name, description, fsk, datePublished, genres, copies, reservations);
        this.edition = edition;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }


    @Override
    public void accept(MediaVisitor mediaVisitor) {
        mediaVisitor.visit(this);
    }
}
