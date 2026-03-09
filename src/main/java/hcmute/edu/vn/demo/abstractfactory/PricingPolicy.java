package hcmute.edu.vn.demo.abstractfactory;

import java.math.BigDecimal;

/**
 * Interface cho chính sách tính giá
 * Interface for pricing policy
 */
public interface PricingPolicy {
    
    /**
     * Tính giá cuối cùng dựa trên số đêm
     * Calculate final price based on number of nights
     */
    BigDecimal calculatePrice(BigDecimal basePrice, int nights);
    
    /**
     * Mô tả chính sách giá
     * Describe pricing policy
     */
    String getDescription();
}
