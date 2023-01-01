package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface MediaServiceInterface {

    public abstract boolean addMedia(String name, String description, Integer fsk, Date datePublished, Collection<Genre> genres);
    public abstract boolean addMedia(String name, String description, Integer fsk, Date datePublished);
    public abstract boolean addMedia(Media media);

    public abstract Collection<Media> getAll();

    public abstract Collection<Media> getByAllOptional(String name, Integer fsk, Long genre);

    public abstract Media getById(Long id);

    public abstract void deleteById(Long id);

    public abstract List<Genre> getAllGenres();


}
