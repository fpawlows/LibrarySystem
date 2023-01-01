package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import at.ac.fhsalzburg.swd.spring.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;

@Controller
public class UserController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    UserServiceInterface userService;

    @RequestMapping(value = {"/login"})
    public String login_dwa(Model model) {return "login";}

    @RequestMapping(value = {"/login-error"})
    public String loginError_dwa(Model model) {
        model.addAttribute("error", "Login error");
        return "login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model, @RequestParam(value = "username", required = false) String username) {

        User modUser = null;
        UserDTO userDTO = new UserDTO();

        if (username != null) {
            modUser = userService.getByUsername(username);
        }

        if (modUser != null) {
            userDTO = ObjectMapperUtils.map(modUser, UserDTO.class);
        } else {
            userDTO = new UserDTO();
        }

        model.addAttribute("user", userDTO);

        return "register";
    }


    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String addUser(Model model, //
                          @ModelAttribute("UserForm") UserDTO userDTO) { // The @ModelAttribute is
        // an annotation that binds
        // a method parameter or
        // method return value to a
        // named model attribute
        // and then exposes it to a
        // web view:

        // merge instances
        User user = ObjectMapperUtils.map(userDTO, User.class);

        // if user already existed in DB, new information is already merged and saved
        // a new user must be persisted (because not managed by entityManager yet)
        if (!entityManager.contains(user)) userService.addUser(user);

        return "redirect:/login";
    }
}

