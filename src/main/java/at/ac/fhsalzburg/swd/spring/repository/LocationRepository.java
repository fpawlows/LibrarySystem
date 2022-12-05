package at.ac.fhsalzburg.swd.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Location;


@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

	@Transactional(timeout = 10)
    Optional<Location> findById(Long id);

}
