package at.ac.fhsalzburg.swd.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreDTO {

    private Long id;
    private String name;


    public String toString(){
        return this.getName();
    }
}
