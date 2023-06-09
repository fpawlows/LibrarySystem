package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
import at.ac.fhsalzburg.swd.spring.model.Compartment;
import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import at.ac.fhsalzburg.swd.spring.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaServiceInterface mediaService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public String getById(
        Model model, @PathVariable Long id,
        @CurrentSecurityContext(expression = "authentication") Authentication authentication) {


        String message = model.asMap().get("message") != null ? (String) model.asMap().get("message") : "No Copies found";
        MediaDTO mediaDTO = null;
        Media media = mediaService.getMediaById(id);
        List<Compartment> compartmets = mediaService.getAllCompartments();
        model.addAttribute("allCompartments", compartmets);

        if (media != null) {
            mediaDTO = ObjectMapperUtils.map(media, mediaService.getMediaClasses().get(media.getClass().getSimpleName()).DTO);
            //TODO check if we can do that instead of that derived class based what if long pyramid
        }
        else {
            message = message = "There is no media with ID: " + id;
        }
        model.addAttribute("mediaDTO", mediaDTO);


        Boolean editable = (Boolean) (model.asMap().get("editable")!=null ? model.asMap().get("editable") : false);
        Copy copy = (Copy) model.asMap().get("copy");
        model.addAttribute("message", message);
        model.addAttribute("editable", editable);
        model.addAttribute("copy", copy);
        return "media";
    }
}
