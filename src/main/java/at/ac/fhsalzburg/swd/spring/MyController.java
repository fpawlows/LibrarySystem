package at.ac.fhsalzburg.swd.spring;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.ac.fhsalzburg.swd.spring.dao.Customer;
import at.ac.fhsalzburg.swd.spring.services.CustomerServiceInterface;

@Controller // marks the class as a web controller, capable of handling the HTTP requests. Spring will look at the methods of the class marked with the @Controller annotation and establish the routing table to know which methods serve which endpoints.
public class MyController {

	@Autowired // To wire the application parts together, use @Autowired on the fields, constructors, or methods in a component. Spring's dependency injection mechanism wires appropriate beans into the class members marked with @Autowired.
	private ApplicationContext context;
	
	@Resource(name = "sessionBean") // The @Resource annotation is part of the JSR-250 annotation collection and is packaged with Jakarta EE. This annotation has the following execution paths, listed by Match by Name, Match by Type, Match by Qualifier. These execution paths are applicable to both setter and field injection. https://www.baeldung.com/spring-annotations-resource-inject-autowire
	TestBean sessionBean;
	
	
	@Autowired 
	CustomerServiceInterface customerService;
	
	    
    
	@Autowired 
	TestBean singletonBean;
    
    
    
	
	@RequestMapping("/") // The @RequestMapping(method = RequestMethod.GET, value = "/path") annotation specifies a method in the controller that should be responsible for serving the HTTP request to the given path. Spring will work the implementation details of how it's done. You simply specify the path value on the annotation and Spring will route the requests into the correct action methods: https://springframework.guru/spring-requestmapping-annotation/#:~:text=%40RequestMapping%20is%20one%20of%20the,map%20Spring%20MVC%20controller%20methods.
	public String index(Model model, HttpSession session) {
		
		if (session==null)
		{
			model.addAttribute("message","no session");
		}
		else
		{			
			Integer count = (Integer) session.getAttribute("count"); 
			if (count==null)
			{
				count = new Integer(0);				
			}
			count++;
			session.setAttribute("count", count);
		}

		model.addAttribute("message",customerService.doSomething());
		
		model.addAttribute("halloNachricht","welchem to SWD lab");

		model.addAttribute("customers", customerService.getAll());
		
		model.addAttribute("beanSingleton", singletonBean.getHashCode());
		
		TestBean prototypeBean = context.getBean("prototypeBean", TestBean.class);
		model.addAttribute("beanPrototype", prototypeBean.getHashCode());
		
		model.addAttribute("beanSession", sessionBean.getHashCode());
		

	    return "index";
	}
	
	
	@RequestMapping(value = { "/addCustomer" }, method = RequestMethod.POST)
    public String addCustomer(Model model, //
        @ModelAttribute("customerForm") CustomerForm customerForm) { // The @ModelAttribute is an annotation that binds a method parameter or method return value to a named model attribute and then exposes it to a web view: https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation
        String firstName = customerForm.getFirstName();
        String lastName = customerForm.getLastName();
        String eMail = customerForm.getEMail();
        String tel = customerForm.getTel();
        
        customerService.addCustomer(firstName, lastName, eMail,  tel);
         
        return "redirect:/";
	}
	
	@RequestMapping(value = { "/addCustomer" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {
        CustomerForm customerForm = new CustomerForm();
        model.addAttribute("customerForm", customerForm);
        
        model.addAttribute("message",customerService.doSomething());
        
        return "addCustomer";
    }

	
	
	// Mappings for REST-Service
	
	@GetMapping("/customers") // @GetMapping annotation maps HTTP GET requests onto specific handler methods. It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).
    public @ResponseBody List<Customer> allUsers() {

        return (List<Customer>) customerService.getAll();
    }
    
    @RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.GET)
    public @ResponseBody Customer getCustomer(@PathVariable long id) {
    	Customer customer = customerService.getById(id);
    	
    	return customer;
    }
	
    @RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.PUT)
    public String setCustomer(@RequestBody Customer customer) {    	
    	
    	customerService.addCustomer(customer);
    	
    	return "redirect:/customers";
    }
    
    @DeleteMapping("/customers/{id}") // @DeleteMapping annotation maps HTTP DELETE requests onto specific handler methods. It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.DELETE).
    public String delete(@PathVariable String id) {
        Long customerid = Long.parseLong(id);
        customerService.deleteById(customerid);
        return "redirect:/customers";
    }

	
	
}

