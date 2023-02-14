package at.ac.fhsalzburg.swd.spring.services;

import at.ac.fhsalzburg.swd.spring.model.Payment;
import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;
import at.ac.fhsalzburg.swd.spring.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements PaymentServiceInterface {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment getPaymentById(PaymentId paymentId) {
        return null;
    }
}
