package at.ac.fhsalzburg.swd.spring.model.medias;

import javax.persistence.Entity;

@Entity
public class Book extends Media {

    final static String className = "Book";
    public static String getClassName() {return className;}

    private Integer ISBN;
    private String Author;

    public Book() {
    }

    public Book(Integer ISBN, String author) {
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
}

