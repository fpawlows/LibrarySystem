package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.*;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;
import at.ac.fhsalzburg.swd.spring.model.medias.*;
import at.ac.fhsalzburg.swd.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
    private final Map<String, PairBusiness_DTO> mediaClassesPairs = new HashMap<>() {
        {
            put(Book.class.getSimpleName(), new PairBusiness_DTO(Book.class, BookDTO.class));
            put(Audio.class.getSimpleName(), new PairBusiness_DTO(Audio.class, AudioDTO.class));
            put(Paper.class.getSimpleName(), new PairBusiness_DTO(Paper.class, PaperDTO.class));
            put(Movie.class.getSimpleName(), new PairBusiness_DTO(Movie.class, MovieDTO.class));
        }
    };

    //TODO maybe
    //private final Set<Class<? extends Object>> allClasses = (new Reflection("src/main/java/at/ac/fhsalzburg/swd/spring/model/medias")).getSubTypesOf(Media.class);

    private final MediaRepository mediaRepository;

    private final CopyRepository copyRepository;

    private final LocationRepository locationRepository;

    private final CompartmentRepository compartmentRepository;

    private final ShelfRepository shelfRepository;

    private final GenreRepository genreRepository;

    public MediaService(MediaRepository mediaRepository, CopyRepository copyRepository, LocationRepository locationRepository,
                        CompartmentRepository compartmentRepository, ShelfRepository shelfRepository, GenreRepository genreRepository,
                        @Value("myapp.media.default.description") String defaultDescription,
                        @Value("#{'${myapp.media.possible.fsk.values}'.split(',')}") List<Integer> fskValues) {
        //TODO check default description set
        this.possibleFskValues = fskValues;
        this.DEFAULT_DESCRIPTION = defaultDescription;
        this.mediaRepository = mediaRepository;
        this.genreRepository = genreRepository;
        this.copyRepository = copyRepository;
        this.locationRepository = locationRepository;
        this.shelfRepository = shelfRepository;
        this.compartmentRepository = compartmentRepository;
    }

//TODO check in each media if its allowed for particular user
    @Override
    public boolean addBook(Integer ISBN, String author, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk == null ? 0 : fsk;
            Book media = new Book(ISBN, author, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean addPaper(Integer edition, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk == null ? 0 : fsk;
            Paper media = new Paper(edition, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addMovie(Integer duration, String format, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk == null ? 0 : fsk;
            Movie media = new Movie(duration, format, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addAudio(Integer duration, String codec, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        if (name != null && name.length() > 0 && possibleFskValues.contains(fsk)) {
            description = (description == null || description.equals("")) ? DEFAULT_DESCRIPTION : description;
            fsk = fsk == null ? 0 : fsk;
            Audio media = new Audio(duration, codec, name, description, fsk, datePublished, genres, copies, reservations);
            mediaRepository.save(media);
            return true;
        } else {
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
        if (name != null && name.length() > 0) {
            Genre genre = new Genre(name);
            genreRepository.save(genre);
            return true;
        }
        return false;
    }

    @Override
    public boolean addCopy(Media media) {
        CopyId copyId = new CopyId(null, media);
        Copy copy = new Copy(copyId, null, true);
        Collection<Copy> copies = media.getCopies();
        if (copies.add(copy)) {
            media.setCopies(copies);
            copyRepository.save(copy);
            mediaRepository.save(media);
        }
        return true;
    }


    @Override
    public boolean addCopy(Media media, Compartment compartment, Integer copyNr) {
        if (copyNr == null || compartment == null || media == null) {
            return false;
        }
        Collection<Integer> copyNumbers = new ArrayList<>();
        for (Copy copy_ : (media.getCopies() != null ? media.getCopies() : new ArrayList<Copy>())) {
            copyNumbers.add(copy_.getCopyId().getCopyNr());
        }
        if (copyNumbers.contains(copyNr)) {
            return false;
        }
        CopyId copyId = new CopyId(copyNr, media);
        Copy copy = new Copy(copyId, compartment, true);
        Collection<Copy> copies = media.getCopies();

        if (copies == null) {
            copies = new ArrayList<Copy>();
            copies.add(copy);
        } else {
            if (!copies.add(copy)) {
                throw new IllegalArgumentException("Cannot add copy to the list");
            }
        }
        List<Copy> copies_compartment = compartment.getCopies();
        if (copies_compartment == null) {
            copies_compartment = Arrays.asList(copy);
        } else {
            copies_compartment.add(copy);
        }
        compartment.setCopies(copies_compartment);
        media.setCopies(copies);
        copyRepository.save(copy);
//        mediaRepository.save(media);
//        compartmentRepository.save(compartment);
        return true;
    }

    @Override
    public boolean addCopy(Copy copy) {
        copyRepository.save(copy);
        return true;
    }

    @Override
    public boolean addLocation(Location location) {
        locationRepository.save(location);
        return true;
    }

    @Override
    public boolean addLocation(String name) {
        if (name != null && !name.equals("")) {
            locationRepository.save(new Location(null, name));
        }
        return true;
    }

    @Override
    public boolean addShelf(Shelf shelf, Long locationId) {

        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isEmpty()) return false;
        if (shelf == null) return false;
        Location location_valid = location.get();
        if (location_valid.getShelves() == null || !location_valid.getShelves().contains(shelf)) {
            List<Shelf> shelves_arg = Arrays.asList(shelf);
            Collection<Shelf> shelves = location_valid.getShelves();
            if (shelves == null) {
                shelves = shelves_arg;
            } else {
                if (shelves.addAll(shelves_arg)) return false;
            }
            //location_valid.setShelves(shelves);
        }
        locationRepository.save(location_valid);
        shelfRepository.save(shelf);
        return true;
    }

    @Override
    public boolean addShelf(Integer shelfNumber, Long locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isEmpty()) return false;
        if (shelfNumber == null) return false;
        Location location_valid = location.get();
        Shelf shelf = new Shelf(new ShelfId(shelfNumber, location_valid));
        if (location_valid.getShelves() == null || !location_valid.getShelves().contains(shelf)) {
            Collection<Shelf> shelves_arg = Arrays.asList(shelf);
            Collection<Shelf> shelves = location_valid.getShelves();
            if (shelves == null) {
                shelves = shelves_arg;
            } else {
                if (shelves.addAll(shelves_arg)) return false;
            }
            //location_valid.setShelves(shelves);
        }
        shelfRepository.save(shelf);
        locationRepository.save(location_valid);
        return true;
    }

    @Override
    public boolean addCompartment(Compartment compartment, ShelfId shelfId) {
        Optional<Shelf> shelf_optional = shelfRepository.findById(shelfId);
        if (shelf_optional.isEmpty()) return false;
        if (compartment == null) return false;
        Shelf shelf = shelf_optional.get();
        if (shelf.getCompartments() == null || !shelf.getCompartments().contains(compartment)) {
            Collection<Compartment> compartments_args = Arrays.asList(compartment);
            Collection<Compartment> compartments = shelf.getCompartments();
            if (compartments == null) {
                compartments = compartments_args;
            } else {
                if (compartments.addAll(compartments_args)) return false;
            }
            //shelf.setCompartments(compartments);
        }
        shelfRepository.save(shelf);
        compartmentRepository.save(compartment);
        return true;
    }

    @Override
    public boolean addCompartment(Integer numberOfCompartmentPlaces, Integer compartmentPosition, ShelfId shelfId) {
        Optional<Shelf> shelf_optional = shelfRepository.findById(shelfId);
        if (shelf_optional.isEmpty()) return false;
        if (numberOfCompartmentPlaces == null || numberOfCompartmentPlaces < 1) return false;

        Shelf shelf = shelf_optional.get();
        Compartment compartment = new Compartment(new CompartmentId(compartmentPosition, shelf), numberOfCompartmentPlaces);
        if (shelf.getCompartments() == null || !shelf.getCompartments().contains(compartment)) {
            Collection<Compartment> compartments_args = Arrays.asList(compartment);
            Collection<Compartment> compartments = shelf.getCompartments();
            if (compartments == null) {
                compartments = compartments_args;
            } else {
                if (compartments.addAll(compartments_args)) return false;
            }
            //shelf.setCompartments(compartments);
        }
        shelfRepository.save(shelf);
        compartmentRepository.save(compartment);
        return true;
    }


    @Override
    public Collection<Media> getAllMedia() {
        List<Media> medias = new ArrayList<>();

        mediaRepository.findAll().forEach(medias::add);
        return medias;
    }

    @Transactional
    @Override
    public synchronized void setAvailibility(CopyId copyId, Boolean isAvailable) {
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
        } else {
            return media.get();
        }
    }

    @Override
    public Copy getCopyById(CopyId copyId) {
        Optional<Copy> copy = copyRepository.findById(copyId);
        if (copy.isEmpty()) {
            return null;
        } else {
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

    @Override
    public List<Shelf> getAllShelves() {
        List<Shelf> shelves = new ArrayList<>();
        shelfRepository.findAll().forEach(shelves::add);
        return shelves;
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> shelves = new ArrayList<>();
        locationRepository.findAll().forEach(shelves::add);
        return shelves;
    }

    @Override
    public List<Compartment> getAllCompartments() {
        List<Compartment> compartments = new ArrayList<>();
        compartmentRepository.findAll().forEach(compartments::add);
        return compartments;
    }

    public List<Integer> getPossibleFskValues() {
        return possibleFskValues;
    }

    @Override
    public Collection<Location> getLocationByName(String name) {
        Collection<Location> location = locationRepository.findByName(name);
        return location;
    }

    @Override
    public Location getLocationById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.isEmpty() ? null : location.get();
    }

    @Override
    public Shelf getShelfById(ShelfId shelfId) {
        Optional<Shelf> shelf = shelfRepository.findById(shelfId);
        return shelf.isEmpty() ? null : shelf.get();

    }

    @Override
    public Compartment getCompartmentById(CompartmentId compartmentId) {
        Optional<Compartment> compartment = compartmentRepository.findById(compartmentId);
        return compartment.isEmpty() ? null : compartment.get();
    }

    public Map<String, PairBusiness_DTO> getMediaClasses() {
        return mediaClassesPairs;
    }


    public Genre getGenreById(Long Id){
        Optional<Genre> genre = genreRepository.findById(Id);
        return genre.isEmpty()? null : genre.get();
    }

    @Override
    synchronized public Integer getNextQueueNumber(Media media) {
        return media.getReservations().stream().filter(p -> p.getState() == Reservation.reservationState.inQueue || p.getState() == Reservation.reservationState.loanAllowed).collect(Collectors.toList()).size()+1;
    }

}
