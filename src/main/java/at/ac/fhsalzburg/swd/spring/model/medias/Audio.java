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
public class Audio extends Media {

    final static String className = "Audio";
    public static String getClassName() {return className;}

    private String codec;
    private Integer duration;

    public Audio() {
//TODO change them to protected?
    }

    public Audio(String codec, Integer duration) {
        this.codec = codec;
        this.duration = duration;
    }

    public Audio(Integer duration, String codec, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        super(name, description, fsk, datePublished, genres, copies, reservations);
        this.codec = codec;
        this.duration = duration;
    }

    public Audio(String name, String description, Integer fsk, String codec, Integer duration) {
        super(name, description, fsk);
        this.codec = codec;
        this.duration = duration;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public void accept(MediaVisitor mediaVisitor) {
        mediaVisitor.visit(this);
    }
}
