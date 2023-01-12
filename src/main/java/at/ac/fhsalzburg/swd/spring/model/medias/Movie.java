package at.ac.fhsalzburg.swd.spring.model.medias;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.visitors.MediaVisitor;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Movie extends Media {

    final static String className = "Movie";
    public static String getClassName() {return className;}

    private Integer duration;
    private String format;

    public Movie() {
    }

    public Movie(Integer duration, String format) {
        this.duration = duration;
        this.format = format;
    }

    public Movie(String name, String description, Integer fsk, Integer duration, String format) {
        super(name, description, fsk);
        this.duration = duration;
        this.format = format;
    }

    public Movie(Integer duration, String format, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        super(name, description, fsk, datePublished, genres, copies, reservations);
        this.format = format;
        this.duration = duration;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void accept(MediaVisitor mediaVisitor) {
        mediaVisitor.visit(this);
    }
}
