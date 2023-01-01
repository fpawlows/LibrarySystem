package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Iterable findAll() {
        return mediaRepository.findAll();
    }

    @GetMapping("/title/{mediaTitle}")
    public List findByTitle(@PathVariable String mediaTitle) {
        return mediaRepository.findByName(mediaTitle);
    }
}
