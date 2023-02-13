package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;

import javax.management.BadAttributeValueExpException;
import java.util.Date;

public interface LoanServiceInterface {
    public abstract Loan loanMedia(Copy copy, User user, Date dateBorrowed, Loan.loanState state) throws BadAttributeValueExpException;
    public abstract Loan loanMedia(Media media, User user, Date dateBorrowed, Loan.loanState state) throws BadAttributeValueExpException;

    public abstract Boolean returnMedia (Long loanId);
    public abstract Boolean isMediaAllowedFor (Media media, User user) throws BadAttributeValueExpException;
    public abstract Boolean isCopyAllowedFor (Copy copy, User user) throws BadAttributeValueExpException;
    public abstract Iterable<Copy> getAvailableCopies (Media media);
    public abstract Boolean isDateExceeded (Loan loan);
    public abstract Boolean pay(Loan loan);
    public abstract Boolean reserveMedia (Media media, User user) throws BadAttributeValueExpException;
    public abstract void cancelReservation (Long reservationId);
    public abstract User findNextInQueue (Media media);


    public abstract Loan getLoanById(Long Id);
    public abstract Reservation getReservationById(Long Id);

}

