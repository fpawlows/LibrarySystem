package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.dto.medias.MediaDTO;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import at.ac.fhsalzburg.swd.spring.services.MediaService;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import at.ac.fhsalzburg.swd.spring.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaServiceInterface mediaService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public String getById(
        Model model, @PathVariable Long id) {

        MediaDTO mediaDTO = null;
        Media media = mediaService.getById(id);
        if (media != null) {
             mediaDTO = ObjectMapperUtils.map(media, mediaService.getMediaClasses().get(media.getClass().getSimpleName()).DTO);
            model.addAttribute("mediaDTO", mediaDTO);
            //Not sure if it will work always
            model.addAttribute("searched"+mediaDTO.getClass().getSimpleName()+"sCollection", Arrays.asList(mediaDTO));
        }

        return "media";
    }
}
