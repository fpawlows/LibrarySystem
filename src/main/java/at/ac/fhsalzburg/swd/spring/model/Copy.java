package at.ac.fhsalzburg.swd.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Year;
import java.util.Collection;
import java.util.Date;

@Entity
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;
}
