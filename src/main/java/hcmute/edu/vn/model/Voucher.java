package hcmute.edu.vn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Persistent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private double discountPercent;

    private BigDecimal discountAmount;

    private boolean isActive;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "expiration_date")
    @Future(message = "Thời gian hết hạn phải ở trong tương lai")
    private LocalDate expirationDate;

    private int quantity;

    @ManyToMany
    private List<User> customers;
}
