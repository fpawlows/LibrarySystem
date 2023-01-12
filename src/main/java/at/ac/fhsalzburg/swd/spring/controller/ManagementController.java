package at.ac.fhsalzburg.swd.spring.controller;

import io.jsonwebtoken.security.InvalidKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.persistence.EntityManager;

import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.medias.*;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("editedStatus", (String) model.asMap().get("editedStatus"));

        if (id != null) {
            modMedia = mediaService.getById(id);
        }

        if (modMedia != null) {
            //TODO split based on types
            mediaDTO = ObjectMapperUtils.map(modMedia, mediaService.getMediaDTOClasses().get(modMedia.getClassName()));
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
            model.addAttribute("bookDTO", (BookDTO) mediaDTO);
        } else {

            if (mediaDTO instanceof AudioDTO) {
                model.addAttribute("audioDTO", (AudioDTO) mediaDTO);
            } else {

                if (mediaDTO instanceof PaperDTO) {
                    model.addAttribute("paperDTO", (PaperDTO) mediaDTO);
                } else {

                    if (mediaDTO instanceof MovieDTO) {
                        model.addAttribute("movieDTO", (MovieDTO) mediaDTO);
                    }
                }
            }
        }
        return "editMedia";
    }


    @PostMapping("/editMedia")
    public String saveMedia(Model model, RedirectAttributes redirectAttributes,
                            @ModelAttribute("bookDTO") BookDTO bookDTO,
                            @ModelAttribute("audioDTO") AudioDTO audioDTO,
                            @ModelAttribute("paperDTO") PaperDTO paperDTO,
                            @ModelAttribute("movieDTO") MovieDTO movieDTO,
                            @RequestParam(value = "className", required = false) String className)
    {
        Media media = null;
        if (bookDTO.getClassName().equals(className)) media = ObjectMapperUtils.map(bookDTO, mediaService.getMediaClasses().get(bookDTO.getClassName()));
        if (audioDTO.getClassName().equals(className)) media = ObjectMapperUtils.map(audioDTO, mediaService.getMediaClasses().get(audioDTO.getClassName()));
        if (paperDTO.getClassName().equals(className)) media = ObjectMapperUtils.map(paperDTO, mediaService.getMediaClasses().get(paperDTO.getClassName()));
        if (movieDTO.getClassName().equals(className)) media = ObjectMapperUtils.map(movieDTO, mediaService.getMediaClasses().get(movieDTO.getClassName()));
        //Media or ? extends
        if (!entityManager.contains(media)) {
            mediaService.addMedia(media);
        }

        redirectAttributes.addFlashAttribute("editedStatus", "Media edited successfully!");
        return "redirect:/admin/editMedia";
    }

    @DeleteMapping("/deleteMedia")
    public String deleteMedia(Model model,
                       @RequestParam(value = "id", required = false) Long id) {
        mediaService.deleteById(id);
   return "redirect:/admin/editMedia";
    }

}

