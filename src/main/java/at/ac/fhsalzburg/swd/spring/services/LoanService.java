package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.stereotype.Service;

@Service
public class LoanService implements LoanServiceInterface {


    @Override
    public Loan loanMedia(Copy copy, User user) {
        return null;
    }

    @Override
    public Boolean returnMedia(LoanId loanId) {
        return null;
    }

    @Override
    public Iterable<Copy> getAvailableCopies(Media media) {
        return null;
    }

    @Override
    public Boolean pay(Loan loan) {
        return null;
    }

    @Override
    public Reservation reserveMedia(Media media) {
        return null;
    }

    @Override
    public void cancelReservation(Reservation reservation) {

    }

    @Override
    public User findNextInQueue(Media media) {
        return null;
    }
}
