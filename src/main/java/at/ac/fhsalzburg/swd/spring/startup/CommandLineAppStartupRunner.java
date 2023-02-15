package at.ac.fhsalzburg.swd.spring.startup;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.random.RandomGenerator;

import at.ac.fhsalzburg.swd.spring.model.*;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;
import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;
import at.ac.fhsalzburg.swd.spring.model.medias.Audio;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.model.medias.Movie;
import at.ac.fhsalzburg.swd.spring.model.medias.Paper;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.OrderServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.ProductServiceInterface;

import javax.persistence.PersistenceException;

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


        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -100);

    	if (userService.getByUsername("admin")!=null) return; // data already exists -> return
    	userService.addUser("admin", "Administrator", "admin@work.org", "123", c.getTime(), "admin","ADMIN");

        if (userService.getByUsername("Max")!=null) return; // data already exists -> return
    	userService.addUser("Max", "Mustermann", "max@work.org", "123", new Date(), "max","USER");

        productService.addProduct("first product", 3.30f);
        User user = userService.getAll().iterator().next();
        user.setCredit(100l);
        user = userService.getByUsername("admin");
        orderService.addOrder(new Date(), user, productService.getAll());


        final Integer N_MEDIAS = 100;
        final Integer N_MEDIAS_P_TYPE = N_MEDIAS/4;
        final Integer N_FIRST_MEDIAS_FOR_QUEUES = 3;

        //TODO change below to setting using services
        ArrayList<ArrayList<Reservation>> givenReservations = new ArrayList<>();
        ArrayList<Media> givenMedias = new ArrayList<Media>();
        ArrayList<User> givenUsers = new ArrayList<User>();
        ArrayList<Genre> givenGenres= new ArrayList<Genre>();

        for (int i = 0; i < N_FIRST_MEDIAS_FOR_QUEUES; i++) {
            givenReservations.add(new ArrayList<Reservation>());
        }

        String name = "Library";

        for (int i = 0; i < N_FIRST_MEDIAS_FOR_QUEUES; i++) {
            givenReservations.add(new ArrayList<Reservation>());
        }

        givenGenres.add(new Genre("Documentation"));
        givenGenres.add(new Genre("Horror"));
        givenGenres.add(new Genre("Family"));
        givenGenres.add(new Genre("Thriller"));
        givenGenres.add(new Genre("Romantic"));
        givenGenres.add(new Genre("Dokumentation"));

        for (var g : givenGenres)
        	mediaService.addGenre(g);

        for (int i = 0; i < N_MEDIAS; i++) {
            if(i < N_MEDIAS_P_TYPE)
                givenMedias.add(new Book("BookXY " + i, "Description " + i + "\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.", (i%9*3)%19, new Date(), Integer.MAX_VALUE-(i*3), "AuthorXY " + i));
            else if(i < 2 * N_MEDIAS_P_TYPE)
                givenMedias.add(new Audio("Audio " + i, "Description " + i + "\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation .", (i%9*3)%19, new Date(), "MP3", i*4));
            else if(i < 3 * N_MEDIAS_P_TYPE)
                givenMedias.add(new Movie("MovieXY " + i, "Description " + i + "\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.", (i%9*3)%19, new Date(), i*4, "MP4"));
            else if(i < 4 * N_MEDIAS_P_TYPE)
                givenMedias.add(new Paper("PaperXY " + i, "Description " + i + "\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.", (i%9*3)%19, new Date(), i));

            int g = i%3;
            givenMedias.get(i).setGenres(givenGenres.subList(g, g+3));

            mediaService.addMedia(givenMedias.get(i));

            name = "Library"+i;
            if(!mediaService.addLocation(name)) {
                throw new ExceptionInInitializerError();
            }
            for (int j=0; j<6; j++) {
                Location location = mediaService.getLocationByName(name).stream().iterator().next();
                if(!mediaService.addShelf(j, location.getId())) {
                    throw new ExceptionInInitializerError();
                }

                for (int k=0; k<6; k++) {
                    ShelfId shelfId = new ShelfId(j, location);
                    if(!mediaService.addCompartment(k+j+1, k, shelfId)) {
                        throw new ExceptionInInitializerError();
                    }

                    CompartmentId compartmentId = new CompartmentId(k, mediaService.getShelfById(shelfId));
                    Compartment compartment = mediaService.getCompartmentById( compartmentId);
                    if(!mediaService.addCopy(givenMedias.get(i), compartment, RandomGenerator.getDefault().nextInt(213454))){
                        //throw new PersistenceException("Couldnt add copy");
                    }
                    }
                }
            }
        }

        // for (int i = 0; i < N_MEDIAS; i++) {
        //     //givenGenres.add(new Genre("Horror" + i));
        //     mediaService.addGenre(givenGenres.get(i));
        //     givenMedias.add(new Book("Twillight" + i, "Byla sobie zabka mala rerekumum", 12, i, Integer.toString(i)));
        //     givenMedias.get(i).setGenres(givenGenres);
        //     //Reservation reservation = new Reservation(new ReservationId(givenMedias.get(i % N_FIRST_MEDIAS_FOR_QUEUES), givenUsers.get(i)), (int) Math.floor((i + N_FIRST_MEDIAS_FOR_QUEUES) / N_FIRST_MEDIAS_FOR_QUEUES));
        //     //givenReservations.get(i % N_FIRST_MEDIAS_FOR_QUEUES).add(reservation);

        //     mediaService.addMedia(givenMedias.get(i));

        //     name = "Library"+i;
        //     if(!mediaService.addLocation(name)) {
        //         throw new ExceptionInInitializerError();
        //     }
        //     for (int j=0; j<6; j++) {
        //         Location location = mediaService.getLocationByName(name).stream().iterator().next();
        //         if(!mediaService.addShelf(j, location.getId())) {
        //             throw new ExceptionInInitializerError();
        //         }

        //         for (int k=0; k<6; k++) {
        //             ShelfId shelfId = new ShelfId(j, location);
        //             if(!mediaService.addCompartment(k+j+1, k, shelfId)) {
        //                 throw new ExceptionInInitializerError();
        //             }

        //             CompartmentId compartmentId = new CompartmentId(k, mediaService.getShelfById(shelfId));
        //             Compartment compartment = mediaService.getCompartmentById( compartmentId);
        //             if(!mediaService.addCopy(givenMedias.get(i), compartment, RandomGenerator.getDefault().nextInt(213454))){
        //                 //throw new PersistenceException("Couldnt add copy");
        //             }
        //             }
        //         }
        //     }
        //     //reservationService.persist(reservation);
        // }
    }

