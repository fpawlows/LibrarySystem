package at.ac.fhsalzburg.swd.spring.test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.*;

import at.ac.fhsalzburg.swd.spring.CustomerForm;
import at.ac.fhsalzburg.swd.spring.MyController;
import at.ac.fhsalzburg.swd.spring.dao.Customer;
import at.ac.fhsalzburg.swd.spring.dao.CustomerRepository;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTest {
	
    @Autowired
    private MockMvc mvc;
 	
    // in these tests we focus on the controller, so we don't test the repo and mock the needed behavior
    @MockBean
    private CustomerRepository repo;
    
    @Autowired
    MyController myController;
 
    @Test
    public void whenControllerInjected_thenNotNull() throws Exception {
        assertThat(myController).isNotNull();
    }
    
    // Test /
    @Test
    public void givenNothing_whenHome_thenIndex()
  	      throws Exception {
    
    	mvc.perform(MockMvcRequestBuilders.get("/")
        		.contentType(MediaType.TEXT_HTML))
        		.andExpect(status().isOk())
        		.andExpect(model().attributeExists("customers"))
        		.andExpect(view().name("index"));;
    	
    }
    
    // HTTP add customer test
    @Test
    public void givenCustomerForm_whenAddCustomer_thenRedirect()
    	      throws Exception {
    	
    	CustomerForm form = new CustomerForm();
        form.setFirstName("Max");
        form.setLastName("MusterMann");
        form.setEMail("max@musterm.ann");
        form.setTel("123");
        
        mvc.perform(MockMvcRequestBuilders.post	("/addCustomer",form)
        		.contentType(MediaType.TEXT_HTML))
        		.andExpect(status().is3xxRedirection());
    
    }
    
    
    // REST Service Test
    @Test
    public void givenCustomer_whenGetCustomer_thenReturnJsonArrayTest()
      throws Exception {
         
    	Customer customer = new Customer("Max","Mustermann","max@muster.com","123");
     
        List<Customer> allCustomers = Arrays.asList(customer);
        
        // mock the repo: whenever findAll is called, we will get our predefined customer
        given(repo.findAll()).willReturn(allCustomers);
        
        // call REST service and check
        mvc.perform(get("/customers")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.length()", is(1)))
          .andExpect(jsonPath("$[0].lastName", is(customer.getLastName())));

        
    }
    
    

}
