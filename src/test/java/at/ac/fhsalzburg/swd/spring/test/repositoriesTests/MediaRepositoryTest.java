package at.ac.fhsalzburg.swd.spring.test.repositoriesTests;

import at.ac.fhsalzburg.swd.spring.model.Location;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.ReservationId;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.LocationRepository;
import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class MediaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MediaRepository mediaRepository;

    @Test
    public void whenGetReservations_thenOrderRetained() {
        // given
        Queue<Reservation> reservations = new LinkedList<>();
        ArrayList<Media> givenMedias = new ArrayList<Media>();
        ArrayList<User> users = new ArrayList<User>();
        Integer n_objects = 5;

        for(int i=0; i<n_objects; i++) {
            givenMedias.add(new Book(i, Integer.toString(i)));
            users.add(new User("asdsa", "asda", "asdasd", "725511146", new Date("19.10.2001"),"adsfas", "USER", "asdadas"));
            reservations.add(new Reservation(new ReservationId(givenMedias.get(i), users.get(i)), i));
        }

        givenMedias.get(1).setReservations(reservations);
        entityManager.persist(givenMedias.get(1));
        entityManager.flush();

        // when
        Queue<Reservation> gotReservations = mediaRepository.getReservationsById(givenMedias.get(1).getId());

        // then
        if (gotReservations.isEmpty()){
            assertTrue(false);
        }
        else {
            for (Reservation res: reservations) {
                assertEquals(res, gotReservations.poll());
            }

        }
    }

}
