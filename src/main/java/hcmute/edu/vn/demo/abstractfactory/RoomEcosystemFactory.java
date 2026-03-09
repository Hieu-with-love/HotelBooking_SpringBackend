package hcmute.edu.vn.demo.abstractfactory;

/**
 * ABSTRACT FACTORY PATTERN
 * 
 * Interface cho factory tạo ra HỆ SINH THÁI hoàn chỉnh của một loại phòng
 * Interface for factory that creates COMPLETE ECOSYSTEM for a room type
 */
public interface RoomEcosystemFactory {
    
    /**
     * Tạo gói sản phẩm hoàn chỉnh (Room + PricingPolicy + CancellationPolicy)
     * Create complete product bundle (Room + PricingPolicy + CancellationPolicy)
     */
    RoomBundle createRoomBundle();
    
    /**
     * Tạo chính sách giá
     * Create pricing policy
     */
    PricingPolicy createPricingPolicy();
    
    /**
     * Tạo chính sách hủy
     * Create cancellation policy
     */
    CancellationPolicy createCancellationPolicy();
}
