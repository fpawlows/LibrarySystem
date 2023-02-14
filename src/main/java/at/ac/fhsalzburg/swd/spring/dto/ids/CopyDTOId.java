package at.ac.fhsalzburg.swd.spring.dto.ids;

import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
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
public class CopyDTOId implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer copyNr;

    private MediaDTO media;
}
