package at.ac.fhsalzburg.swd.spring.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    MediaServiceInterface mediaService;

    @RequestMapping("/home")
    //TODO change 'home' to '/'
    public String home(

        Model model, HttpSession session,
                       @CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        List<Genre> allGenres = mediaService.getAllGenres();
        model.addAttribute("allGenres", allGenres);

        return "home";
    }

//TODO maybe change this to DTO
    @GetMapping(value={"/search"})
    public String showSearchedMedia(
        @RequestParam (value = "searchMediaId", required = false) Long searchMediaId,
        @RequestParam (value = "searchMediaName", required = false) String searchMediaName,
        @RequestParam (value = "searchMediaFsk", required = false) Integer searchMediaFsk,
        @RequestParam (value = "searchMediaGenreId", required = false) Long searchMediaGenreId,
        Model model) {
        if (searchMediaId!=null) {
            model.addAttribute("searchedMediasList", mediaService.getById(searchMediaId));
        } else {
            model.addAttribute("searchedMediasList", mediaService.getByAllOptional(searchMediaName, searchMediaFsk, searchMediaGenreId));
        }
        return "home";

    }



            //TODO Display statistics in footer

}
