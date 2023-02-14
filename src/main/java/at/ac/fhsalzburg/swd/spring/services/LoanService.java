package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.LoanRepository;
import at.ac.fhsalzburg.swd.spring.repository.ReservationRepository;
import javassist.compiler.ast.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import java.sql.Timestamp;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class LoanService implements LoanServiceInterface {

    protected class FutureIdPair implements Comparable<FutureIdPair> {
        public Long id;
        public Future future;
        public Timestamp initialized;

        public FutureIdPair(Long id, ScheduledFuture<?> schedule, Timestamp initialized) {
            this.future = schedule;
            this.id = id;
            this.initialized = initialized;
        }

        @Override
        public int compareTo(FutureIdPair anotherPair) {
            return (int) (this.initialized.getTime() - anotherPair.initialized.getTime());
        }
    }

    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final MediaServiceInterface mediaService;
    private final UserServiceInterface userService;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//TODO shutdown when application exits
    private Collection<FutureIdPair> futureReservations;
    private Collection<FutureIdPair> futureLoans;


    @Value("${myapp.free.days.loan}") // inject secret from application.properties
    private Integer LOAN_DAYS_LIMIT;

    public LoanService(LoanRepository loanRepository, ReservationRepository reservationRepository, MediaServiceInterface mediaService, UserServiceInterface userService)
    {
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
        this.mediaService = mediaService;
        this.userService = userService;
        futureLoans = new ArrayList<FutureIdPair>();
        futureReservations = new ArrayList<FutureIdPair>();
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
        Integer howManyMoreMediaCanBorrow = userService.howManyMoreMediaCanBorrow(user.getUsername());
        return howManyMoreMediaCanBorrow > 0 && isMediaAllowedFor(media, user)
            && (mediaReservations.isEmpty() || findNextInQueue(media) == user);
    }

    @Override
    public Boolean isCopyAllowedFor (Copy copy, User user) throws BadAttributeValueExpException {
        return (copy.isAvailable() && canBorrowNextCopy(copy.getCopyId().getMedia(), user));
    }

    @Override
    public Loan loanMedia(Copy copy, User user, Timestamp dateBorrowed, Loan.loanState state) throws BadAttributeValueExpException {
        if (isCopyAllowedFor(copy, user)) {
            Loan loan = createLoan(copy, user, dateBorrowed, state);
            return loan;
        }
            return null;
    }


    @Override
    public synchronized Loan loanMedia(Media media, User user, Timestamp dateBorrowed, Loan.loanState state) throws BadAttributeValueExpException {
        if (canBorrowNextCopy(media, user)) {
            Copy copy = getAvailableCopies(media).iterator().next();
                Loan loan = createLoan(copy, user, dateBorrowed, state);
                mediaService.setAvailibility(copy.getCopyId(), false);
                return loan;
        }
        return null;
    }


    @Override
    public synchronized Loan rollbackLoan (Loan loan) {
        loan.setState(Loan.loanState.Cancelled);
        loanRepository.save(loan);
        return loan;
    }


        @Override
        public synchronized Loan createLoan(Copy copy, User user, Timestamp dateBorrowed, Loan.loanState state) throws BadAttributeValueExpException {
        dateBorrowed = dateBorrowed == null ? new Timestamp(System.currentTimeMillis()) : dateBorrowed;
        state = state == null ? Loan.loanState.waitingForPickUp : state;

        Loan loan = new Loan(null, copy, user, dateBorrowed, state);
        loan = loanRepository.save(loan);


            Loan finalLoan = loan;
            Runnable task = () -> {
            rollbackLoan(finalLoan);
            };

        futureLoans.add(new FutureIdPair(loan.getId(), executor.schedule(task, 5, TimeUnit.SECONDS),  new Timestamp(System.currentTimeMillis())));
        //from properties
        return loan;
    }

    @Override
    public synchronized Loan startLoan(Loan loan) {
        List<FutureIdPair> sortedLoansFutures = futureLoans.stream().filter(p -> p.id == loan.getId()).sorted().collect(Collectors.toList());
        if (sortedLoansFutures.iterator().hasNext()) {
            FutureIdPair futureIdPair = sortedLoansFutures.iterator().next();

            if (futureIdPair.future.isDone()) {
                return null;
            }
            loan.setState(Loan.loanState.Borrowed);
            futureIdPair.future.cancel(true);
            loanRepository.save(loan);
            return loan;
        }
        return null ;
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
        List<Reservation> mediaReservations = media.getReservations();
        return mediaReservations.get(0).getUser();
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
