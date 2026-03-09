package hcmute.edu.vn.demo.abstractfactory;

/**
 * Chính sách hủy phòng cho Suite
 * Suite Room Cancellation Policy
 */
public class SuiteCancellationPolicy implements CancellationPolicy {
    
    @Override
    public boolean canCancel(int daysBeforeCheckIn) {
        // Có thể hủy nếu trước 5 ngày
        return daysBeforeCheckIn >= 5;
    }
    
    @Override
    public double getCancellationFee(int daysBeforeCheckIn) {
        if (daysBeforeCheckIn >= 14) {
            return 0.0; // Miễn phí nếu hủy trước 14 ngày
        } else if (daysBeforeCheckIn >= 7) {
            return 0.1; // 10% phí nếu hủy từ 7-13 ngày
        } else if (daysBeforeCheckIn >= 5) {
            return 0.3; // 30% phí nếu hủy từ 5-6 ngày
        } else {
            return 1.0; // 100% phí nếu hủy trong vòng 5 ngày
        }
    }
    
    @Override
    public String getDescription() {
        return "Miễn phí hủy trước 14 ngày, 10% phí từ 7-13 ngày, 30% phí từ 5-6 ngày, không hoàn tiền trong vòng 5 ngày";
    }
}
