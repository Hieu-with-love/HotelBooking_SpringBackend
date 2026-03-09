package hcmute.edu.vn.demo.abstractfactory;

import hcmute.edu.vn.model.Room;

import java.math.BigDecimal;

/**
 * RoomBundle - Room entity với đầy đủ Pricing và Cancellation Policies
 * Extends Room để kế thừa tất cả properties của JPA entity
 * Thêm 2 policies để tạo hệ sinh thái hoàn chỉnh cho Abstract Factory Pattern
 */
public class RoomBundle extends Room {
    
    // Thêm 2 policies riêng cho Abstract Factory Pattern
    private PricingPolicy pricingPolicy;
    private CancellationPolicy cancellationPolicy;
    
    /**
     * Constructor với đầy đủ policies
     */
    public RoomBundle() {
        super(); // Gọi constructor của Room
    }
    
    /**
     * Set pricing policy
     */
    public void setPricingPolicy(PricingPolicy pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }
    
    /**
     * Set cancellation policy
     */
    public void setCancellationPolicy(CancellationPolicy cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }
    
    /**
     * Hiển thị thông tin đầy đủ bao gồm cả policies
     */
    public void displayFullInfo() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  THÔNG TIN ĐẦY ĐỦ - " + this.getName() + "                          ");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ 💰 GIÁ CƠ BẢN: " + this.getPrice() + " VND/đêm");
        System.out.println("║ 👥 SỨC CHỨA: " + this.getNumberOfAdults() + " người lớn, " + 
                          this.getNumberOfChildren() + " trẻ em");
        System.out.println("║ 🛏️  GIƯỜNG: " + this.getNumberOfBeds() + " giường");
        System.out.println("║ 🏷️  LOẠI PHÒNG: " + this.getType());
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ 🏷️  CHÍNH SÁCH GIÁ:");
        System.out.println("║    " + pricingPolicy.getDescription());
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ ❌ CHÍNH SÁCH HỦY:");
        System.out.println("║    " + cancellationPolicy.getDescription());
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        if (this.getServices() != null && !this.getServices().isEmpty()) {
            System.out.println("║ ✨ TIỆN NGHI:");
            this.getServices().forEach(service -> 
                System.out.println("║    - " + service.toString()));
        }
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Tính tổng giá với pricing policy
     */
    public BigDecimal calculateTotalPrice(int nights) {
        return pricingPolicy.calculatePrice(this.getPrice(), nights);
    }
    
    /**
     * Tính phí hủy phòng
     */
    public double calculateCancellationFee(int daysBeforeCheckIn) {
        return cancellationPolicy.getCancellationFee(daysBeforeCheckIn);
    }
    
    /**
     * Kiểm tra có thể hủy phòng không
     */
    public boolean canCancelBooking(int daysBeforeCheckIn) {
        return cancellationPolicy.canCancel(daysBeforeCheckIn);
    }
    
    // Getters for policies
    public PricingPolicy getPricingPolicy() { 
        return pricingPolicy; 
    }
    
    public CancellationPolicy getCancellationPolicy() { 
        return cancellationPolicy; 
    }
}
