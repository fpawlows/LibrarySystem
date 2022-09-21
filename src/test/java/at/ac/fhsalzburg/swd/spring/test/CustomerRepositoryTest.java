package at.ac.fhsalzburg.swd.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import at.ac.fhsalzburg.swd.spring.dao.User;
import at.ac.fhsalzburg.swd.spring.dao.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository customerRepository;

    @Test
    public void whenFindByUsername_thenReturnCustomer() {
        // given
        User customer = new User("Max", "Max Mustermann", "max@muster.com", "123", new Date(),"","USER");
        entityManager.persist(customer);
        entityManager.flush();
        List<User> given = new ArrayList<User>();
        given.add(customer);

        // when
        User found = customerRepository.findByUsername(customer.getUsername());

        // then
        assertEquals(given, found);

    }

}
