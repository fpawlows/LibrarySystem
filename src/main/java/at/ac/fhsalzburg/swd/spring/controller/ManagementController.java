package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import io.jsonwebtoken.security.InvalidKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

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
@RequestMapping( value = {"/admin"})
public class ManagementController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    MediaServiceInterface mediaService;

    @RequestMapping("/editMedia")
    public String editMedia(
        Model model,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "className", required = false) String className) {

        MediaDTO mediaDTO = null;
        Media modMedia = null;
        List<Genre> allGenres = mediaService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("fskValues", mediaService.getPossibleFskValues());

        if (id != null) {
            modMedia = mediaService.getById(id);
        }

        if (modMedia != null) {
            mediaDTO = ObjectMapperUtils.map(modMedia, MediaDTO.class);
        } else {
            if (className == null) {
                List<String> mediaClassesNames = mediaService.getMediaClasses().keySet().stream().toList();
                model.addAttribute("mediaClassesNames", mediaClassesNames);
            } else {
                try {
                    //TODO Maybe here problems cause no constructors
                    mediaDTO = mediaService.getMediaDTOClasses().get(className).getConstructor().newInstance();
                } catch (Exception e) {
                    throw new InvalidKeyException("Media doesn't fit to provided name");
                }
            }
        }

        if (mediaDTO instanceof BookDTO) {
            model.addAttribute("mediaDTO", (BookDTO) mediaDTO);
        } else {

            if (mediaDTO instanceof AudioDTO) {
                model.addAttribute("mediaDTO", (AudioDTO) mediaDTO);
            } else {

                if (mediaDTO instanceof PaperDTO) {
                    model.addAttribute("mediaDTO", (PaperDTO) mediaDTO);
                } else {

                    if (mediaDTO instanceof MovieDTO) {
                        model.addAttribute("mediaDTO", (MovieDTO) mediaDTO);
                    }
                }
            }
        }
        return "editMedia";
    }
}
