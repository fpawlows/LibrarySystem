package at.ac.fhsalzburg.swd.spring.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.ac.fhsalzburg.swd.spring.TestBean;
import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import at.ac.fhsalzburg.swd.spring.util.ObjectMapperUtils;

@Controller // marks the class as a web controller, capable of handling the HTTP requests. Spring
// will look at the methods of the class marked with the @Controller annotation and
// establish the routing table to know which methods serve which endpoints.
public class HomeController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    UserServiceInterface userService;

    @RequestMapping("/home")
    public String home(Model model, HttpSession session, @CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        Authentication lauthentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authenticated", lauthentication);

        //TODO Display statistics

        return "home";
    }

    @RequestMapping(value = {"/login_dwa"})
    public String login_dwa(Model model) {return "login";}

    @RequestMapping(value = {"/login-error_dwa"})
    public String loginError_dwa(Model model) {
        model.addAttribute("error", "Login error");
        return "login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model, @RequestParam(value = "username", required = false) String username) {

        User modUser = null;
        UserDTO userDTO = new UserDTO();

        if (username!=null) {
            modUser = userService.getByUsername(username);
        }

        if (modUser!=null) {
            userDTO = ObjectMapperUtils.map(modUser, UserDTO.class);
        }
        else {
            userDTO = new UserDTO();
        }

        model.addAttribute("user", userDTO);

        return "register";
    }

}
