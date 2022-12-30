package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;

public interface LoanServiceInterface {
    public abstract Loan loanMedia (Copy copy, User user); //TODO concurrent - check .available setAvailable
    public abstract Boolean returnMedia (LoanId loanId);
    public abstract Iterable<Copy> getAvailableCopies (Media media);
    public abstract Boolean pay(Loan loan);
    public abstract Reservation reserveMedia (Media media);
    public abstract void cancelReservation (Reservation reservation);
    public abstract User findNextInQueue (Media media);
}
