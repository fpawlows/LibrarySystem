package at.ac.fhsalzburg.swd.spring.repository;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends CrudRepository <Loan, Long> {

    @Transactional(timeout = 10)
    List<Loan> findByTimestampBorrowed (Timestamp timestampBorrowed);

    @Transactional(timeout = 10)
    Optional<Loan> findById(Long id);

    @Transactional(timeout = 10)
    List<Loan> findByCopy (Copy copy);

    @Transactional(timeout = 10)
    List<Loan> findByUser (User user);

}
