package at.ac.fhsalzburg.swd.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	@Transactional(timeout = 10)
    List<Customer> findByLastName(String lastName);

	@Transactional(timeout = 10)
	Customer findById(long id);
	
	//@Transactional(timeout = 10)
	//@Override
	//Customer save(Customer customer);
	
}
