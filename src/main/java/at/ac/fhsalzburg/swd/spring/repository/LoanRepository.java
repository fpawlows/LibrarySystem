package at.ac.fhsalzburg.swd.spring.repository;

import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends CrudRepository <Loan, LoanId> {

    @Transactional(timeout = 10)
    List<Loan> findByName(Date dateBorrowed);

    @Transactional(timeout = 10)
    Optional<Loan> findById(LoanId id);
}
