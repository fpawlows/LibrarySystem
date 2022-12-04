package at.ac.fhsalzburg.swd.spring.model.ids;

import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CopyId implements Serializable {

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer copyNr;

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CopyId)) return false;
        CopyId copyId = (CopyId) o;
        return Objects.equals(getCopyNr(), copyId.getCopyNr()) && Objects.equals(getMedia(), copyId.getMedia());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCopyNr(), getMedia());
    }
}
