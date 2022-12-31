package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MediaService implements MediaServiceInterface {

    private final String DEFAULT_DESCRIPTION = "No description yet.";

    @Autowired
    private MediaRepository repo;

    @Override
    public boolean addMedia(String name, String description, Integer fsk, Date datePublished, Collection<Genre> genres) {
        if (name != null && name.length() > 0 && Media.possibleFskValues.contains(fsk)) {
        description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;

        Media media = new Media (name, description, fsk, datePublished, genres);
        repo.save(media);
        return true;
        }
    return false;
    }

    @Override
    public boolean addMedia(String name, String description, Integer fsk, Date datePublished) {
            if (name != null && name.length() > 0 && Media.possibleFskValues.contains(fsk)) {
                description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;

                Media media = new Media (name, description, fsk, datePublished, null);
                repo.save(media);
                return true;
            }
            return false;
        }

    @Override
    public boolean addMedia(Media media) {
        repo.save(media);
        return true;
    }

    @Override
    public Collection<Media> getAll() {
        List<Media> medias = new ArrayList<>();

        repo.findAll().forEach(medias::add);
        return medias;
    }

    @Override
    public Media getById(Long id) {
        Optional<Media> media = repo.findById((id));
        if (media.isEmpty()) {
            throw new NoSuchElementException("Wrong Id");
        }
        else {
            return media.get();
        }
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
