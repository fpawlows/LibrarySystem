package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.GenreRepository;
import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MediaService implements MediaServiceInterface {

    private final String DEFAULT_DESCRIPTION = "No description yet.";

    //TODO maybe
    //private final Set<Class<? extends Object>> allClasses = (new Reflection("src/main/java/at/ac/fhsalzburg/swd/spring/model/medias")).getSubTypesOf(Media.class);

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private GenreRepository genreRepository;

    //TODO change all those to addBook addAudio ... (media shouldnt be created itself)
    @Override
    public boolean addMedia(String name, String description, Integer fsk, Date datePublished, List<Genre> genres) {
        if (name != null && name.length() > 0 && Media.possibleFskValues.contains(fsk)) {
        description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
        fsk = fsk==null ? 0 : fsk;
        Media media = new Media (name, description, fsk, datePublished, genres);
        mediaRepository.save(media);
        return true;
        }
    return false;
    }

    @Override
    public boolean addMedia(String name, String description, Integer fsk, Date datePublished) {
            if (name != null && name.length() > 0 && Media.possibleFskValues.contains(fsk)) {
                description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
                fsk = fsk==null ? 0 : fsk;

                Media media = new Media (name, description, fsk, datePublished, null);
                mediaRepository.save(media);
                return true;
            }
            return false;
        }

    @Override
    public boolean addMedia(Media media) {
        mediaRepository.save(media);
        return true;
    }

    @Override
    public boolean addGenre(Genre genre) {
        genreRepository.save(genre);
        return true;
    }

    @Override
    public boolean addGenre(String name) {
        if (name != null && name.length() > 0 ) {
            Genre genre = new Genre (name);
            genreRepository.save(genre);
            return true;
        }
        return false;
    }


    @Override
    public Collection<Media> getAll() {
        List<Media> medias = new ArrayList<>();

        mediaRepository.findAll().forEach(medias::add);
        return medias;
    }

    @Override
    public Media getById(Long id) {
        Optional<Media> media = mediaRepository.findById((id));
        if (media.isEmpty()) {
            throw new NoSuchElementException("Wrong Id");
        }
        else {
            return media.get();
        }
    }

    public Collection<Media> getByAllOptional(String name, Integer fsk, List<Genre> genres) {
        List<Media> medias = new ArrayList<>();

        mediaRepository.findAllOptionalLike(name, fsk, genres).forEach(medias::add);
        return medias;
    }


    @Override
    public void deleteById(Long id) {
        mediaRepository.deleteById(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();

        genreRepository.findAll().forEach(genres::add);

        return genres;
    }


}
