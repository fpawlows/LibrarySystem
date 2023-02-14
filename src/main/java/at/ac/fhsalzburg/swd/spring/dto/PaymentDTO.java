package at.ac.fhsalzburg.swd.spring.dto;



import java.util.Collection;

import at.ac.fhsalzburg.swd.spring.dto.ids.PaymentDTOId;
import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;
import at.ac.fhsalzburg.swd.spring.model.Payment;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

enum states {successful, failed, inProgress, initialized};

@Getter
@Setter
public class PaymentDTO {

    private PaymentDTOId paymentId;
    private states state;
    private BigInteger price;
}
