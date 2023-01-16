package at.ac.fhsalzburg.swd.spring.repository;

import java.util.Collection;
import java.util.Optional;

import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;


@Repository
public interface CopyRepository extends CrudRepository<Copy, CopyId> {

	@Transactional(timeout = 10)
    Optional<Copy> findById(CopyId id);

}
