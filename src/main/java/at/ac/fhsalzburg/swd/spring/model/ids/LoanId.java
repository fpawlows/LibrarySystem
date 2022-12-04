package at.ac.fhsalzburg.swd.spring.model.ids;

import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data //Getters, setters, constructors
public class LoanId implements Serializable {

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "copy_nr", nullable = false),
        @JoinColumn(name = "media_id", nullable = false) //here is id of an id
    })
    private Copy copy;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanId)) return false;
        LoanId loanId = (LoanId) o;
        return Objects.equals(getCopy(), loanId.getCopy()) && Objects.equals(getUser(), loanId.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCopy(), getUser());
    }
}
