package hcmute.edu.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO cho room được tạo bởi Factory Pattern
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomFactoryResponse {
    
    private Long roomId;
    private String roomName;
    private String roomType;
    private BigDecimal basePrice;
    private int capacity;
    private int numberOfBeds;
    private List<String> amenities;
    private String pricingPolicy;
    private String cancellationPolicy;
    private String factoryUsed;
    private String message;
}
