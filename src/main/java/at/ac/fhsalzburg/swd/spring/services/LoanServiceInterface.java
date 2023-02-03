package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;

import java.util.Date;

public interface LoanServiceInterface {
    public abstract Loan loanMedia(Copy copy, User user, Date dateBorrowed, Loan.loanState state);

    public abstract Boolean returnMedia (Long loanId);
    public abstract Boolean isLoanAllowedFor (Copy copy, User user);
    public abstract Iterable<Copy> getAvailableCopies (Media media);
    public abstract Boolean isDateExceeded (Loan loan);
    public abstract Boolean pay(Loan loan);
    public abstract Reservation reserveMedia (Media media);
    public abstract void cancelReservation (Reservation reservation);
    public abstract User findNextInQueue (Media media);
}
