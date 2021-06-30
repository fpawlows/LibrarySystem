package at.ac.fhsalzburg.swd.spring.services;
import java.util.Date;
import at.ac.fhsalzburg.swd.spring.dao.Customer;
import at.ac.fhsalzburg.swd.spring.dao.Order;
import at.ac.fhsalzburg.swd.spring.dao.Product;

public interface OrderServiceInterface {
	
	public abstract Order addOrder(Date date, Customer customer, Iterable<Product> products);
	
	public abstract Iterable<Order> getAll();
	
	public abstract Order getById(Long id);
	
	public abstract void deleteById(Long id);

}
