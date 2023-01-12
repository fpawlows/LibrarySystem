package at.ac.fhsalzburg.swd.spring.dto.medias;

import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.model.medias.Paper;

public class PaperDTO extends MediaDTO {

    private Integer edition;

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }
}
