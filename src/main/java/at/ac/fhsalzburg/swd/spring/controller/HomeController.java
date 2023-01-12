package at.ac.fhsalzburg.swd.spring.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.medias.*;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // marks the class as a web controller, capable of handling the HTTP requests. Spring
// will look at the methods of the class marked with the @Controller annotation and
// establish the routing table to know which methods serve which endpoints.
@RequestMapping( value = {"/home"})
public class HomeController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private MediaServiceInterface mediaService;


    //TODO take this beginning to the separeate method call

    @RequestMapping
    //TODO change 'home' to '/'
    public String home(
        Model model, HttpSession session,
        @CurrentSecurityContext(expression = "authentication") Authentication authentication){

        List<Genre> allGenres = mediaService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("fskValues", mediaService.getPossibleFskValues());

        //User modUser = null;
        if (model.getAttribute("mediaDTO") == null) {
            MediaDTO mediaDTO = new MediaDTO();
            model.addAttribute("mediaDTO", mediaDTO);
            //TODO should be changed to session?
            //
        }

        return "home";
    }

    @PostMapping(value = {"/search"})
    public String searchForMedia(
        Model model, RedirectAttributes redirectAttributes,
        @ModelAttribute("mediaDTO") MediaDTO mediaDTO) {

        Map<Class, List<Media>> searchedRawMediasClassesMap = new HashMap<>();

        if (mediaDTO.getId()!=null) {
            Media searchedMedia = mediaService.getById(mediaDTO.getId());
            searchedRawMediasClassesMap.put(searchedMedia.getClass(), Collections.singletonList(searchedMedia));
        } else {
            List<Genre> genres = mediaDTO.getGenres().isEmpty() ? null : mediaDTO.getGenres();
            Collection<? extends Media> searchedRawMedias = mediaService.getByAllOptional(mediaDTO.getName(), mediaDTO.getFsk(), genres);
            searchedRawMediasClassesMap = searchedRawMedias.stream().collect(Collectors.groupingBy(Media::getClass));
        }

        //TODO strategy or visitor pattern (actually modelmapper should just be working like that)
//Since the data needs to be identified into different lists for the display anyway, this way it can be done in one place, which is considered better approach than basing on file structure in our project (and finding all classes names through reflection)
            for (var entry: searchedRawMediasClassesMap.entrySet()) {
                if (entry.getKey() == Book.class) {
                    Media book = entry.getValue().get(0);
                    List<BookDTO> searchedBooksCollection = ObjectMapperUtils.mapAll(entry.getValue(), BookDTO.class);
                    redirectAttributes.addFlashAttribute("searchedBooksCollection", searchedBooksCollection);

                } else {
                    if (entry.getKey() == Audio.class) {
                        List<AudioDTO> searchedAudiosCollection = ObjectMapperUtils.mapAll(entry.getValue(), AudioDTO.class);
                        redirectAttributes.addFlashAttribute("searchedAudiosCollection", searchedAudiosCollection);

                    } else {
                        if (entry.getKey() == Paper.class) {
                            List<PaperDTO> searchedPapersCollection = ObjectMapperUtils.mapAll(entry.getValue(), PaperDTO.class);
                            redirectAttributes.addFlashAttribute("searchedPapersCollection", searchedPapersCollection);

                        } else {
                            if (entry.getKey() == Movie.class) {
                                List<MovieDTO> searchedMoviesCollection = ObjectMapperUtils.mapAll(entry.getValue(), MovieDTO.class);
                                redirectAttributes.addFlashAttribute("searchedMoviesCollection", searchedMoviesCollection);
                            }
                        }
                    }
                }
            }

        return "redirect:/home/search";

    }

    //TODO Display statistics in footer


    @GetMapping(value = {"/search"})
    public String getSearched(
        Model model, HttpSession session,
        @CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        List<Genre> allGenres = mediaService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("fskValues", mediaService.getPossibleFskValues());
        //User modUser = null;
        if (model.getAttribute("mediaDTO") == null) {
            MediaDTO mediaDTO = new MediaDTO();
            model.addAttribute("mediaDTO", mediaDTO);
        }

        List<BookDTO> searchedBooksCollection = (List<BookDTO>) model.asMap().get("searchedBooksCollection");
        List<BookDTO> searchedAudiosCollection = (List<BookDTO>) model.asMap().get("searchedAudiosCollection");
        List<BookDTO> searchedPapersCollection = (List<BookDTO>) model.asMap().get("searchedPapersCollection");
        List<BookDTO> searchedMoviesCollection = (List<BookDTO>) model.asMap().get("searchedMoviesCollection");
        model.addAttribute("searchedBooksCollection", searchedBooksCollection);
        model.addAttribute("searchedAudiosCollection", searchedAudiosCollection);
        model.addAttribute("searchedPapersCollection", searchedPapersCollection);
        model.addAttribute("searchedMoviesCollection", searchedMoviesCollection);

        return "home";
    }

            //TODO Display statistics in footer

}
