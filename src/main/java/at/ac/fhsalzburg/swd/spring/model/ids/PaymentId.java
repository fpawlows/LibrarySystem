package at.ac.fhsalzburg.swd.spring.model.ids;
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
@Data
public class PaymentId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(nullable = false)
    private Timestamp whenInitialized;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentId)) return false;
        PaymentId paymentId = (PaymentId) o;
        return Objects.equals(getLoan(), paymentId.getLoan()) && Objects.equals(getWhenInitialized(), paymentId.getWhenInitialized());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoan(), getWhenInitialized());
    }
}
