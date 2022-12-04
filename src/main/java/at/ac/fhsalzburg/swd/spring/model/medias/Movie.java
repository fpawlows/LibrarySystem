package at.ac.fhsalzburg.swd.spring.model.medias;

import javax.persistence.Entity;

@Entity
public class Movie extends Media {
    private Integer duration;
    private String format;

    Movie() {
    }

    public Movie(Integer duration, String format) {
        this.duration = duration;
        this.format = format;
    }

    public Movie(String name, String description, Short fsk, Float price, Integer duration, String format) {
        super(name, description, fsk, price);
        this.duration = duration;
        this.format = format;
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
}
