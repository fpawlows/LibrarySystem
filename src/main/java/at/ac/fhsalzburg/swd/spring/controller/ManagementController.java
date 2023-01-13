package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.dto.CopyDTO;
import at.ac.fhsalzburg.swd.spring.model.Compartment;
import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import io.jsonwebtoken.security.InvalidKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.naming.NamingException;
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
            modMedia = mediaService.getMediaById(id);
        }

        if (modMedia != null) {
            //TODO split based on types
            mediaDTO = ObjectMapperUtils.map(modMedia, mediaService.getMediaClasses().get(modMedia.getClass().getSimpleName()).DTO);
        } else {
            if (className == null) {
                List<String> mediaClassesNames = mediaService.getMediaClasses().keySet().stream().toList();
                model.addAttribute("mediaClassesNames", mediaClassesNames);
            } else {
                try {
                    if (className.substring(className.length() - 3).equals("DTO")) {
                        className = className.substring(0, className.length() - 3);
                    }
                    //TODO Maybe here problems cause no constructors
                    mediaDTO = mediaService.getMediaClasses().get(className).DTO.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new InvalidKeyException("Class name not registered in media service");
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
                            @RequestParam(value = "className", required = false) String className) throws NamingException {
        Media media = null;

        if (bookDTO.getClass().getSimpleName().equals(className))
            media = ObjectMapperUtils.map(bookDTO, mediaService.getMediaClasses().get(
                className.substring(className.length() - 3).equals("DTO") ? className.substring(0, className.length() - 3) : className)
                .Business);
        if (audioDTO.getClass().getSimpleName().equals(className))
            media = ObjectMapperUtils.map(audioDTO, mediaService.getMediaClasses().get(
                className.substring(className.length() - 3).equals("DTO") ? className.substring(0, className.length() - 3) : className)
            .Business);
        if (paperDTO.getClass().getSimpleName().equals(className))
            media = ObjectMapperUtils.map(paperDTO, mediaService.getMediaClasses().get(
                className.substring(className.length() - 3).equals("DTO") ? className.substring(0, className.length() - 3) : className)
                .Business);
        if (movieDTO.getClass().getSimpleName().equals(className))
            media = ObjectMapperUtils.map(movieDTO, mediaService.getMediaClasses().get(
                className.substring(className.length() - 3).equals("DTO") ? className.substring(0, className.length() - 3) : className)
                .Business);
        //Media or ? extends
        if (media==null) {
            throw new NamingException("Probably wrong DTO objects names");
        }
        if (!entityManager.contains(media)) {
            mediaService.addMedia(media);
        }

        redirectAttributes.addFlashAttribute("editedStatus", "Media edited successfully!");
        return "redirect:/admin/editMedia";
    }

    @RequestMapping("/deleteMedia")
    public String deleteMedia(Model model,
                              @RequestParam(value = "id", required = false) Long id) {
        mediaService.deleteById(id);
        return "redirect:/home/search";
    }


    @GetMapping("/editCopy")
    public String addCopy(Model model, RedirectAttributes redirectAttributes,
                      @RequestParam(value = "mediaId", required = false) Long mediaId,
                    @RequestParam(value = "copyId", required = false) CopyId copyId) {
        Copy copy = null;
        Media media = null;
        MediaDTO mediaDTO = null;

        if (copyId == null) {
            if(mediaId != null )  {
                media = mediaService.getMediaById(mediaId);
                if (media != null) {
                    mediaDTO = ObjectMapperUtils.map(media, mediaService.getMediaClasses().get(media.getClass().getSimpleName()).DTO);
                    copy = new Copy();
                    copy.setCopyId(new CopyId(null, media));
                    copy.setCompartment(new Compartment(new CompartmentId(null,null), null));
                }
            }
        } else {
            copy = mediaService.getCopyById(copyId);
        }
            redirectAttributes.addFlashAttribute("copy", copy);
            redirectAttributes.addFlashAttribute("editable", true);
            return "redirect:/media/" + mediaId;
    }

    @PostMapping("/editCopy")
    public String addCopy(Model model, RedirectAttributes redirectAttributes,
                          @ModelAttribute("copy") Copy copy) {
        if (copy != null) {
            Compartment compartment = mediaService.getCompartmentById(copy.getCompartment().getCompartmentId());
            if (compartment!=null) {
                redirectAttributes.addFlashAttribute("message", "No compartment with chosen Id");
            }
            else {
                copy.setCompartment(compartment);
                if (mediaService.addCopy(copy)) {
                    redirectAttributes.addFlashAttribute("message", "Couldn't add copy");
                }
                redirectAttributes.addFlashAttribute("message", "Copy successfully added");
            }

        }
        return "redirect:/media/" + copy.getCopyId().getMedia().getId();
    }
}


