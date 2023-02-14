package at.ac.fhsalzburg.swd.spring.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.*;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("result", "No media searched.");


        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.getByUsername(currentUserName);
            Integer howManyMoreMediaCanBorrow = userService.howManyMoreMediaCanBorrow(currentUserName);
            model.addAttribute("howManyMoreMediaCanBorrow", howManyMoreMediaCanBorrow);
        }


        if (model.getAttribute("mediaDTO") == null) {
            MediaDTO mediaDTO = new MediaDTO();
            model.addAttribute("mediaDTO", mediaDTO);
        }

        return "home";
    }

    @PostMapping(value = {"/search"})
    public String searchForMedia(
        Model model, RedirectAttributes redirectAttributes,
        @ModelAttribute("mediaDTO") MediaDTO mediaDTO) {

        Map<Class, List<Media>> searchedRawMediasClassesMap = new HashMap<>();
        String result = null;

        if (mediaDTO.getId()!=null) {
            Media searchedMedia = mediaService.getMediaById(mediaDTO.getId());
            if (searchedMedia != null) {
                searchedRawMediasClassesMap.put(searchedMedia.getClass(), Collections.singletonList(searchedMedia));
                result = "None media with ID = " + mediaDTO.getId() + " found";
            }
        } else {
            List<Genre> genres = mediaDTO.getGenres().isEmpty() ? null : mediaDTO.getGenres();
            Collection<? extends Media> searchedRawMedias = mediaService.getByAllOptional(mediaDTO.getName(), mediaDTO.getFsk(), genres);
            searchedRawMediasClassesMap = searchedRawMedias.stream().collect(Collectors.groupingBy(Media::getClass));
        }

        //TODO strategy or visitor pattern (actually modelmapper should just be working like that)
//Since the data needs to be identified into different lists for the display anyway, this way it can be done in one place, which is considered better approach than basing on file structure in our project (and finding all classes names through reflection)

        if (searchedRawMediasClassesMap.size() == 0) result = "None media meeting provided requirements found";
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
        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addFlashAttribute("mediaDTO", mediaDTO);
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

        //Create mediaDTO for search or reuse previous one
        if (model.getAttribute("mediaDTO") == null) {
            MediaDTO mediaDTO = (MediaDTO) model.asMap().get("mediaDTO");
            mediaDTO = mediaDTO!=null? mediaDTO : new MediaDTO();
            model.addAttribute("mediaDTO", mediaDTO);
        }

        //If media was searched, then collections were passed through redirect attributes

        List<BookDTO> searchedBooksCollection = (List<BookDTO>) model.asMap().get("searchedBooksCollection");
        List<AudioDTO> searchedAudiosCollection = (List<AudioDTO>) model.asMap().get("searchedAudiosCollection");
        List<PaperDTO> searchedPapersCollection = (List<PaperDTO>) model.asMap().get("searchedPapersCollection");
        List<MovieDTO> searchedMoviesCollection = (List<MovieDTO>) model.asMap().get("searchedMoviesCollection");

        model.addAttribute("searchedBooksCollection", searchedBooksCollection);
        model.addAttribute("searchedAudiosCollection", searchedAudiosCollection);
        model.addAttribute("searchedPapersCollection", searchedPapersCollection);
        model.addAttribute("searchedMoviesCollection", searchedMoviesCollection);

        //Set final prompt
        String result = (String)model.asMap().get("result")!=null ? (String)model.asMap().get("result") : "No media searched";
        model.addAttribute("result", result);

        return "home";
    }

            //TODO Display statistics in footer

}
