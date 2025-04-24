package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.dto.request.PaymentRequest;
import hcmute.edu.vn.model.Payment;
import hcmute.edu.vn.repository.PaymentRepository;
import hcmute.edu.vn.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentRequest> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(p -> {
                    PaymentRequest paymentRequest = new PaymentRequest();
                    paymentRequest.setId(p.getId());
                    paymentRequest.setType(p.getType());
                    return paymentRequest;
                }).toList();
    }

    @Override
    public void createPayment(String typeName) {
        Payment payment = paymentRepository.findByType(typeName);
        if (payment == null){
            payment = new Payment();
            payment.setType(typeName);
            paymentRepository.save(payment);
        }else{
            throw new RuntimeException("Payment already exists");
        }
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentRepository.delete(payment);
    }
}
