package hcmute.edu.vn.demo.abstractfactory;

/**
 * Interface cho chính sách hủy phòng
 * Interface for cancellation policy
 */
public interface CancellationPolicy {
    
    /**
     * Kiểm tra xem có thể hủy phòng không
     * Check if cancellation is allowed
     * 
     * @param daysBeforeCheckIn Số ngày trước khi check-in
     * @return true nếu có thể hủy
     */
    boolean canCancel(int daysBeforeCheckIn);
    
    /**
     * Tính phí hủy phòng
     * Calculate cancellation fee
     * 
     * @param daysBeforeCheckIn Số ngày trước khi check-in
     * @return Phần trăm phí hủy (0.0 - 1.0)
     */
    double getCancellationFee(int daysBeforeCheckIn);
    
    /**
     * Mô tả chính sách hủy
     * Describe cancellation policy
     */
    String getDescription();
}
