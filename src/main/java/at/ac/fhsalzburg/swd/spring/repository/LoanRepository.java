package at.ac.fhsalzburg.swd.spring.repository;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends CrudRepository <Loan, LoanId> {

    @Transactional(timeout = 10)
    List<Loan> findByDateBorrowed (Date dateBorrowed);

    @Transactional(timeout = 10)
    Optional<Loan> findById(LoanId id);

    @Transactional(timeout = 10)
    List<Loan> findByLoanIdCopy (Copy copy);

    @Transactional(timeout = 10)
    List<Loan> findByLoanIdUser (User user);

}
