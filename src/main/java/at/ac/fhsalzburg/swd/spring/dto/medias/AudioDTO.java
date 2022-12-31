package at.ac.fhsalzburg.swd.spring.dto.medias;

public class AudioDTO extends MediaDTO {
    private String codec;
    private Integer duration;

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
