package hcmute.edu.vn.controller.admin;

import hcmute.edu.vn.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> loadPayments() {
        try {
            return ResponseEntity.ok(paymentService.getAllPayments());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to load payments: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestParam("type") String type){
        try {
            paymentService.createPayment(type);
            return ResponseEntity.ok("Payment created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create payment: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable("id") String id){
        try {
            paymentService.deletePayment(Long.parseLong(id));
            return ResponseEntity.ok("Payment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete payment: " + e.getMessage());
        }
    }
}
