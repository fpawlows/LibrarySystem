package at.ac.fhsalzburg.swd.spring.test.repositoriesTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Optional;

import at.ac.fhsalzburg.swd.spring.model.Location;
import at.ac.fhsalzburg.swd.spring.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.repository.UserRepository;

import javax.swing.text.html.Option;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class LocationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void whenFindById_thenReturnLocation() {
        // given
        Location givenLocation = new Location(null, "Main Library");
        entityManager.persist(givenLocation);
        entityManager.flush();

        // when
        Optional<Location> optionalLocation = locationRepository.findById(givenLocation.getId());

        // then
        if (optionalLocation.isEmpty()){
            assertTrue(false);
        }
        else {
            Location foundLocation = optionalLocation.get();
            assertEquals(givenLocation, foundLocation);

        }
    }

}
