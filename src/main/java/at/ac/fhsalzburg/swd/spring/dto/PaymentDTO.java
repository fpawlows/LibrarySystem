package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Collection;

import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;
import at.ac.fhsalzburg.swd.spring.model.Payment;

import java.math.BigInteger;

enum states {successful, failed, inProgress, initialized};

public class PaymentDTO {

    private PaymentId paymentId;
    private states state;
    private BigInteger price;
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
