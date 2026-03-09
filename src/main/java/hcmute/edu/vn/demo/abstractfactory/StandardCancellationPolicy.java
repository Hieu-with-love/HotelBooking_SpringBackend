package hcmute.edu.vn.demo.abstractfactory;

/**
 * Chính sách hủy phòng cho Standard
 * Standard Room Cancellation Policy
 */
public class StandardCancellationPolicy implements CancellationPolicy {
    
    @Override
    public boolean canCancel(int daysBeforeCheckIn) {
        // Có thể hủy nếu trước 2 ngày
        return daysBeforeCheckIn >= 2;
    }
    
    @Override
    public double getCancellationFee(int daysBeforeCheckIn) {
        if (daysBeforeCheckIn >= 7) {
            return 0.0; // Miễn phí nếu hủy trước 7 ngày
        } else if (daysBeforeCheckIn >= 2) {
            return 0.3; // 30% phí nếu hủy từ 2-6 ngày
        } else {
            return 1.0; // 100% phí nếu hủy trong vòng 2 ngày
        }
    }
    
    @Override
    public String getDescription() {
        return "Miễn phí hủy trước 7 ngày, 30% phí từ 2-6 ngày, không hoàn tiền trong vòng 2 ngày";
    }
}
