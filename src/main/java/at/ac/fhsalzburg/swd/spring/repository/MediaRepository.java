package at.ac.fhsalzburg.swd.spring.repository;

import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface MediaRepository extends CrudRepository<Media, Long> {

    @Override
    Iterable<Media> findAll();

    @Override
    Optional<Media> findById(Long Long);

    Iterable<Media> findByFsk(Integer fsk);

    List<Media> findByName(String name);
}

