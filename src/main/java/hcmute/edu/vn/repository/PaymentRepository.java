package hcmute.edu.vn.repository;

import hcmute.edu.vn.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select p from Payment p where p.type = :type")
    Payment findByType(@RequestParam("type") String type);

    String type(String type);
}
