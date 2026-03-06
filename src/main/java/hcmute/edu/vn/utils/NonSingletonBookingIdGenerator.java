package hcmute.edu.vn.utils;

/**
 * ❌ NON-SINGLETON VERSION - FOR DEMONSTRATION ONLY
 * 
 * Class này được tạo ra để DEMONSTRATE vấn đề khi KHÔNG dùng Singleton Pattern.
 * 
 * KHÔNG sử dụng class này trong production code!
 * 
 * Vấn đề:
 * - Mỗi lần new NonSingletonBookingIdGenerator() → tạo instance MỚI
 * - Mỗi instance có counter riêng = 0
 * - Nhiều instances → nhiều counters → DUPLICATE IDs!
 * 
 * So sánh:
 * 
 * ❌ KHÔNG Singleton:
 *   NonSingletonBookingIdGenerator gen1 = new NonSingletonBookingIdGenerator();
 *   NonSingletonBookingIdGenerator gen2 = new NonSingletonBookingIdGenerator();
 *   gen1.generateId(); // "BK-000001" (counter của gen1)
 *   gen2.generateId(); // "BK-000001" (counter của gen2) ← DUPLICATE!
 * 
 * ✅ CÓ Singleton:
 *   BookingIdGenerator gen1 = BookingIdGenerator.getInstance();
 *   BookingIdGenerator gen2 = BookingIdGenerator.getInstance();
 *   gen1.generateId(); // "BK-000001"
 *   gen2.generateId(); // "BK-000002" ← UNIQUE!
 */
public class NonSingletonBookingIdGenerator {
    
    // Mỗi instance có counter RIÊNG
    private int counter = 0;
    
    /**
     * ❌ PUBLIC constructor - Ai cũng có thể tạo instance mới
     * Đây là vấn đề chính!
     */
    public NonSingletonBookingIdGenerator() {
        // Constructor public → có thể tạo nhiều instances
    }
    
    /**
     * Generate booking ID
     * Vấn đề: Nhiều instances → mỗi instance counter = 0 → duplicate IDs
     */
    public synchronized String generateId() {
        counter++;
        return "BK-" + String.format("%06d", counter);
    }
    
    public synchronized int getCounter() {
        return counter;
    }
}
