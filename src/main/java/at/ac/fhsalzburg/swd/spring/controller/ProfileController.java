package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import at.ac.fhsalzburg.swd.spring.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    private EntityManager entityManager;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private MediaServiceInterface mediaService;

    @RequestMapping(value = "/profile/{username}")
    public String reserveMedia(
        @PathVariable String username,
        Model model, @CurrentSecurityContext(expression = "authentication") Authentication authentication){

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.getByUsername(currentUserName);
            UserDTO userDTO = ObjectMapperUtils.map(user, UserDTO.class);
            Integer howManyMoreMediaCanBorrow = userService.howManyMoreMediaCanBorrow(currentUserName);
            model.addAttribute("howManyMoreMediaCanBorrow", howManyMoreMediaCanBorrow);

            model.addAttribute("userDTO", userDTO);
        }
        else {
            throw new AuthenticationCredentialsNotFoundException("Not authenticated");
        }
    return "profile";
    }
}
