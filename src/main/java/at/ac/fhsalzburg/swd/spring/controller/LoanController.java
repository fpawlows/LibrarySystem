package at.ac.fhsalzburg.swd.spring.controller;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.services.LoanServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.BadAttributeValueExpException;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoanController {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private MediaServiceInterface mediaService;

    @Autowired
    private LoanServiceInterface loanService;


    @RequestMapping(value = "/reserve")
    public String reserveMedia(
        HttpServletRequest request, RedirectAttributes redirectAttributes, Model model,
        @RequestParam(value = "id") Long mediaId, @CurrentSecurityContext(expression = "authentication") Authentication authentication){

        String topAlert = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.getByUsername(currentUserName);
            Media media = mediaService.getMediaById(mediaId);
            try {
                if (loanService.reserveMedia(media, user)) {
                    topAlert = "Media reserved. Go to your profile, when you wish to Loan it.";
                } else {
                    topAlert = "Couldn't reserve media";
                }
            }
            catch (BadAttributeValueExpException e) {
                e.printStackTrace();
            }

            redirectAttributes.addFlashAttribute("topAlert", topAlert);
        } else {
            throw new AuthenticationCredentialsNotFoundException("No logged user");
        }

        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping("/reservation/cancel")
    public String cancelReservation(
        HttpServletRequest request,
        Model model, @RequestParam(value = "id") Long reservationId){

        loanService.cancelReservation(reservationId);

        return "redirect:" + request.getHeader("Referer");
    }


    @RequestMapping(value = "/loan")
    public String loanMedia(
        HttpServletRequest request, RedirectAttributes redirectAttributes, Model model,
        @RequestParam(value = "mediaId", required = false) Long mediaId,
        @RequestParam(value = "copyId", required = false) CopyId copyId,
        @CurrentSecurityContext(expression = "authentication") Authentication authentication){

        String topAlert = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.getByUsername(currentUserName);

            Copy copy = null;
            if (copyId != null)
            {
                copy = mediaService.getCopyById(copyId);
            }

            Media media = null;
            if (mediaId!=null)
            {
                media = mediaService.getMediaById(mediaId);
            }
            try {
                Loan loan = null;
                if (media != null) {
                    loan = loanService.loanMedia(media, user, null, null);
                } else {
                    if (copy != null) {
                        loan = loanService.loanMedia(copy, user, null, null);
                    }
                    else {
                        throw new IllegalArgumentException("Didn't receive any of ids to loan");
                    }
                }
                if ( loan != null) {
                    topAlert = "Media is waiting to be picked up. Your CopyNr: " + loan.getCopy().getCopyId().getCopyNr();
                    redirectAttributes.addFlashAttribute("topAlert", topAlert);
                    return "redirect:/media/" + loan.getCopy().getCopyId().getMedia().getId();
                } else {
                    topAlert = "Couldn't loan media";
                    redirectAttributes.addFlashAttribute("topAlert", topAlert);
                }
            }
            catch (BadAttributeValueExpException e) {
                e.printStackTrace();
            }

        } else {
            throw new AuthenticationCredentialsNotFoundException("No logged user");
        }

//        redirectAttributes = forwardSearchedLists(model, redirectAttributes);

        return "redirect:/home/";
    }

    @RequestMapping("/loans/pickUp")
    public String pickUp(
        Model model, RedirectAttributes redirectAttributes,
        HttpServletRequest request,
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @RequestParam(value = "id") Long loanId) {

        String topAlert = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.getByUsername(currentUserName);
            Loan loan = loanService.getLoanById(loanId);
            if (loan != null) {
                if (loanService.startLoan(loan)!=null) {
                    topAlert = "Loaned succesfully!";
                } else {
                    topAlert = "Couldn't loan";
                }
            }
        }
        redirectAttributes.addFlashAttribute("topAlert", topAlert);
    return "redirect:" + request.getHeader("Referer");
    }

}
