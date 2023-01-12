package at.ac.fhsalzburg.swd.spring.dto.ids;

import at.ac.fhsalzburg.swd.spring.dto.CopyDTO;
import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.model.Copy;
import at.ac.fhsalzburg.swd.spring.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTOId implements Serializable {

    private CopyDTO copy;

    private UserDTO user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanDTOId)) return false;
        LoanDTOId loanDTOId = (LoanDTOId) o;
        return Objects.equals(copy, loanDTOId.copy) && Objects.equals(user, loanDTOId.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(copy, user);
    }
}
