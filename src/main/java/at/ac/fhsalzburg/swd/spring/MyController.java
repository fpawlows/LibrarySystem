package at.ac.fhsalzburg.swd.spring;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

@Controller
@EnableJpaRepositories(basePackageClasses= {CustomerRepository.class})
public class MyController {

	@Autowired
	private ApplicationContext context;
	
	@Resource(name = "sessionBean")
    TestBean sessionBean;
	
	
	@Autowired
	TestServiceI testService;
	
	
    @Autowired //don't forget the setter
    private CustomerRepository repo; 
    
    @Autowired
	TestBean singletonBean;
    
    
    
	
	@RequestMapping("/")
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

		model.addAttribute("message",testService.doSomething());
		
		model.addAttribute("halloNachricht","welchem to SWD lab");

		model.addAttribute("customers", repo.findAll());
		
		model.addAttribute("beanSingleton", singletonBean.getHashCode());
		
		TestBean prototypeBean = context.getBean("prototypeBean", TestBean.class);
		model.addAttribute("beanPrototype", prototypeBean.getHashCode());
		
		model.addAttribute("beanSession", sessionBean.getHashCode());
		

	    return "index";
	}
	
	
	@RequestMapping(value = { "/addCustomer" }, method = RequestMethod.POST)
    public String addCustomer(Model model, //
        @ModelAttribute("customerForm") CustomerForm customerForm) {
        String firstName = customerForm.getFirstName();
        String lastName = customerForm.getLastName();
        
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            Customer newCustomer = new Customer(firstName, lastName);
            //customers.add(newCustomer);
            repo.save(newCustomer);
        } 
        return "redirect:/";
	}
	
	@RequestMapping(value = { "/addCustomer" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {
        CustomerForm customerForm = new CustomerForm();
        model.addAttribute("customerForm", customerForm);
        
        model.addAttribute("message",testService.doSomething());
        
        return "addCustomer";
    }

	
	
	// Mappings for REST-Service
	
	@GetMapping("/customers")
    public @ResponseBody List<Customer> allUsers() {

        return (List<Customer>) repo.findAll();
    }
    
    @RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.GET)
    public @ResponseBody Customer getCustomer(@PathVariable long id) {
    	Customer customer = repo.findById(id);
    	
    	return customer;
    }
	
    @RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.PUT)
    public String setCustomer(@RequestBody Customer customer) {    	
    	
    	repo.save(customer);
    	
    	return "redirect:/customers";
    }
    
    @DeleteMapping("/customers/{id}")
    public String delete(@PathVariable String id) {
        Long customerid = Long.parseLong(id);
        repo.deleteById(customerid);
        return "redirect:/customers";
    }

	
	
}

