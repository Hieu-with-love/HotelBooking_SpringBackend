package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.PaymentRequest;

import java.util.List;

public interface PaymentService {
    List<PaymentRequest> getAllPayments();
    void createPayment(String typeName);
    void deletePayment(Long id);
}
