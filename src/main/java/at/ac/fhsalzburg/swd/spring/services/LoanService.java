package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.ids.LoanId;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.LoanRepository;
import at.ac.fhsalzburg.swd.spring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoanService implements LoanServiceInterface {

    private final LoanRepository repo;

    @Value("${myapp.free.days.loan}") // inject secret from application.properties
    private Integer LOAN_DAYS_LIMIT;

    public LoanService(LoanRepository repo) {
        this.repo = repo;
    }

    @Override
    public Boolean isLoanAllowedFor (Copy copy, User user) {
        List<Reservation> mediaReservations = copy.getCopyId().getMedia().getReservations();
        return (copy.isAvailable() && (mediaReservations.isEmpty() || mediaReservations.get(0).getReservationId().getUser() == user));
    }

    @Override
    public Loan loanMedia(Copy copy, User user) {
        if (isLoanAllowedFor(copy, user)) {
            Loan loan = new Loan(new LoanId(copy, user));
            loan = repo.save(loan);

            return loan;
        }
        else {
            return null;
        }
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
    public Boolean isDateExceeded (Loan loan) {
        Date now = new Date();
        long diff = now.getTime() - loan.getDateBorrowed().getTime();
        return LOAN_DAYS_LIMIT < TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public Boolean pay(Loan loan) {
        if (isDateExceeded(loan)) {

        }
        //No need to pay if date wasn't exceeded
        return true;
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
