package at.ac.fhsalzburg.swd.spring.dto.medias;

import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.model.medias.Movie;

public class MovieDTO extends MediaDTO {

    private Integer duration;
    private String format;

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
