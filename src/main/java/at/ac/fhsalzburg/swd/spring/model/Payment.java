package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.Timestamp;

@Entity
public class Payment {
    static enum states {successful, failed, inProgress, initialized};

    @EmbeddedId
    private PaymentId paymentId;
    private states state;
    private BigInteger price;

    public Payment() {}

    public Payment(PaymentId paymentId, states state, BigInteger price) {
        this.paymentId = paymentId;
        this.state = state;
        this.price = price;
    }

    public PaymentId getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(PaymentId paymentId) {
        this.paymentId = paymentId;
    }

    public states getState() {
        return state;
    }

    public void setState(states state) {
        this.state = state;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }
}
