package at.ac.fhsalzburg.swd.spring.repository;

import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface MediaRepository extends CrudRepository<Media, Long> {

    @Transactional(timeout = 10)
    Iterable<Media> findAll();

    @Transactional(timeout = 10)
    Optional<Media> findById(Long Long);

    Iterable<Media> findByFsk(Integer fsk);

    List<Media> findByName(String name);

    @Transactional(timeout = 10)
    //TODO add in every repository this @Transactional
    @Query(value =
        "select m from media m join genre g where (:name IS NULL or m.name LIKE '%'||:name||'%') " +
            "or (:fsk IS NULL or m.fsk == :fsk)" +
            "or (:genreId IS NULL or g.id == :genreId)"
        , nativeQuery = true)
    List<Media> findAllOptionalLike(String name, Integer fsk, Long genre);
    //Possible extension with date and more

}

