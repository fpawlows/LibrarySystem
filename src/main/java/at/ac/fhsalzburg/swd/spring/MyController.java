package at.ac.fhsalzburg.swd.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableJpaRepositories(basePackageClasses= {CustomerRepository.class})
public class MyController {

	@Autowired
	TestService testService;
	
	private static List<Customer> customers = new ArrayList<Customer>();
	
    static {
        customers.add(new Customer("Bill", "Gates"));
        customers.add(new Customer("Steve", "Jobs"));
    }
    
    @Autowired //don't forget the setter
    private CustomerRepository repo; 
	
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

		model.addAttribute("customers", repo.findAll());

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
        return "addCustomer";
    }
	
	@GetMapping("/index2")
	public String index2(Model model, HttpSession session) {
	    return "index2";
	}

	
	
}

