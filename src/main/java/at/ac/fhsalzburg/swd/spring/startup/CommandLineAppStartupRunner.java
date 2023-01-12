package at.ac.fhsalzburg.swd.spring.startup;



import java.util.ArrayList;
import java.util.Date;

import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.services.OrderServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.ProductServiceInterface;

@Profile("!test")
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    UserServiceInterface userService;

    @Autowired
    ProductServiceInterface productService;

    @Autowired
    OrderServiceInterface orderService;

    @Autowired
    MediaServiceInterface mediaService;


    // Initialize System with preset accounts and stocks
    @Override
    @Transactional // this method runs within one database transaction; performing a commit at the
                   // end
    public void run(String... args) throws Exception {

    	if (userService.getByUsername("admin")!=null) return; // data already exists -> return

    	userService.addUser("admin", "Administrator", "admin@work.org", "123", new Date(), "admin","ADMIN");

        productService.addProduct("first product", 3.30f);
        User user = userService.getAll().iterator().next();
        user.setCredit(100l);
        user = userService.getByUsername("admin");
        orderService.addOrder(new Date(), user, productService.getAll());


        final Integer N_MEDIAS = 40;
        final Integer N_FIRST_MEDIAS_FOR_QUEUES = 3;

        //TODO change below to setting using services
        ArrayList<ArrayList<Reservation>> givenReservations = new ArrayList<>();
        ArrayList<Media> givenMedias = new ArrayList<Media>();
        ArrayList<User> givenUsers = new ArrayList<User>();
        ArrayList<Genre> givenGenres= new ArrayList<Genre>();

        for (int i = 0; i < N_FIRST_MEDIAS_FOR_QUEUES; i++) {
            givenReservations.add(new ArrayList<Reservation>());
        }

        for (int i = 0; i < N_MEDIAS; i++) {
            givenGenres.add(new Genre("asdsad" + i));
            mediaService.addGenre(givenGenres.get(i));
            givenMedias.add(new Book("Name" + i, null, null, i, Integer.toString(i)));
            givenMedias.get(i).setGenres(givenGenres);
            //Reservation reservation = new Reservation(new ReservationId(givenMedias.get(i % N_FIRST_MEDIAS_FOR_QUEUES), givenUsers.get(i)), (int) Math.floor((i + N_FIRST_MEDIAS_FOR_QUEUES) / N_FIRST_MEDIAS_FOR_QUEUES));
            //givenReservations.get(i % N_FIRST_MEDIAS_FOR_QUEUES).add(reservation);

            mediaService.addMedia(givenMedias.get(i));

            //reservationService.persist(reservation);
        }
    }
}
