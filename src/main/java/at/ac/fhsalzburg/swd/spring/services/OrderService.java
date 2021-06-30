package at.ac.fhsalzburg.swd.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.ac.fhsalzburg.swd.spring.dao.Customer;
import at.ac.fhsalzburg.swd.spring.dao.Order;
import at.ac.fhsalzburg.swd.spring.dao.OrderRepository;
import at.ac.fhsalzburg.swd.spring.dao.Product;


@Service
public class OrderService implements OrderServiceInterface {
    
	@Autowired
	private CustomerServiceInterface customerService;
	
	@Autowired
    private OrderRepository repo;
    
    public OrderService() {
    	
    }
	
	
	@Override
	public Order addOrder(Date date, Customer customer, Iterable<Product> products) {
		List<Product> productlist = new ArrayList<Product>();	
		products.forEach(productlist::add);
		
		if (customerService.hasCredit(customer)) {
			Order order = new Order(date,customer,productlist);
			order=repo.save(order);
	        
			return order;
		}
		else {
			return null;
		}
	    
	}

	
	@Override
	public Iterable<Order> getAll() {
		return repo.findAll();
	}
	
	@Override
	public Order getById(Long id) {
		return repo.findById(id).get();
	}
	
	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
}
