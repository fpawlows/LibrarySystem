package at.ac.fhsalzburg.swd.spring.dto.ids;
import at.ac.fhsalzburg.swd.spring.dto.LoanDTO;
import at.ac.fhsalzburg.swd.spring.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTOId implements Serializable {

    private LoanDTO loan;

    private Timestamp whenInitialized;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDTOId)) return false;
        PaymentDTOId that = (PaymentDTOId) o;
        return Objects.equals(loan, that.loan) && Objects.equals(whenInitialized, that.whenInitialized);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loan, whenInitialized);
    }
}
