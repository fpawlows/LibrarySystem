package at.ac.fhsalzburg.swd.spring.dto.medias;

import java.io.Serializable;

public class BookDTO extends MediaDTO {

    private Integer ISBN;
    private String Author;

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

