package hcmute.edu.vn.demo.abstractfactory;

import java.math.BigDecimal;

/**
 * Chính sách giá cho phòng Standard
 * Standard Room Pricing Policy
 */
public class StandardPricingPolicy implements PricingPolicy {
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, int nights) {
        // Standard: Giá cố định, không giảm giá
        return basePrice.multiply(new BigDecimal(nights));
    }
    
    @Override
    public String getDescription() {
        return "Giá cố định - Không có giảm giá";
    }
}
