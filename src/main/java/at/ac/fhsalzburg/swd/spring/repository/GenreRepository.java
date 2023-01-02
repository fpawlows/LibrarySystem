package at.ac.fhsalzburg.swd.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Genre;


@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Transactional(timeout = 10)
    Optional<Genre> findById(Long id);

    @Transactional(timeout = 10)
    List<Genre> findAll();
}
