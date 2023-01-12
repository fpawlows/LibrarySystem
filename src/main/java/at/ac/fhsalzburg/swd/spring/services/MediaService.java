package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
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

    public class PairBusiness_DTO {
        public Class<? extends Media> Business;
        public Class<? extends MediaDTO> DTO;

        public PairBusiness_DTO(Class Business, Class DTO) {
            this.Business = Business;
            this.DTO = DTO;
        }
    }

    //TODO delete
    private final Map<String, PairBusiness_DTO> mediaClassesPairs = new HashMap<>(){
        {
            put(Book.class.getSimpleName(), new PairBusiness_DTO(Book.class, BookDTO.class));
            put(Audio.class.getSimpleName(), new PairBusiness_DTO(Audio.class, AudioDTO.class));
            put(Paper.class.getSimpleName(), new PairBusiness_DTO(Paper.class, PaperDTO.class));
            put(Movie.class.getSimpleName(), new PairBusiness_DTO(Movie.class, MovieDTO.class));
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


    @Override
    public boolean addMedia(Integer ISBN, String author, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk==null ? 0 : fsk;
            Book media = new Book (ISBN, author, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public boolean addPaper(Integer edition, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk==null ? 0 : fsk;
            Paper media = new Paper (edition, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean addMovie(Integer duration, String format, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk==null ? 0 : fsk;
            Movie media = new Movie (duration, format, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean addAudio(Integer duration, String codec, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk==null ? 0 : fsk;
            Audio media = new Audio (duration, codec, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        }
        else {
            return false;
        }
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
    public boolean addCopy(Media media) {
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
    public Media getMediaById(Long id) {
        Optional<Media> media = mediaRepository.findById(id);
        if (media.isEmpty()) {
            return null;
        }
        else {
            return media.get();
        }
    }

    @Override
    public Copy getCopyById(CopyId copyId) {
        Optional<Copy> copy = copyRepository.findById(copyId);
        if (copy.isEmpty()) {
            return null;
        }
        else {
            return copy.get();
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

    public Map<String, PairBusiness_DTO> getMediaClasses() { return mediaClassesPairs;}

    }
