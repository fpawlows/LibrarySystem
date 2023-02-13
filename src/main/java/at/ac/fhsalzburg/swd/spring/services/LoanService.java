package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.LoanRepository;
import at.ac.fhsalzburg.swd.spring.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import java.sql.Timestamp;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class LoanService implements LoanServiceInterface {

    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final MediaServiceInterface mediaService;


    @Value("${myapp.free.days.loan}") // inject secret from application.properties
    private Integer LOAN_DAYS_LIMIT;

    public LoanService(LoanRepository loanRepository, ReservationRepository reservationRepository, MediaServiceInterface mediaService)
    {
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
        this.mediaService = mediaService;
    }


    @Override
    public Boolean isMediaAllowedFor (Media media, User user) throws BadAttributeValueExpException {
        Date today = new Date();
        if (user.getBirthDate() == null) {
            throw new BadAttributeValueExpException("User has no birthdate");
        }

        return (media.getFsk() <= Period.between(user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears());
    }

    @Override
    public Boolean canBorrowNextCopy (Media media, User user) throws BadAttributeValueExpException {
        List<Reservation> mediaReservations = media.getReservations();
        return (mediaReservations.isEmpty() || mediaReservations.get(0).getUser() == user) && isMediaAllowedFor(media, user);
    }

    @Override
    public Boolean isCopyAllowedFor (Copy copy, User user) throws BadAttributeValueExpException {
        return (copy.isAvailable() && canBorrowNextCopy(copy.getCopyId().getMedia(), user));
    }

    @Override
    public Loan loanMedia(Copy copy, User user, Timestamp dateBorrowed, Loan.loanState state) throws BadAttributeValueExpException {
        if (isCopyAllowedFor(copy, user)) {
            dateBorrowed = dateBorrowed==null? new Timestamp(System.currentTimeMillis()) : dateBorrowed;
            state = state==null? Loan.loanState.waitingForPickUp : state;

            Loan loan = new Loan(null, copy, user, dateBorrowed, state);
            loan = loanRepository.save(loan);
            return loan;
        }
            return null;
    }

    @Override
    public synchronized Loan loanMedia(Media media, User user, Timestamp dateBorrowed, Loan.loanState state) throws BadAttributeValueExpException {
        if (canBorrowNextCopy(media, user)) {
            dateBorrowed = dateBorrowed == null ? new Timestamp(System.currentTimeMillis()) : dateBorrowed;
            state = state == null ? Loan.loanState.waitingForPickUp : state;

                Copy copy = getAvailableCopies(media).iterator().next();
                Loan loan = new Loan(null, copy, user, dateBorrowed, state);
                loanRepository.save(loan);
                mediaService.setAvailibility(copy.getCopyId(), false);
                return loan;
        }
        return null;
    }

    @Override
    public Boolean returnMedia(Long loanId) {
        return null;
    }

    @Override
    public Iterable<Copy> getAvailableCopies(Media media) {
        List<Copy> availableCopies = media.getCopies().stream().filter(p -> p.isAvailable()).collect(Collectors.toList());
        return availableCopies;
    }

    @Override
    public Boolean isDateExceeded (Loan loan) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long diff = now.getTime() - loan.getTimestampBorrowed().getTime();
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
    public Reservation allowLoan(Reservation reservation) {
        reservation.setState(Reservation.reservationState.loanAllowed);
        reservation.setPriorityStartTimestamp(new Timestamp(System.currentTimeMillis()));
//start timer
        return reservation;
    }

    //availability lock_guard
    @Override
    public Boolean reserveMedia(Media media, User user) throws BadAttributeValueExpException {
            if (isMediaAllowedFor(media, user)) {
                synchronized (this) {
                    if (!media.getReservations().stream().anyMatch(res -> res.getUser().getUsername() == user.getUsername())) {
                        Reservation.reservationState state = Reservation.reservationState.inQueue;
                        Reservation reservation = new Reservation(null, mediaService.getNextQueueNumber(media), new Timestamp(System.currentTimeMillis()), null, state, media, user);
                        if (media.getReservations().size()==0) {
                            reservation = allowLoan(reservation);
                        }
                        reservationRepository.save(reservation);
                        return true;
                    }
                }
            }
            return false;
    }

    @Override
    public Boolean cancelReservation(Long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (!optionalReservation.isEmpty()) {
            Reservation reservation = optionalReservation.get();
            reservation.setState(Reservation.reservationState.canceled);
        }
        return false;
    }

    @Override
    synchronized
    public Boolean loanFromReservation(Reservation reservation) throws BadAttributeValueExpException {
        Loan loan = loanMedia(reservation.getMedia(), reservation.getUser(), new Timestamp(System.currentTimeMillis()), Loan.loanState.waitingForPickUp);
        reservation.setState(Reservation.reservationState.loanCreated);
        reservationRepository.save(reservation);
        loanRepository.save(loan);

        return false;
    }

    @Override
    public User findNextInQueue(Media media) {
        return null;
    }

    @Override
    public Iterable<Loan> getActiveLoans(User user) {
        return null;
    }

    @Override
    public Iterable<Reservation> getActiveReservations(User user) {
        return null;
    }


    @Override
    public Loan getLoanById(Long Id) {
        Optional<Loan> loan = loanRepository.findById(Id);
        return loan.isEmpty()? null : loan.get();
    }

    @Override
    public Reservation getReservationById(Long Id) {
        Optional<Reservation> reservation = reservationRepository.findById(Id);
        return reservation.isEmpty()? null : reservation.get();
    }
}
