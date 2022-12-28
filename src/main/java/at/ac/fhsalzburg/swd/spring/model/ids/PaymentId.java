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
    @JoinColumns({
        @JoinColumn(name = "copy_nr", nullable = false),
        @JoinColumn(name = "media_id", nullable = false),
        @JoinColumn(name = "username", nullable = false) //here is id of an id
    })
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
