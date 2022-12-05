package at.ac.fhsalzburg.swd.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.ids.ReservationId;


@Repository
public interface ReservationRepository extends CrudRepository<Reservation, ReservationId> {

	@Transactional(timeout = 10)
    Optional<Reservation> findById(ReservationId id);

}
