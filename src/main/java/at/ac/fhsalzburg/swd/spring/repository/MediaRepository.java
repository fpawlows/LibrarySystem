package at.ac.fhsalzburg.swd.spring.repository;

import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Queue;

public interface MediaRepository extends CrudRepository<Media, Long> {

    @Transactional(timeout = 10)
    Queue<Reservation> getReservationsById(Long Id);
}
