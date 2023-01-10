package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MediaServiceInterface {

    public abstract boolean addMedia(String name, String description, Integer fsk, Date datePublished, List<Genre> genres);
    public abstract boolean addMedia(String name, String description, Integer fsk, Date datePublished);
    public abstract boolean addMedia(Media media);
    public abstract boolean addGenre(Genre genre);
    public abstract boolean addGenre(String name);

    public abstract Collection<Media> getAll();

    public abstract void setAvailibility(CopyId copyId, Boolean isAvailable);

    public abstract Collection<Media> getByAllOptional(String name, Integer fsk, List<Genre> genres);

    public abstract Media getById(Long id);

    public abstract void deleteById(Long id);

    public abstract List<Genre> getAllGenres();

    public abstract List<Integer> getPossibleFskValues();

    public abstract Map<String, Class<? extends Media>> getMediaClasses();
    public abstract Map<String, Class<? extends MediaDTO>> getMediaDTOClasses();

}
