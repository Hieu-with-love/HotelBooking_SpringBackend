package hcmute.edu.vn.demo.abstractfactory;

import java.math.BigDecimal;

/**
 * Chính sách giá cho phòng Suite
 * Suite Room Pricing Policy
 */
public class SuitePricingPolicy implements PricingPolicy {
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, int nights) {
        // Suite: Giảm 10% từ 3 đêm, 15% từ 7 đêm, 20% từ 14 đêm
        BigDecimal totalPrice = basePrice.multiply(new BigDecimal(nights));
        
        if (nights >= 14) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.80")); // -20%
        } else if (nights >= 7) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.85")); // -15%
        } else if (nights >= 3) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.90")); // -10%
        }
        
        return totalPrice;
    }
    
    @Override
    public String getDescription() {
        return "Giảm 10% từ 3 đêm, 15% từ 7 đêm, 20% từ 14 đêm";
    }
}
