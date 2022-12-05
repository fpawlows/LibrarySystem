package at.ac.fhsalzburg.swd.spring.test.repositoriesTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Optional;

import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;
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
        Genre givenGenre = new Genre(null, "Horror");
        entityManager.persist(givenGenre);
        entityManager.flush();

        // when
        Optional<Genre> optionalFoundGenre = genreRepository.findById(givenGenre.getId());

        if (optionalFoundGenre.isPresent()) {
            Genre foundGenre = optionalFoundGenre.get();

            // then
            assertEquals(givenGenre, foundGenre);
        }
        else {
            assertTrue(false);
        }
    }

    @Test
    public void whenFindById_thenReturnShelf() {
        // given
        Location loc = new Location(null, "Main Library");
        Shelf givenShelf = new Shelf(new ShelfId(0, loc));
        entityManager.persist(loc);
        entityManager.persist(givenShelf);
        entityManager.flush();

        // when
        Optional<Shelf> optionalFoundShelf = shelfRepository.findById(givenShelf.getId());

        if (optionalFoundShelf.isPresent()) {
            Shelf foundShelf = optionalFoundShelf.get();

            // then
            assertEquals(givenShelf, foundShelf);
        }
        else {
            assertTrue(false);
        }
    }

    @Test
    public void whenFindById_thenReturnCompartment() {
        // given

        Location loc = new Location(null, "Main Library");
        ShelfId shelfid = new ShelfId(0, loc);
        Shelf givenShelf = new Shelf(shelfid);
        Compartment givenCompartment = new Compartment(new CompartmentId(1, givenShelf), 10);
        entityManager.persist(loc);
        entityManager.persist(givenShelf);
        entityManager.persist(givenCompartment);
        entityManager.flush();

        // when
        Optional<Compartment> optionalFoundCompartment = compartmentRepository.findById(givenCompartment.getCompartmentId());

        if(optionalFoundCompartment.isPresent()) {
            Compartment foundCompartment = optionalFoundCompartment.get();

            // then
            assertEquals(givenCompartment, foundCompartment);
        }
        else {
            assertTrue(false);
        }
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
