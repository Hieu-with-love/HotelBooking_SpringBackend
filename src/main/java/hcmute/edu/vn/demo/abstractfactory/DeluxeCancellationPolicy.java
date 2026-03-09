package hcmute.edu.vn.demo.abstractfactory;

/**
 * Chính sách hủy phòng cho Deluxe
 * Deluxe Room Cancellation Policy
 */
public class DeluxeCancellationPolicy implements CancellationPolicy {
    
    @Override
    public boolean canCancel(int daysBeforeCheckIn) {
        // Có thể hủy nếu trước 3 ngày
        return daysBeforeCheckIn >= 3;
    }
    
    @Override
    public double getCancellationFee(int daysBeforeCheckIn) {
        if (daysBeforeCheckIn >= 10) {
            return 0.0; // Miễn phí nếu hủy trước 10 ngày
        } else if (daysBeforeCheckIn >= 5) {
            return 0.2; // 20% phí nếu hủy từ 5-9 ngày
        } else if (daysBeforeCheckIn >= 3) {
            return 0.5; // 50% phí nếu hủy từ 3-4 ngày
        } else {
            return 1.0; // 100% phí nếu hủy trong vòng 3 ngày
        }
    }
    
    @Override
    public String getDescription() {
        return "Miễn phí hủy trước 10 ngày, 20% phí từ 5-9 ngày, 50% phí từ 3-4 ngày, không hoàn tiền trong vòng 3 ngày";
    }
}
