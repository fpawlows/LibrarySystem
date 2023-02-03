package at.ac.fhsalzburg.swd.spring.test.repositoriesTests;

import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void whenFindReservationsByMedia_thenOrderRetained() {
        // given
        final Integer N_MEDIAS = 40;
        final Integer N_FIRST_MEDIAS_FOR_QUEUES = 3;

        ArrayList<ArrayList<Reservation>> givenReservations = new ArrayList<>();
        ArrayList<Media> givenMedias = new ArrayList<Media>();
        ArrayList<User> givenUsers = new ArrayList<User>();

        for (int i = 0; i < N_FIRST_MEDIAS_FOR_QUEUES; i++) {
            givenReservations.add(new ArrayList<Reservation>());
        }

        for (int i = 0; i < N_MEDIAS; i++) {
            givenMedias.add(new Book(i, Integer.toString(i)));
            User user = new User(i + "chuj", "asda", "asdada", "asdaasd", new Date() ,"asdsadasas", "asdasd", "USER");
            givenUsers.add(user);
            Reservation reservation = new Reservation(null, (int) Math.floor((i + N_FIRST_MEDIAS_FOR_QUEUES) / N_FIRST_MEDIAS_FOR_QUEUES), new Date(), givenMedias.get(i % N_FIRST_MEDIAS_FOR_QUEUES), givenUsers.get(i));
            givenReservations.get(i % N_FIRST_MEDIAS_FOR_QUEUES).add(reservation);

            entityManager.persist(user);
            entityManager.persist(givenMedias.get(i));
            entityManager.persist(reservation);
        }

        entityManager.flush();

        for (int i = 0; i < N_FIRST_MEDIAS_FOR_QUEUES; i++) {
            //when
            List<Reservation> gotReservations = reservationRepository.findAllByMedia(givenMedias.get(i));

            //then
            if (gotReservations.isEmpty()) {
                assertTrue(false);
            } else {
                assertEquals(givenReservations.get(i).size(), gotReservations.size());

                Iterator<Reservation> givenIterator = givenReservations.get(i).iterator();
                Iterator<Reservation> gotIterator = gotReservations.iterator();

                while ((givenIterator.hasNext())) {
                    assertEquals(givenIterator.next(), gotIterator.next());
                }
            }
        }
    }

    @Test
    public void whenFindReservationsByMediaAfterDeletingReservations_thenOrderRetained() {

    }
}
