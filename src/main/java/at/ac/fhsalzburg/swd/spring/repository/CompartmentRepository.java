package at.ac.fhsalzburg.swd.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Compartment;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;


@Repository
public interface CompartmentRepository extends CrudRepository<Compartment, CompartmentId> {

	@Transactional(timeout = 10)
    Optional<Compartment> findById(CompartmentId id);

}
