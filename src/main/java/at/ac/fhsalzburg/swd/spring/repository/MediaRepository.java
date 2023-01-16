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

    @Transactional(timeout = 10)
    Iterable<Media> findByFsk(Integer fsk);

    @Transactional(timeout = 10)
    List<Media> findByName(String name);


    @Transactional(timeout = 10)
    //TODO add in every repository this @Transactional
    @Query(value =
        //Maybe not m.genre but just genre
        //TODO create all queries for all media types -now only for book
        "select distinct m from Media m left join m.genres g WHERE (:name IS NULL or m.name LIKE '%'||:name||'%') " +
            "and (:fsk IS NULL or m.fsk = :fsk)" +
            "and ((:genres) IS NULL or g IN (:genres))")
    List<Media> findAllOptionalLike(@Param("name") String name, @Param("fsk") Integer fsk, @Param("genres") List<Genre> genres);
    //Possible extension with date and more
}

