package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.medias.*;
import at.ac.fhsalzburg.swd.spring.repository.CopyRepository;
import at.ac.fhsalzburg.swd.spring.repository.GenreRepository;
import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MediaService implements MediaServiceInterface {

    private final String DEFAULT_DESCRIPTION;

    private final List<Integer> possibleFskValues;

    //TODO delete
    private final Map<String, Class<? extends Media>> mediaClasses = new HashMap<>(){
        {
            put(Book.getClassName(), Book.class);
            put(Audio.getClassName(), Audio.class);
            put(Paper.getClassName(), Paper.class);
            put(Movie.getClassName(), Movie.class);
        }};

    private final Map<String, Class<? extends MediaDTO>> mediaDTOClasses = new HashMap<>(){
        {
            put(BookDTO.getClassName(), BookDTO.class);
            put(AudioDTO.getClassName(), AudioDTO.class);
            put(PaperDTO.getClassName(), PaperDTO.class);
            put(MovieDTO.getClassName(), MovieDTO.class);
        }};


    //TODO maybe
    //private final Set<Class<? extends Object>> allClasses = (new Reflection("src/main/java/at/ac/fhsalzburg/swd/spring/model/medias")).getSubTypesOf(Media.class);

    private final MediaRepository mediaRepository;

    private final CopyRepository copyRepository;

    private final GenreRepository genreRepository;

    public MediaService(MediaRepository mediaRepository, CopyRepository copyRepository, GenreRepository genreRepository,
                        @Value("myapp.media.default.description") String defaultDescription,
                        @Value("#{'${myapp.media.possible.fsk.values}'.split(',')}") List<Integer> fskValues) {
        this.possibleFskValues = fskValues;
        this.DEFAULT_DESCRIPTION = defaultDescription;
        this.mediaRepository = mediaRepository;
        this.genreRepository = genreRepository;
        this.copyRepository = copyRepository;
    }

    //TODO change all those to addBook addAudio ... (media shouldnt be created itself)
    @Override
    public boolean addMedia(String name, String description, Integer fsk, Date datePublished, List<Genre> genres) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
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
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk == null ? 0 : fsk;

            Media media = new Media(name, description, fsk, datePublished, null);
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

    @Transactional
    @Override
    //TODO idk if this transactional will work
    public void setAvailibility(CopyId copyId, Boolean isAvailable) {
        Optional<Copy> copy = copyRepository.findById(copyId);
        if (copy.isEmpty()) {
            throw new NoSuchElementException("Wrong Id");
        }
        Copy copy_valid = copy.get();
        copy_valid.setAvailable(isAvailable);
        copyRepository.save(copy_valid);
    }

    @Override
    public Media getById(Long id) {
        Optional<Media> media = mediaRepository.findById(id);
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

    public List<Integer> getPossibleFskValues(){
        return possibleFskValues;
    }

    public Map<String, Class<? extends Media>> getMediaClasses() { return mediaClasses;}
    public Map<String, Class<? extends MediaDTO>> getMediaDTOClasses() { return mediaDTOClasses;}

    }
