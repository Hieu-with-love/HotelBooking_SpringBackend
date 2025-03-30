package hcmute.edu.vn.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VoucherDto {
    private Long id;
    private String code;
    private BigDecimal discountAmount;
    private double discountPercent;
    private int quantity;
    private LocalDate createdAt;
    private LocalDate expirationDate;
    private boolean isActive;
}
