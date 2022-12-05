package at.ac.fhsalzburg.swd.spring.test.repositoriesTests;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.ac.fhsalzburg.swd.spring.model.Compartment;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Location;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;
import at.ac.fhsalzburg.swd.spring.model.Shelf;
import at.ac.fhsalzburg.swd.spring.repository.CompartmentRepository;
import at.ac.fhsalzburg.swd.spring.repository.GenreRepository;
import at.ac.fhsalzburg.swd.spring.repository.UserRepository;
import at.ac.fhsalzburg.swd.spring.repository.ShelfRepository;
import at.ac.fhsalzburg.swd.spring.repository.LocationRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CompartmentRepository compartmentRepository;

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private LocationRepository locationRepository;


    @Test
    public void whenFindByUsername_thenReturnCustomer() {
        // given
        User givenUser = new User("Max", "Max Mustermann", "max@muster.com", "123", new Date(),"","USER",null);
        entityManager.persist(givenUser);
        entityManager.flush();

        // when
        User foundUser = userRepository.findByUsername(givenUser.getUsername());

        // then
        assertEquals(givenUser, foundUser);

    }

    @Test
    public void whenFindById_thenReturnGenre() {
        // given
        Genre givenGenre = new Genre(1, "Horror");
        Genre foundGenre = null;
        entityManager.persist(givenGenre);
        entityManager.flush();

        // when
        Optional<Genre> optionalFoundGenre = genreRepository.findById(givenGenre.getId());

        if(optionalFoundGenre.isPresent())
            foundGenre = optionalFoundGenre.get();

        // then
        assertEquals(givenGenre, foundGenre);

    }

    @Test
    public void whenFindById_thenReturnShelf() {
        // given
        //Location loc = new Location((long) 1, "Bib");
        //ShelfId sId = new ShelfId(1, loc);
        Shelf givenShelf = new Shelf();
        Shelf foundShelf = null;
        entityManager.persist(givenShelf);
        entityManager.flush();

        // when
        Optional<Shelf> optionalFoundShelf = shelfRepository.findById(givenShelf.getId());

        if(optionalFoundShelf.isPresent())
            foundShelf = optionalFoundShelf.get();

        // then
        assertEquals(givenShelf, foundShelf);

    }

    @Test
    public void whenFindById_thenReturnCompartment() {
        // given
        Compartment givenCompartment = new Compartment();
        Compartment foundCompartment = null;
        entityManager.persist(givenCompartment);
        entityManager.flush();

        // when
        Optional<Compartment> optionalFoundCompartment = compartmentRepository.findById(givenCompartment.getCompartmentId());

        if(optionalFoundCompartment.isPresent())
            foundCompartment = optionalFoundCompartment.get();

        // then
        assertEquals(givenCompartment, foundCompartment);

    }

    @Test
    public void whenFindById_thenReturnLocation() {
        // given
        Location givenLocation = new Location();
        Location foundLocation = null;
        entityManager.persist(givenLocation);
        entityManager.flush();

        // when
        Optional<Location> optionalFoundLocation = locationRepository.findById(givenLocation.getId());

        if(optionalFoundLocation.isPresent())
            foundLocation = optionalFoundLocation.get();

        // then
        assertEquals(givenLocation, foundLocation);

    }


}
