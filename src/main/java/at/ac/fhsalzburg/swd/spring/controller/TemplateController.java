package at.ac.fhsalzburg.swd.spring.controller;

import java.security.Principal;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import at.ac.fhsalzburg.swd.spring.UserForm;
import at.ac.fhsalzburg.swd.spring.TestBean;
import at.ac.fhsalzburg.swd.spring.services.UserService;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;

@Controller // marks the class as a web controller, capable of handling the HTTP requests. Spring
            // will look at the methods of the class marked with the @Controller annotation and
            // establish the routing table to know which methods serve which endpoints.
public class TemplateController {

    // Dependency Injection
    // ----------------------------------------------------------------------

    @Autowired // To wire the application parts together, use @Autowired on the fields,
               // constructors, or methods in a component. Spring's dependency injection mechanism
               // wires appropriate beans into the class members marked with @Autowired.
    private ApplicationContext context;

    @Autowired
    UserServiceInterface userService;

    @Resource(name = "sessionBean") // The @Resource annotation is part of the JSR-250 annotation
                                    // collection and is packaged with Jakarta EE. This annotation
                                    // has the following execution paths, listed by Match by Name,
                                    // Match by Type, Match by Qualifier. These execution paths are
                                    // applicable to both setter and field injection.
                                    // https://www.baeldung.com/spring-annotations-resource-inject-autowire
    TestBean sessionBean;

    @Autowired
    TestBean singletonBean;


    // HTTP Request Mappings GET/POST/... and URL Paths
    // ----------------------------------------------------------------------


    @RequestMapping("/") // The @RequestMapping(method = RequestMethod.GET, value = "/path")
                         // annotation specifies a method in the controller that should be
                         // responsible for serving the HTTP request to the given path. Spring will
                         // work the implementation details of how it's done. You simply specify the
                         // path value on the annotation and Spring will route the requests into the
                         // correct action methods:
                         // https://springframework.guru/spring-requestmapping-annotation/#:~:text=%40RequestMapping%20is%20one%20of%20the,map%20Spring%20MVC%20controller%20methods.
    public String index(Model model, HttpSession session, @CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        if (session == null) {
            model.addAttribute("message", "no session");
        } else {
            Integer count = (Integer) session.getAttribute("count");
            if (count == null) {
                count = Integer.valueOf(0);
            }
            count++;
            session.setAttribute("count", count);
        }
        
        // check if user is logged in
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("user",currentUserName);
        }

        model.addAttribute("message", userService.doSomething());

        model.addAttribute("halloNachricht", "welchem to SWD lab");

        model.addAttribute("users", userService.getAll());

        model.addAttribute("beanSingleton", singletonBean.getHashCode());

        TestBean prototypeBean = context.getBean("prototypeBean", TestBean.class);
        model.addAttribute("beanPrototype", prototypeBean.getHashCode());

        model.addAttribute("beanSession", sessionBean.getHashCode());


        return "index";
    }
    
    @RequestMapping(value = {"/login"})
    public String login(Model model) {
    	return "login";
    }
    
    @RequestMapping(value = {"/login-error"})
    public String loginError(Model model) {
    	model.addAttribute("error","Login error");
    	return "login";
    }

    @RequestMapping(value = {"/admin/addUser"}, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        model.addAttribute("message", userService.doSomething());
        
        
        Authentication lauthentication = SecurityContextHolder.getContext().getAuthentication();        
        model.addAttribute("authenticated", lauthentication);
        

        return "addUser";
    }


    @RequestMapping(value = {"/admin/addUser"}, method = RequestMethod.POST)
    public String addUser(Model model, //
            @ModelAttribute("UserForm") UserForm userForm) { // The @ModelAttribute is
                                                                         // an annotation that binds
                                                                         // a method parameter or
                                                                         // method return value to a
                                                                         // named model attribute
                                                                         // and then exposes it to a
                                                                         // web view:
                                                                         // https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation
        String username = userForm.getUsername();
        String fullName = userForm.getFullname();
        String password = userForm.getPassword();
        String eMail = userForm.getEMail();
        String tel = userForm.getTel();
        Date birth = userForm.getBirthDate();

        userService.addUser(username, fullName, eMail, tel, birth, password, UserService.DEFAULT_ROLE);

        return "redirect:/";
    }
}
