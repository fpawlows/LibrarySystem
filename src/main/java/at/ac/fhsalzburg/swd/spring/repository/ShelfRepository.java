package at.ac.fhsalzburg.swd.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Shelf;
import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;


@Repository
public interface ShelfRepository extends CrudRepository<Shelf, ShelfId> {

	@Transactional(timeout = 10)
    Optional<Shelf> findById(ShelfId id);

}
