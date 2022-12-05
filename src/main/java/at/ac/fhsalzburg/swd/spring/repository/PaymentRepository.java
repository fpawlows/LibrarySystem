package at.ac.fhsalzburg.swd.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Payment;
import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;


@Repository
public interface PaymentRepository extends CrudRepository<Payment, PaymentId> {

	@Transactional(timeout = 10)
    Optional<Payment> findById(PaymentId id);

}
