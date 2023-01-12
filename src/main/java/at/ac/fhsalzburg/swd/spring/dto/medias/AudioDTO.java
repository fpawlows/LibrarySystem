package at.ac.fhsalzburg.swd.spring.dto.medias;

import at.ac.fhsalzburg.swd.spring.model.medias.Audio;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;

import java.io.Serializable;

public class AudioDTO extends MediaDTO {

    private String codec;
    private Integer duration;

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
}
