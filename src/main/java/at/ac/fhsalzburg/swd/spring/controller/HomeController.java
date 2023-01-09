package at.ac.fhsalzburg.swd.spring.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
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

    //@Autowired
    //StatisticsService statisticsService;

    @RequestMapping( value = {"/home"})
    //TODO change 'home' to '/'
    public String home(
        //@RequestParam(value = "id", required = false) Long id,
        Model model, HttpSession session,
        @CurrentSecurityContext(expression = "authentication") Authentication authentication){
//        @ModelAttribute("searchedMediasList") Collection<MediaDTO> searchedMediasList) {

        List<Genre> allGenres = mediaService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("fskValues", mediaService.getPossibleFskValues());
        model.addAttribute("searchedMediasList", new ArrayList<MediaDTO>());
//              (searchedMediasList==null) ? new ArrayList<MediaDTO>() : searchedMediasList);
//            (searchedMediasList==null || searchedMediasList.isEmpty()) ? statisticsService.getTopBorrowed(5) : searchedMediasList);

        //User modUser = null;
        if (model.getAttribute("mediaDTO") == null) {
            MediaDTO mediaDTO = new MediaDTO();
            model.addAttribute("mediaDTO", mediaDTO);
            //TODO should be changed to session?
            //
        }

        return "home";
    }

    @PostMapping(value={"/search"})
    public String showSearchedMedia(
        Model model,
        @ModelAttribute("mediaDTO") MediaDTO mediaDTO) {

//        Map<? extends Class<? extends Media>, ? extends List<? extends Media>> searchedRawMediasClassesMap = new HashMap<>();
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
                    List<BookDTO> searchedBooksCollection = ObjectMapperUtils.mapAll(entry.getValue(), BookDTO.class);
                    model.addAttribute("searchedBooksCollection", searchedBooksCollection);

                } else {
                    if (entry.getKey() == Audio.class) {
                        List<AudioDTO> searchedAudiosCollection = ObjectMapperUtils.mapAll(entry.getValue(), AudioDTO.class);
                        model.addAttribute("searchedAudiosCollection", searchedAudiosCollection);

                    } else {
                        if (entry.getKey() == Paper.class) {
                            List<PaperDTO> searchedPapersCollection = ObjectMapperUtils.mapAll(entry.getValue(), PaperDTO.class);
                            model.addAttribute("searchedPapersCollection", searchedPapersCollection);

                        } else {
                            if (entry.getKey() == Movie.class) {
                                List<MovieDTO> searchedMoviesCollection = ObjectMapperUtils.mapAll(entry.getValue(), MovieDTO.class);
                                model.addAttribute("searchedMoviesCollection", searchedMoviesCollection);
                            }
                        }
                    }
                }
            }

        return "home";

    }

    //TODO Display statistics in footer


    @RequestMapping( value = {"/search"}, method = RequestMethod.GET)
    //TODO change 'home' to '/'
    public String getSearched(
        //@RequestParam(value = "id", required = false) Long id,
        Model model, HttpSession session,
        @CurrentSecurityContext(expression = "authentication") Authentication authentication,
        @ModelAttribute("searchedMediasList") Collection<MediaDTO> searchedMediasList) {

        List<Genre> allGenres = mediaService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("fskValues", mediaService.getPossibleFskValues());
        model.addAttribute("searchedMediasList",
              (searchedMediasList==null) ? new ArrayList<MediaDTO>() : searchedMediasList);
//            (searchedMediasList==null || searchedMediasList.isEmpty()) ? statisticsService.getTopBorrowed(5) : searchedMediasList);

        //User modUser = null;
        if (model.getAttribute("mediaDTO") == null) {
            MediaDTO mediaDTO = new MediaDTO();
            model.addAttribute("mediaDTO", mediaDTO);
            //TODO should be changed to session?
            //
        }

        return "home";
    }

            //TODO Display statistics in footer

}
