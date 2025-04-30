 package hcmute.edu.vn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse {
    private Long id;
    private String code;
    private BigDecimal discountAmount;
    private Double discountPercent;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private Integer quantity;
    @JsonProperty("active")
    private boolean isActive;
    private Integer remainingQuantity;
    private LocalDate createdAt;
}