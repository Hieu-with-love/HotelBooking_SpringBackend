package hcmute.edu.vn.utils;

/**
 * Thread-Safe Singleton Pattern - Double-Checked Locking
 * 
 * Mục đích: Tạo ra một instance duy nhất của BookingIdGenerator trong toàn bộ ứng dụng
 * và đảm bảo thread-safe khi nhiều threads cùng truy cập.
 * 
 * Các cơ chế thread-safe được sử dụng:
 * 
 * 1. VOLATILE keyword:
 *    - Đảm bảo mọi thread đều thấy giá trị mới nhất của biến 'instance'
 *    - Ngăn chặn instruction reordering (tái sắp xếp lệnh) của compiler/JVM
 *    - Khi một thread ghi vào 'instance', các thread khác sẽ thấy ngay lập tức
 * 
 * 2. DOUBLE-CHECKED LOCKING:
 *    - First check (ngoài synchronized): Kiểm tra nhanh, tránh overhead của synchronized
 *    - Second check (trong synchronized): Đảm bảo chỉ 1 thread tạo instance
 *    - Chỉ lock khi instance == null (lần đầu tiên), các lần sau không cần lock
 * 
 * 3. SYNCHRONIZED block:
 *    - Chỉ 1 thread được vào block này tại một thời điểm
 *    - Lock trên BookingIdGenerator.class (class-level lock)
 *    - Đảm bảo việc khởi tạo instance là atomic (không bị gián đoạn)
 * 
 * 4. SYNCHRONIZED method (generateId):
 *    - Đảm bảo chỉ 1 thread được tăng counter tại một thời điểm
 *    - Ngăn chặn race condition khi nhiều threads cùng đọc/ghi counter
 *    - Mỗi thread phải đợi thread trước hoàn thành trước khi vào method
 * 
 * Tại sao cần Thread-Safe?
 * - Trong môi trường web server (Spring Boot), có nhiều requests đồng thời
 * - Mỗi request chạy trên một thread riêng
 * - Nếu không có thread-safe, có thể tạo ra duplicate booking IDs
 * 
 * Example scenario WITHOUT thread-safe:
 * Thread 1: đọc counter = 100, chuẩn bị ghi 101
 * Thread 2: đọc counter = 100 (vẫn chưa được update), chuẩn bị ghi 101
 * => Kết quả: 2 bookings có cùng ID = BK-101 (BUG!)
 * 
 * WITH thread-safe:
 * Thread 1: lock -> đọc counter = 100 -> ghi 101 -> unlock -> return "BK-101"
 * Thread 2: đợi -> lock -> đọc counter = 101 -> ghi 102 -> unlock -> return "BK-102"
 * => Kết quả: Mỗi booking có ID duy nhất
 */
public class BookingIdGenerator {

    // volatile: Đảm bảo visibility across threads
    private static volatile BookingIdGenerator instance;

    // Counter để tạo ID tăng dần
    private int counter = 0;

    /**
     * Private constructor - ngăn không cho tạo instance từ bên ngoài
     * Đây là đặc điểm của Singleton Pattern
     */
    private BookingIdGenerator() {}

    /**
     * Double-Checked Locking Pattern
     * 
     * @return instance duy nhất của BookingIdGenerator
     */
    public static BookingIdGenerator getInstance() {
        // First check: không synchronized, nhanh hơn
        // Nếu instance đã tồn tại, return ngay không cần lock
        if (instance == null) {
            // Synchronized block: chỉ được thực thi khi instance == null
            synchronized (BookingIdGenerator.class) {
                // Second check: bên trong synchronized block
                // Đảm bảo chỉ 1 thread tạo instance dù nhiều threads vào cùng lúc
                if (instance == null) {
                    instance = new BookingIdGenerator();
                }
            }
        }
        return instance;
    }

    /**
     * Generate unique booking ID
     * 
     * synchronized: Đảm bảo chỉ 1 thread được thực thi method này tại một thời điểm
     * - Thread-safe cho việc increment counter
     * - Ngăn chặn race condition
     * 
     * @return Booking ID dạng "BK-{number}"
     */
    public synchronized String generateId() {
        counter++;
        return "BK-" + String.format("%06d", counter); // Format: BK-000001, BK-000002, ...
    }

    /**
     * Get current counter value (for testing/debugging)
     * 
     * @return current counter value
     */
    public synchronized int getCounter() {
        return counter;
    }

    /**
     * Reset counter (for testing purposes only)
     */
    public synchronized void resetCounter() {
        counter = 0;
    }
}
