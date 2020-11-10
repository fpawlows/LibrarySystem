package at.ac.fhsalzburg.swd.spring;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerService implements CustomerServiceInterface {
    
	int i;
	
	@Autowired
    private CustomerRepository repo;
    
    public CustomerService() {
    	i=0;
    }
	
	@Override
	public String doSomething()	{
		i++;
    	return Integer.toString(i);
    	
	}
	
	@Override
	public boolean addCustomer(String firstName, String lastName, String eMail, String Tel) {
		
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            Customer newCustomer = new Customer(firstName, lastName, eMail, Tel);
   
            repo.save(newCustomer);
            return true;
        } 
	        
	    return false;
	    
	}
	
	@Override
	public boolean addCustomer(Customer customer) {
		
        repo.save(customer);
            
	    return false;
	    
	}
	
	@Override
	public Iterable<Customer> getAll() {
		return repo.findAll();
	}
	
	@Override
	public Customer getById(Long id) {
		return repo.findById(id).get();
	}
	
	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
}
