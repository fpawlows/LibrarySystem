package at.ac.fhsalzburg.swd.spring.model.medias;

import javax.persistence.Entity;

@Entity
public class Paper extends Media {

    final static String className = "Paper";
    public static String getClassName() {return className;}

    private Integer edition;

    public Paper(){}

    public Paper(Integer edition) {
        this.edition = edition;
    }

    public Paper(String name, String description, Integer fsk, Integer edition) {
        super(name, description, fsk);
        this.edition = edition;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }
}
