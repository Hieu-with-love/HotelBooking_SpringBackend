package hcmute.edu.vn.demo.abstractfactory;

import java.math.BigDecimal;

/**
 * Chính sách giá cho phòng Deluxe
 * Deluxe Room Pricing Policy
 */
public class DeluxePricingPolicy implements PricingPolicy {
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, int nights) {
        // Deluxe: Giảm 5% từ 3 đêm, 10% từ 7 đêm
        BigDecimal totalPrice = basePrice.multiply(new BigDecimal(nights));
        
        if (nights >= 7) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.90")); // -10%
        } else if (nights >= 3) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.95")); // -5%
        }
        
        return totalPrice;
    }
    
    @Override
    public String getDescription() {
        return "Giảm 5% từ 3 đêm, 10% từ 7 đêm";
    }
}
