package at.ac.fhsalzburg.swd.spring.model.medias;

import javax.persistence.Entity;

@Entity
public class Paper extends Media {
    private Integer edition;

    public Paper(){}

    public Paper(Integer edition) {
        this.edition = edition;
    }

    public Paper(String name, String description, Short fsk, Float price, Integer edition) {
        super(name, description, fsk, price);
        this.edition = edition;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }
}
