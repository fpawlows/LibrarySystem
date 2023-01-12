package at.ac.fhsalzburg.swd.spring.model.medias;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.Genre;
import at.ac.fhsalzburg.swd.spring.model.Reservation;
import at.ac.fhsalzburg.swd.spring.model.visitors.MediaVisitor;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Book extends Media {

    final static String className = "Book";
    public static String getClassName() {return className;}

    private Integer ISBN;
    private String Author;

    //TODO change to protected
    public Book() {
    }

    public Book(Integer ISBN, String author) {
        this.ISBN = ISBN;
        this.Author = author;
    }

    public Book(Integer ISBN, String author, String name, String description, Integer fsk, Date datePublished, List<Genre> genres, Collection<Copy> copies, List<Reservation> reservations) {
        super(name, description, fsk, datePublished, genres, copies, reservations);
        this.ISBN = ISBN;
        this.Author = author;
    }

    public Book(String name, String description, Integer fsk, Integer ISBN, String author) {
        super(name, description, fsk);
        this.ISBN = ISBN;
        this.Author = author;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }


    @Override
    public void accept(MediaVisitor mediaVisitor) {
        mediaVisitor.visit(this);
    }
}

