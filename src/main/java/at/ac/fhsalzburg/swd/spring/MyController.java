package at.ac.fhsalzburg.swd.spring;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MyController {

	@Autowired
	TestService testService;
	
	
	@GetMapping("/")
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
			
	    return "index";
	}
		
	
	
}

