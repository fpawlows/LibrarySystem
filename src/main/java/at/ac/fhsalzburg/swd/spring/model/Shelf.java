package at.ac.fhsalzburg.swd.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CompartmentId.class)
public class Shelf {
    @Id
    private Long id;

    @Id
    private Integer numberFromTop;

    public Shelf() {
    }

    public Shelf(Long id, Integer rowNumber) {
        this.id = id;
        this.numberFromTop = rowNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberFromTop() {
        return numberFromTop;
    }

    public void setNumberFromTop(Integer numberFromTop) {
        this.numberFromTop = numberFromTop;
    }

}
