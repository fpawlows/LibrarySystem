package at.ac.fhsalzburg.swd.spring.model.medias;

import javax.persistence.Entity;

@Entity
public class Audio extends Media {
    private String codec;
    private Integer duration;

    public Audio() {

    }

    public Audio(String codec, Integer duration) {
        this.codec = codec;
        this.duration = duration;
    }

    public Audio(String name, String description, Short fsk, Float price, String codec, Integer duration) {
        super(name, description, fsk, price);
        this.codec = codec;
        this.duration = duration;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
