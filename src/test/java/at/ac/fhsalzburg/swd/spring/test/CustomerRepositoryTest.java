package at.ac.fhsalzburg.swd.spring.test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.ac.fhsalzburg.swd.spring.dao.Customer;
import at.ac.fhsalzburg.swd.spring.dao.CustomerRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CustomerRepository customerRepository;
    
    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Customer customer = new Customer("Max","Mustermann","max@muster.com","123");
        entityManager.persist(customer);
        entityManager.flush();
        List<Customer> given = new ArrayList<Customer>();
        given.add(customer);
     
        // when
        List<Customer> found = customerRepository.findByLastName(customer.getLastName());
     
        // then
        assertIterableEquals(given, found);
        
    }
 
}