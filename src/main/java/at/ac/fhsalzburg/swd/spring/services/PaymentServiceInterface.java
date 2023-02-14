package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Payment;
import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;

public interface PaymentServiceInterface {

    public abstract Payment getPaymentById(PaymentId paymentId);

}
