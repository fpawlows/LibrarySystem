package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.dto.medias.AudioDTO;
import at.ac.fhsalzburg.swd.spring.dto.medias.BookDTO;
import at.ac.fhsalzburg.swd.spring.dto.medias.MovieDTO;
import at.ac.fhsalzburg.swd.spring.dto.medias.PaperDTO;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public abstract class BaseController {

    protected static RedirectAttributes forwardSearchedLists(Model model, RedirectAttributes redirectAttributes) {
        List<BookDTO> searchedBooksCollection = (List<BookDTO>) model.asMap().get("searchedBooksCollection");
        List<AudioDTO> searchedAudiosCollection = (List<AudioDTO>) model.asMap().get("searchedAudiosCollection");
        List<PaperDTO> searchedPapersCollection = (List<PaperDTO>) model.asMap().get("searchedPapersCollection");
        List<MovieDTO> searchedMoviesCollection = (List<MovieDTO>) model.asMap().get("searchedMoviesCollection");

        redirectAttributes.addFlashAttribute("searchedBooksCollection", searchedBooksCollection);
        redirectAttributes.addFlashAttribute("searchedAudiosCollection", searchedAudiosCollection);
        redirectAttributes.addFlashAttribute("searchedPapersCollection", searchedPapersCollection);
        redirectAttributes.addFlashAttribute("searchedMoviesCollection", searchedMoviesCollection);
        return redirectAttributes;
    }
}
